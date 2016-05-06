package org.framework;

import org.framework.utils.ClassUtils;
import org.framework.utils.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanContainer {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(BeanContainer.class);

    private static final Map<Class<?>, Object> beanMap = new HashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassUtils.getBeanClassSet();
        for (Class<?> clazz : beanClassSet) {
            Object instance = ReflectionUtils.newInstance(clazz);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("class {} has added to bean container...", clazz.getName());
            }
            beanMap.put(clazz, instance);
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return beanMap;
    }

    /**
     * 获取指定类型的bean实例
     *
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
        return (T) beanMap.get(clazz);
    }

}

