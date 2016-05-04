package org.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 *
 * @author liujie
 */
public final class ReflectionUtils {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ReflectionUtils.class);

    /**
     * 创建实例
     *
     * @param clazz
     * @return
     */
    public static Object newInstance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 调用方法
     *
     * @param instance
     * @param method
     * @param parameters
     * @return
     */
    public static Object invokeMethod(Object instance, Method method,
                                      Object... parameters) {
        try {
            method.setAccessible(true);
            Object result = method.invoke(instance, parameters);
            System.out.println("~~~~~~" + result);
            return result;
        } catch (Exception e) {
            //LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 设置字段的值
     *
     * @param instance
     * @param field
     * @param value
     */
    public static void setField(Object instance, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(instance, value);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
