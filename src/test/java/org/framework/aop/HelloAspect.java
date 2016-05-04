package org.framework.aop;

import java.lang.reflect.Method;

import org.framework.annotation.Service;

/**
 * 切面类
 * Created by LiuJie on 2016/5/4 10:12.
 */


@Aspect(Service.class)
public class HelloAspect extends AspectProxy {


    @Override
	protected void before(Class<?> targetClass, Object targetObject,
			Method targetMethod, Object[] methodParams) {
        System.out.println("before...");
    }

    @Override
	protected void after(Class<?> targetClass, Object targetObject,
			Method targetMethod, Object[] methodParams, Object result) {
        System.out.println("after...");
    }
}
