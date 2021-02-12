package cn.net.vidyo.dylink.data.domain;

import java.io.Serializable;

public interface IModel<ID  extends Serializable>  extends Serializable, Condition {
    ID getId();
    void setId(ID id);
    Class<ID> getIdClass();
    boolean isIdModified();
}
