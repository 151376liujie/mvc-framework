package org.framework.model;

/**
 * 封装返回数据
 *
 * @author liujie
 */
public class ResponseData {

    private Object model;

    public ResponseData(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }

}
