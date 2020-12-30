package cn.net.vidyo.dylink.data.domain;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class CreateUpdateTimeHiddenModel<ID  extends Serializable> extends CreateUpdateTimeModel<ID> {
    boolean hidden=false;

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
