package org.framework.transaction;

import org.framework.annotation.NeedTransaction;
import org.framework.annotation.Service;
import org.framework.aop.Aspect;
import org.framework.aop.AspectProxy;
import org.framework.utils.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 事物代理切面
 * Author: jonny
 * Time: 2017-08-08 22:21.
 */
@Aspect(Service.class)
public class TransactionProxy extends AspectProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);

    /**
     * 子类复写该方法可实现在方法抛出异常时拦截
     *
     * @param targetClass
     * @param targetObject
     * @param targetMethod
     * @param methodParams
     * @param e
     */
    @Override
    protected void error(Class<?> targetClass, Object targetObject, Method targetMethod, Object[] methodParams, Throwable e) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("error when executing method: {} in a transaction , so prepare to rollback transaction.", targetMethod.getName());
        }
        DatabaseUtils.rollbackTransaction();
    }

    /**
     * 子类重写该方法可实现前置增强
     *
     * @param targetClass
     * @param targetObject
     * @param targetMethod
     * @param methodParams
     */
    @Override
    protected void before(Class<?> targetClass, Object targetObject, Method targetMethod, Object[] methodParams) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("starting a new transaction when executing method: {}", targetMethod.getName());
        }
        DatabaseUtils.beginTransaction();
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
    @Override
    protected void after(Class<?> targetClass, Object targetObject, Method targetMethod, Object[] methodParams, Object result) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("committing a new transaction when executing method: {}", targetMethod.getName());
        }
        DatabaseUtils.commitTransaction();
    }

    /**
     * 子类可重写该方法实现有条件的方法拦截
     *
     * @param targetClass
     * @param targetObject
     * @param targetMethod
     * @return
     */
    @Override
    protected boolean intercept(Class<?> targetClass, Object targetObject, Method targetMethod) {
        boolean annotationPresent = targetMethod.isAnnotationPresent(NeedTransaction.class);
        if (annotationPresent && LOGGER.isInfoEnabled()) {
            LOGGER.info("method: {} will need a transaction when executing, because it has @NeedTransaction annotation.", targetMethod.getName());
        }
        return annotationPresent;
    }
}
