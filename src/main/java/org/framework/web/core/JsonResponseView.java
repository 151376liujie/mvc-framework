package org.framework.web.core;

import java.io.Serializable;

/**
 * 封装返回数据
 *
 * @author liujie
 */
public class JsonResponseView<T> implements Serializable {

    private int code;

    private String message;

    private T data;

    public JsonResponseView(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public JsonResponseView(T data) {
        this.data = data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public static JsonResponseView buildSuccessResponse(Object data) {
        return new JsonResponseView(data);
    }
}
