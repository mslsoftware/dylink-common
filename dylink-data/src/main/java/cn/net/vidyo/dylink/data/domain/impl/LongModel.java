package cn.net.vidyo.dylink.data.domain.impl;



import cn.net.vidyo.dylink.data.domain.Model;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class LongModel extends Model<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "idOrGenerate")
    @GenericGenerator(name = "idOrGenerate", strategy = "cn.net.vidyo.dylink.data.domain.IdOrGenerate")
    private long id=0;//唯一标识

    @Override
    public Class<Long> getIdClass() {
        return Long.class;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    /**
     * ID已经被设置过
     *
     * @return 返回信息
     */
    @Override
    public boolean isIdModified(){
        return id!=0;
    }
}
