package org.framework.bean;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 封装请求处理器
 *
 * @author liujie
 */
public class ActionHandler implements Serializable {

    private static final long serialVersionUID = 4383310918010581376L;

    private Class<?> controllerClass;
    private Method actionMethod;

    public ActionHandler(Class<?> controllerClass, Method method) {
        this.actionMethod = method;
        this.controllerClass = controllerClass;
    }

    public Class<?> getControllerClass() {
        return this.controllerClass;
    }

    public Method getActionMethod() {
        return this.actionMethod;
    }

    @Override
    public String toString() {
        return "ActionHandler{" +
                "controllerClass=" + this.controllerClass +
                ", actionMethod=" + this.actionMethod +
                '}';
    }
}
