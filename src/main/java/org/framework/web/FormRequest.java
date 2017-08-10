package org.framework.web;

/**
 * 封装form表单请求参数
 * Author: jonny
 * Time: 2017-08-10 13:47.
 */
public class FormRequest extends WebRequestBaseParameter {

    private String fieldValue;

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
