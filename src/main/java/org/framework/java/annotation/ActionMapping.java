package org.framework.java.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求动作映射
 * 
 * @author liujie
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionMapping {

    public String value();

    // 请求方法
    public String method();

    // 请求的url
    public String requestUrl();
}
