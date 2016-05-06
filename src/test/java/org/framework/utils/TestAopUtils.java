package org.framework.utils;

import org.framework.BeanContainer;
import org.framework.service.HelloService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * aop工具类测试用例
 * Created by liujie on 2016/5/6 16:07.
 */
public class TestAopUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestAopUtils.class);

    @Test
    public void test() {
        ClassUtils.loadClass(AopUtils.class.getName(), true);

//        Map<Class<?>, Object> beanMap = BeanContainer.getBeanMap();

//        for (Map.Entry<Class<?>,Object> entry:beanMap.entrySet()){
//            LOGGER.info("key : {},value : {} ",entry.getKey(),entry.getValue());
//        }

        HelloService helloService = BeanContainer.getBean(HelloService.class);
        helloService.sayHi("liujie");
    }


}
