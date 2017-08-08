package org.framework.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面模板方法,子类继承该类可以实现方法拦截通知、前置通知、后置通知、方法执行后后置通知、抛出异常通知
 * Created by LiuJie on 2016/4/28 10:55.
 */
public abstract class AspectProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Class<?> targetClass = proxyChain.getTargetClass();
        Object targetObject = proxyChain.getTargetObject();
        Method targetMethod = proxyChain.getTargetMethod();
        Object[] methodParams = proxyChain.getMethodParams();

        Object result = null;

        try {
            if (intercept(targetClass, targetObject, targetMethod)) {
                before(targetClass, targetObject, targetMethod, methodParams);
                result = proxyChain.doProxyChain();
                after(targetClass, targetObject, targetMethod, methodParams, result);
                return result;
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Throwable throwable) {
            LOGGER.error(throwable.getMessage(), throwable);
            error(targetClass, targetObject, targetMethod, methodParams, throwable);
            throw throwable;
        } finally {
            end(targetClass, targetObject, targetMethod, methodParams);
        }
        return result;
    }

    /**
     * 子类复写该方法可以在方法返回后拦截
     *
     * @param targetClass
     * @param targetObject
     * @param targetMethod
     * @param methodParams
     */
    protected void end(Class<?> targetClass, Object targetObject,
                       Method targetMethod, Object[] methodParams) {
    }

    /**
     * 子类复写该方法可实现在方法抛出异常时拦截
     *
     * @param targetClass
     * @param targetObject
     * @param targetMethod
     * @param methodParams
     * @param e
     */
    protected void error(Class<?> targetClass, Object targetObject,
                         Method targetMethod, Object[] methodParams, Throwable e) {
    }

    /**
     * 子类重写该方法可实现前置增强
     *
     * @param targetClass
     * @param targetObject
     * @param targetMethod
     * @param methodParams
     */
    protected void before(Class<?> targetClass, Object targetObject,
                          Method targetMethod, Object[] methodParams) {

    }

    /**
     * 子类重写该方法可实现后置增强
     *
     * @param targetClass
     * @param targetObject
     * @param targetMethod
     * @param methodParams
     * @param result
     */
    protected void after(Class<?> targetClass, Object targetObject,
                         Method targetMethod, Object[] methodParams, Object result) {

    }

    /**
     * 子类可重写该方法实现有条件的方法拦截
     *
     * @param targetClass
     * @param targetObject
     * @param targetMethod
     * @return
     */
    protected boolean intercept(Class<?> targetClass, Object targetObject,
                                Method targetMethod) {

        return true;
    }
}
