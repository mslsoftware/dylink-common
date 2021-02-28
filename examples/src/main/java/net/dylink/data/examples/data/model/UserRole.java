package net.dylink.data.examples.data.model;

import cn.net.vidyo.dylink.data.domain.impl.CreateUpdateTimeHiddenIntModel;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Date;

@Entity
public class UserRole extends CreateUpdateTimeHiddenIntModel {
    int user=0;
    int role=0;

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
