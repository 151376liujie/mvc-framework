package org.framework.aop;

import org.framework.annotation.Service;

import java.lang.reflect.Method;

/**
 * Created by liujie on 2016/5/6 10:18.
 */
@Aspect(Service.class)
public class SorryAspect extends AspectProxy {


    @Override
    protected void before(Class<?> targetClass, Object targetObject, Method targetMethod, Object[] methodParams) {
        System.out.println("sorry proxy !!!  ---- > before");
    }

    @Override
    protected void after(Class<?> targetClass, Object targetObject, Method targetMethod, Object[] methodParams, Object result) {
        System.out.println("sorry proxy !!!  ---- > after");
    }
}
