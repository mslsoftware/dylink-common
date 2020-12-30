package cn.net.vidyo.dylink.data.domain;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class Model<ID  extends Serializable> implements IModel<ID> {

    @PreUpdate
    public void preUpdate(){
    }
    @PrePersist
    public void prePersist(){

    }
    @PostPersist
    public void postPersist(){

    }
    @PostUpdate
    public void postUpdate(){

    }
    @PostLoad
    public void postLoad(){

    }
}
