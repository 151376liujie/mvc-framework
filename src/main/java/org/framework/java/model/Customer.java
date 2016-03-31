package org.framework.java.model;


public class Customer extends BaseModel {

    private static final long serialVersionUID = -5910058898132624911L;

    private String name;
    private String mobile;
    private String addr;

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
