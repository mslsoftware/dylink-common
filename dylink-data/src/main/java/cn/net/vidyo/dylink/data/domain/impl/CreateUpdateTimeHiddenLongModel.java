package cn.net.vidyo.dylink.data.domain.impl;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class CreateUpdateTimeHiddenLongModel extends CreateUpdateTimeLongModel {
    @Column(columnDefinition="BIT COMMENT '逻辑删除 0未删除 1删除'")
    boolean hidden=false;

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
