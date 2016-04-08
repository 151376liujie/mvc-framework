package org.framework.java.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装请求参数
 * 
 * @author liujie
 * 
 */
public class RequestParameter implements Serializable {

    private static final long serialVersionUID = -7840058383773003098L;

    private Map<String, Object> parameterMap = new HashMap<String, Object>();

    public RequestParameter(Map<String, Object> parameterMap) {
	super();
	this.parameterMap = parameterMap;
    }

    public Map<String, Object> getParameterMap() {
	return parameterMap;
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
