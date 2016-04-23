package org.framework.utils;

import org.framework.BeanContainer;

public final class FrameworkLoader {

    /**
     * 初始化工作，加载所有class、加载所有bean组成BeanMap、实现自动注入、获取所有controller类
     */
    public static void init() {
	Class<?>[] classList = {
		ClassUtils.class,
		BeanContainer.class,
		IocUtils.class,
		ControllerUtils.class
 };
	for (Class<?> clazz : classList) {
	    ClassUtils.loadClass(clazz.getName());
	}
    }

}
