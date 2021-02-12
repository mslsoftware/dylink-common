package net.dylink.data.examples.data.model;

import cn.net.vidyo.dylink.data.domain.impl.CreateUpdateTimeHiddenIntModel;

import javax.persistence.Entity;

@Entity
public class User extends CreateUpdateTimeHiddenIntModel {
    String name="";
    int age=0;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
