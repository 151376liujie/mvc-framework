package org.framework.web;

import java.io.Serializable;

/**
 * Author: jonny
 * Time: 2017-08-10 13:48.
 */
public class WebRequestBaseParameter implements Serializable {

    private String fieldName;

    protected String getFieldName() {
        return fieldName;
    }

    protected void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
