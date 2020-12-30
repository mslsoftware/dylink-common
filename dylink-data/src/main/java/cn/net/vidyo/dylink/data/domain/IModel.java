package cn.net.vidyo.dylink.data.domain;

import cn.net.vidyo.dylink.data.jpa.ICondition;

import java.io.Serializable;

public interface IModel<ID  extends Serializable>  extends Serializable, ICondition<ID> {
    ID getId();
    void setId(ID id);
    Class<ID> getIdClass();
    boolean isIdModified();
}
