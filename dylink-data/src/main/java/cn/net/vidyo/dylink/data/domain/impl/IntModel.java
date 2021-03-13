package cn.net.vidyo.dylink.data.domain.impl;



import cn.net.vidyo.dylink.data.domain.Model;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@MappedSuperclass
public class IntModel extends Model<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "idOrGenerate")
    @GenericGenerator(name = "idOrGenerate", strategy = "cn.net.vidyo.dylink.data.domain.IdOrGenerate")
    @Column(columnDefinition="int COMMENT '整形主键'")
    private int id=0;//唯一标识

    @Override
    public Class<Integer> getIdClass() {
        return Integer.class;
    }

    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public void setId(Integer id) {
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
