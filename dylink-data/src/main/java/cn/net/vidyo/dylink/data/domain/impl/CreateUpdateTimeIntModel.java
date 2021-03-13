package cn.net.vidyo.dylink.data.domain.impl;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class CreateUpdateTimeIntModel extends IntModel {
    @Column(columnDefinition="bigint  COMMENT '创建时间'")
    long createtime =0;
    @Column(columnDefinition="bigint  COMMENT '更新时间'")
    long updatetime =0;

    @Override
    public void preUpdate() {
        updatetime =System.currentTimeMillis();
        super.preUpdate();
    }

    @Override
    public void prePersist() {
        createtime =System.currentTimeMillis();
        super.prePersist();
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }
}
