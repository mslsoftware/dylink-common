package demo.domain;

import cn.net.vidyo.dylink.data.domain.impl.CreateUpdateTimeHiddenIntModel;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;

@Entity(name = "user")
@Proxy(lazy = false)
public class User extends CreateUpdateTimeHiddenIntModel {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
