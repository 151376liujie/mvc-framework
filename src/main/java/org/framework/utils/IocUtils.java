package org.framework.utils;

import org.framework.BeanContainer;
import org.framework.annotation.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 控制反转工具类
 *
 * @author liujie
 */
public final class IocUtils {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(IocUtils.class);

    /**
     * 实现IOC控制反转
     */
    static {
        Map<Class<?>, Object> beanMap = BeanContainer.getBeanMap();
        for (Entry<Class<?>, Object> entry : beanMap.entrySet()) {
            Class<?> clazz = entry.getKey();
            Object instance = entry.getValue();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Inject.class)) {
                    Class<?> type = field.getType();
                    if (beanMap.containsKey(type)) {
                        if (LOGGER.isInfoEnabled()) {
                            LOGGER.info(" prepare to inject {}.{}...",
                                    clazz.getName(), field.getName());
                        }
                        Object value = beanMap.get(type);
                        ReflectionUtils.setField(instance, field, value);
                    } else {
                        LOGGER.error("failed to inject filed: {}, no type: {} found in the bean container.", field.getName(), type.getName());
                    }
                }
            }
        }
    }

}
