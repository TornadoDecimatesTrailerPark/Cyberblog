package com.cyberblogger.model;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class BasePoJo {
    private int id;
    private Timestamp createTime;
    private Timestamp updateTime;

    public BasePoJo(int id, Timestamp createTime, Timestamp updateTime) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public BasePoJo(Timestamp createTime, Timestamp updateTime) {
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

/*
    public BasePoJo(int id) {
        this.id = id;
    }
    public BasePoJo(int id,Timestamp createTime) {
        this.id = id;
        this.createTime=createTime;
    }
*/

    public BasePoJo() {

    }

}
