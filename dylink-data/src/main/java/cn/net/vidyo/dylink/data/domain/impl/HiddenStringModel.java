package cn.net.vidyo.dylink.data.domain.impl;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class HiddenStringModel extends StringModel {
    boolean hidden=false;

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
