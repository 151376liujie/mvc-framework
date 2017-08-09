package org.framework.utils;

import org.framework.BeanContainer;
import org.framework.FrameworkLoader;
import org.framework.service.HelloService;
import org.junit.Before;
import org.junit.Test;

/**
 * 控制反转工具类测试用例
 *
 * @author liujie
 */
public class TestIocUtils {

    @Before
    public void setUp() throws Exception {
        FrameworkLoader.init();
    }

    @Test
    public void testDoInject() {
        HelloService helloService = BeanContainer.getBean(HelloService.class);

        helloService.sayHi("liujie");
    }

}
