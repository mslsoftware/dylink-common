package cn.net.vidyo.dylink.data.domain;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class CreateUpdateTimeModel<ID  extends Serializable> extends Model<ID> {
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
