package org.framework.utils;

import org.framework.BeanContainer;
import org.framework.aop.Aspect;
import org.framework.aop.AspectProxy;
import org.framework.aop.Proxy;
import org.framework.aop.ProxyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * AOP工具类
 * Created by liujie on 2016/5/6 13:43.
 */
public final class AopUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopUtils.class);

    static {
        LOGGER.info("aop framework is going to load...");
        //key-切面类class，value-目标类集合
        Map<Class<?>, Set<Class<?>>> proxyMap = getProxyMap();
        //key-目标类class，value-切面类实例
        Map<Class<?>, List<Proxy>> targetMap = getTargetMap(proxyMap);

        Map<Class<?>, Object> beanMap = BeanContainer.getBeanMap();

        for (Map.Entry<Class<?>, List<Proxy>> entry : targetMap.entrySet()) {
            //目标类
            Class<?> targetClass = entry.getKey();
            //切面实例集合
            List<Proxy> proxyList = entry.getValue();

            Object proxy = ProxyManager.getProxy(targetClass, proxyList);

            beanMap.put(targetClass, proxy);

        }
    }


    /**
     * 获取指定注解下面的所有目标类
     *
     * @param aspect
     * @return
     */
    private static Set<Class<?>> getTargetClassSet(Aspect aspect) {
        Set<Class<?>> classes = new HashSet<>();
        if (aspect != null) {
            Class<? extends Annotation> annotation = aspect.value();
            classes.addAll(ClassUtils.getClassSetByAnnotation(annotation));
        }
        return classes;
    }

    /**
     * 获取切面类和目标类之间的映射关系，一个切面类对应多个目标类
     * key --> 切面类；value --> 目标类集合
     *
     * @return
     */
    private static Map<Class<?>, Set<Class<?>>> getProxyMap() {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        //所有的切面类
        Set<Class<?>> aspectClassSet = ClassUtils.getClassSetBySuperClass(AspectProxy.class);
        for (Class<?> aspClass : aspectClassSet) {
            if (aspClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = aspClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = getTargetClassSet(aspect);
                proxyMap.put(aspClass, targetClassSet);
            }
        }
        return proxyMap;
    }

    /**
     * 根据切面类和目标类的映射关系提取目标类和代理（切面）的映射关系
     * key --> 目标类；value --> 切面实例的集合
     *
     * @param proxyMap
     * @return
     */
    private static Map<Class<?>, List<Proxy>> getTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> entry : proxyMap.entrySet()) {
            //切面类
            Class<?> aspectClass = entry.getKey();
            //目标类集合
            Set<Class<?>> targetClassSet = entry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxyInstance = (Proxy) ReflectionUtils.newInstance(aspectClass);
                if (!targetMap.containsKey(targetClass)) {
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxyInstance);
                    targetMap.put(targetClass, proxyList);
                } else {
                    List<Proxy> proxyList = targetMap.get(targetClass);
                    proxyList.add(proxyInstance);
                }
            }
        }
        return targetMap;
    }
}
