package org.framework.java.bean;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 封装请求处理器
 * 
 * @author liujie
 * 
 */
public class ActionHandler implements Serializable {

    private static final long serialVersionUID = 4383310918010581376L;

    private Class<?> controllerClass;
    private Method actionMethod;

    public Class<?> getControllerClass() {
	return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
	this.controllerClass = controllerClass;
    }

    public Method getActionMethod() {
	return actionMethod;
    }

    public void setActionMethod(Method actionMethod) {
	this.actionMethod = actionMethod;
    }

}
