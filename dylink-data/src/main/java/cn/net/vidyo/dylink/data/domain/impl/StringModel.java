package cn.net.vidyo.dylink.data.domain.impl;



import cn.net.vidyo.dylink.data.domain.Model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class StringModel extends Model<String> {

    @Id
    @Column(columnDefinition="int COMMENT '字符串主键'")
    private String id="";//唯一标识

    @Override
    public Class<String> getIdClass() {
        return String.class;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
    /**
     * ID已经被设置过
     *
     * @return 返回信息
     */
    @Override
    public boolean isIdModified(){
        return id!=null && !id.isEmpty();
    }
}
