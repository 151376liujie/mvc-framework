package org.framework;

import org.framework.utils.AopUtils;
import org.framework.utils.ClassUtils;
import org.framework.utils.ControllerUtils;
import org.framework.utils.IocUtils;

public final class FrameworkLoader {

    /**
     * 初始化工作，加载所有class、加载所有bean组成BeanMap、创建代理对象、实现自动注入、获取所有controller类
     */
    public static void init() {
        Class<?>[] classList = {
                ClassUtils.class,
                BeanContainer.class,
                AopUtils.class,
                IocUtils.class,
                ControllerUtils.class
        };
        for (Class<?> clazz : classList) {
            ClassUtils.loadClass(clazz.getName(), true);
        }
    }

}
