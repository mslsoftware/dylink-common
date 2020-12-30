package cn.net.vidyo.dylink.data.domain.impl;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class CreateUpdateTimeIntModel extends IntModel {
    long createtime =0;
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
