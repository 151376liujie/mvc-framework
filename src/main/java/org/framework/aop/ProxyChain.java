package org.framework.aop;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理链
 * Created by liujie on 2016/4/26 23:49.
 */
public class ProxyChain {
    /**
     * 目标类
     */
    private Class<?> targetClass;
    /**
     * 目标对象
     */
    private Object targetObject;
    /**
     * 目标方法
     */
    private Method targetMethod;
    /**
     * 方法代理
     */
    private MethodProxy methodProxy;
    /**
     * 方法参数
     */
    private Object[] methodParams;

    private List<Proxy> proxyList = new ArrayList<>();

    private int index = 0;

    public ProxyChain(List<Proxy> proxyList, Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams) {
        this.proxyList = proxyList;
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public MethodProxy getMethodProxy() {
        return methodProxy;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Object doProxyChain() throws Throwable {
        Object invokeResult;
        if (index < proxyList.size()) {
            Proxy proxy = proxyList.get(index++);
            invokeResult = proxy.doProxy(this);
        } else {
            invokeResult = getMethodProxy().invokeSuper(targetObject, methodParams);
        }
        return invokeResult;
    }

}
