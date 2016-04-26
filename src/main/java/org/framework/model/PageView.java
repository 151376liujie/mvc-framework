package org.framework.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 视图对象的封装
 *
 * @author liujie
 */
public class PageView {

    private String location;
    private Map<String, Object> modelMap = new HashMap<String, Object>();

    public PageView(String location) {
        this.location = location;
    }

    public PageView addModel(String key, Object value) {
        modelMap.put(key, value);
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Map<String, Object> getModelMap() {
        return modelMap;
    }
}
