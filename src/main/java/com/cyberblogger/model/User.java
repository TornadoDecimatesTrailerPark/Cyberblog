package com.cyberblogger.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by chris on 29/01/20.
 *
 * @author chris
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BasePoJo {

    private String f_name;
    private String l_name;
    private String email;
    private Date birthDay;
    private String description;
    private String avatarUrl;

    public User(int id, String f_name, String l_name, String email, Date birthDay, String description, String avatarUrl,
                Timestamp createTime, Timestamp updateTime) {
        super(id, createTime, updateTime);
        this.f_name = f_name;
        this.l_name = l_name;
        this.birthDay = birthDay;
        this.description = description;
        this.avatarUrl = avatarUrl;
        this.email = email;
    }

    public User(String f_name, String l_name, String email, Date birthDay, String description, String avatarUrl,
                Timestamp createTime, Timestamp updateTime) {
        super(createTime, updateTime);
        this.f_name = f_name;
        this.l_name = l_name;
        this.birthDay = birthDay;
        this.description = description;
        this.avatarUrl = avatarUrl;
        this.email = email;
    }

    public User() {

    }
}
