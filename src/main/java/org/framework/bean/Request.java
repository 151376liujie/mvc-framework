package org.framework.bean;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 对请求方式和请求路径的封装
 * 
 * @author liujie
 * 
 */
public class Request implements Serializable {

    private static final long serialVersionUID = 5401388820585430464L;

    private String requestMethod;
    private String requestUrl;

    public Request(String requestMethod, String requestUrl) {
	super();
	this.requestMethod = requestMethod;
	this.requestUrl = requestUrl;
    }

    public String getRequestMethod() {
	return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
	this.requestMethod = requestMethod;
    }

    public String getRequestUrl() {
	return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
	this.requestUrl = requestUrl;
    }

    @Override
    public int hashCode() {
	int reflectionHashCode = HashCodeBuilder.reflectionHashCode(this);
	return reflectionHashCode;
    }

    @Override
    public boolean equals(Object obj) {
	boolean reflectionEquals = EqualsBuilder.reflectionEquals(this, obj);
	/*
	 * if (obj == null || this == null) { return false; } if (obj.getClass()
	 * != this.getClass()) { return false; } Request request = (Request)
	 * obj; return
	 * request.getRequestMethod().equals(this.getRequestMethod()) &&
	 * request.getRequestUrl().equals(this.getRequestUrl());
	 */
	return reflectionEquals;
    }

    @Override
    public String toString() {
	return "Request [requestMethod=" + requestMethod + ", requestUrl="
		+ requestUrl + "]";
    }

}
