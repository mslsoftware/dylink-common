package cn.net.vidyo.dylink.data.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class CreateUpdateTimeHiddenModel<ID  extends Serializable> extends CreateUpdateTimeModel<ID> {
    @Column(columnDefinition="BIT COMMENT '逻辑删除 0未删除 1删除'")
    boolean hidden=false;

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
