package org.framework.model;

import java.io.Serializable;

/**
 * Author: jonny
 * Time: 2017-08-09 16:37.
 */
public class User implements Serializable {

    private int id;
    private String username;
    private String password;
    private short sex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public short getSex() {
        return sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }
}
