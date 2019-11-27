package com.alan.test;

import com.alan.db.annotations.Column;
import com.alan.db.annotations.Patcher;
import com.alan.db.annotations.PrimaryKey;
import com.alan.db.annotations.Table;
import com.alan.db.base.DbModel;

/**
 * @author Alan
 * 时 间：2019-11-26
 * 简 述：<功能简述>
 */
@Table(name = "person")
@Patcher(name = {PersonPathch2.class})
public class Person extends DbModel {

    @PrimaryKey
    private String name;
    @PrimaryKey
    private int age;
    @Column(column = "create_time",defaultValue = "0")
    private long createTime;

    private int sid;

    private int score;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public long getCreateTime() {
        return createTime;
    }

    public int getSid() {
        return sid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
