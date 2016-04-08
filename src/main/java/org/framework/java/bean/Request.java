package org.framework.java.bean;

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
	return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
	return EqualsBuilder.reflectionEquals(this, obj);
    }

}
