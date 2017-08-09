package org.framework.model;

import java.io.Serializable;

/**
 * 封装返回数据
 *
 * @author liujie
 */
public class JsonResponseData<T> implements Serializable {

    private int code;

    private String message;

    private T data;

    public JsonResponseData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public JsonResponseData(T data) {
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
}
