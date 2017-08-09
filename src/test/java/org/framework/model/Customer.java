package org.framework.model;

import java.io.Serializable;

/**
 * Author: jonny
 * Time: 2017-08-09 16:45.
 */
public class Customer implements Serializable {

    private int id;
    private String name;
    private String mobile;
    private String addr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
