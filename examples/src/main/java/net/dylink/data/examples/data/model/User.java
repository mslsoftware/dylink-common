package net.dylink.data.examples.data.model;

import cn.net.vidyo.dylink.data.domain.impl.CreateUpdateTimeHiddenIntModel;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Entity
public class User extends CreateUpdateTimeHiddenIntModel {
    String name="";
    int age=0;
    int status=0;
    double money=0L;
    float rate=0;
    @Enumerated(value = EnumType.ORDINAL)
    UserType userType=UserType.teacher;
    @Enumerated(value = EnumType.STRING)
    UserType userType2=UserType.student;

    long submitTime=0;
    Date businessTime=new Date();
    short num=0;
    char answer='A';

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserType getUserType2() {
        return userType2;
    }

    public void setUserType2(UserType userType2) {
        this.userType2 = userType2;
    }

    public long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(long submitTime) {
        this.submitTime = submitTime;
    }

    public Date getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(Date businessTime) {
        this.businessTime = businessTime;
    }

    public short getNum() {
        return num;
    }

    public void setNum(short num) {
        this.num = num;
    }

    public char getAnswer() {
        return answer;
    }

    public void setAnswer(char answer) {
        this.answer = answer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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
