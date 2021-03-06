package org.framework.aop;

import org.framework.BeanContainer;
import org.framework.FrameworkLoader;
import org.framework.service.HelloService;
import org.framework.service.UserService;
import org.framework.utils.ClassUtils;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by LiuJie on 2016/5/4 10:19.
 */
public class AopClient {


    @Before
    public void setup() throws Exception {
        //初始化
        FrameworkLoader.init();
    }

    @Test
    public void testAop() {
        Set<Class<?>> aspectClassSet = ClassUtils.getAspectClassSet();
        System.out.println(aspectClassSet);
        List<Proxy> proxyList = new ArrayList<>();
        ProxyManager manager = new ProxyManager();
        for (Class<?> clazz : aspectClassSet) {
            try {
                Proxy instance = (Proxy) clazz.newInstance();
                proxyList.add(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (clazz.isAnnotationPresent(Aspect.class)) {
                Aspect annotation = clazz.getAnnotation(Aspect.class);
                Class<? extends Annotation> ann = annotation.value();
                Set<Class<?>> serviceBeanClassSet = ClassUtils.getClassSetByAnnotation(ann);
                for (Class<?> clz : serviceBeanClassSet) {
                    HelloService service = (HelloService) manager.getProxy(clz, proxyList);
                    service.sayHi("world");
                }
            }
        }
    }

    @Test
    public void testTransaction() throws Exception {
        UserService userService = BeanContainer.getBean(UserService.class);
        int update = userService.updateUserPassword(1, "11111");
        System.out.println(update);
    }
}
