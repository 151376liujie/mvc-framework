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

    /**
     * 存放bean的集合，key-bean对应的class，value-class对应的实例对象
     */
    private static final Map<Class<?>, Object> beanMap = new HashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassUtils.getBeanClassSet();
        for (Class<?> clazz : beanClassSet) {
            Object instance = ReflectionUtils.newInstance(clazz);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("class {} has bean instanced and added to bean container...", clazz.getName());
            }
            beanMap.put(clazz, instance);
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return beanMap;
    }

    /**
     * 获取指定类型的实例
     *
     * @param clazz
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        if (!beanMap.containsKey(clazz)) {
            throw new RuntimeException("can not get bean by class: " + clazz.getName());
        }
        return (T) beanMap.get(clazz);
    }

}

