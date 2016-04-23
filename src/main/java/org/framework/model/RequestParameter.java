package org.framework.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 封装请求参数
 * 
 * @author liujie
 * 
 */
public class RequestParameter implements Serializable {

    private static final long serialVersionUID = -7840058383773003098L;

    private static final String KEY_REQUEST = "key_request";

    private static final String KEY_RESPONSE = "key_response";

    private Map<String, Object> parameterMap = new HashMap<String, Object>();

    public RequestParameter(Map<String, Object> parameterMap) {
	super();
	this.parameterMap = parameterMap;
    }

    public Map<String, Object> getParameterMap() {
	return parameterMap;
    }

    /**
     * 获取httpservletrequest对象
     * 
     * @return
     */
    public HttpServletRequest getHttpServletRequest() {
	return (HttpServletRequest) parameterMap.get(KEY_REQUEST);
    }

    /**
     * 获取httpservletResponse 对象
     * 
     * @return
     */
    public HttpServletResponse getHttpServletResponse() {
	return (HttpServletResponse) parameterMap.get(KEY_RESPONSE);
    }

    /**
     * 根据指定名称获取参数值
     * 
     * @param name
     * @return
     */
    public String getString(String name) {
	return parameterMap.get(name).toString();
    }

}