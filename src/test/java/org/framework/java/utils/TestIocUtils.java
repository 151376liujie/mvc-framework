package org.framework.java.utils;

import org.framework.java.BeanContainer;
import org.framework.java.controller.HelloServlet;
import org.junit.Test;

/**
 * 控制反转工具类测试用例
 * 
 * @author liujie
 * 
 */
public class TestIocUtils {

    @Test
    public void testDoInject() {
	HelloServlet bean = BeanContainer.getBean(HelloServlet.class);
	System.out.println(bean.getHelloService());
	IocUtils.doInject();
	bean = BeanContainer.getBean(HelloServlet.class);
	System.out.println(bean.getHelloService());
    }

}
