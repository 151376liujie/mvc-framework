package org.framework.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装请求参数
 *
 * @author liujie
 */
public class WebRequestParameterHolder implements Serializable {

    private Map<String, Object> parameterMap = new HashMap<>();

    public WebRequestParameterHolder(Map<String, Object> parameterMap) {
        super();
        this.parameterMap = parameterMap;
    }

    public Map<String, Object> getParameterMap() {
        return this.parameterMap;
    }

    /**
     * 根据指定名称获取参数值
     *
     * @param name
     * @return
     */
    public String getString(String name) {
        return this.parameterMap.get(name).toString();
    }

    public boolean isEmpty() {
        return this.parameterMap.isEmpty();
    }
}
