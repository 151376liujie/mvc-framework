package org.framework.web.core;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 对请求方式和请求路径的封装
 *
 * @author liujie
 */
public class HandlerMappingParameter implements Serializable {

    private String requestMethod;
    private String requestUrl;

    public HandlerMappingParameter(String requestMethod, String requestUrl) {
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
	 * != this.getClass()) { return false; } HandlerMappingParameter request = (HandlerMappingParameter)
	 * obj; return
	 * request.getRequestMethod().equals(this.getRequestMethod()) &&
	 * request.getRequestUrl().equals(this.getRequestUrl());
	 */
        return reflectionEquals;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
