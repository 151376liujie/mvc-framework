package org.framework.java.bean;

import java.io.Serializable;

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

}
