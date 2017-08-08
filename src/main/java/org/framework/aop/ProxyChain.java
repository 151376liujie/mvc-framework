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

    private int index = 0;
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

    public ProxyChain(List<Proxy> proxyList, Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams) {
        this.proxyList = proxyList;
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
    }

    public Class<?> getTargetClass() {
        return this.targetClass;
    }

    public Object getTargetObject() {
        return this.targetObject;
    }

    public Method getTargetMethod() {
        return this.targetMethod;
    }

    public MethodProxy getMethodProxy() {
        return this.methodProxy;
    }

    public Object[] getMethodParams() {
        return this.methodParams;
    }

    public Object doProxyChain() throws Throwable {
        Object invokeResult;
        if (this.index < this.proxyList.size()) {
            Proxy proxy = this.proxyList.get(this.index++);
            invokeResult = proxy.doProxy(this);
        } else {
            invokeResult = getMethodProxy().invokeSuper(this.targetObject, this.methodParams);
        }
        return invokeResult;
    }

}
