package org.framework.utils;

import org.framework.annotation.Controller;
import org.framework.aop.Proxy;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Set;

public class TestClassUtils {

    private final Logger LOGGER = LoggerFactory
            .getLogger(TestClassUtils.class);

    /**
     * 测试获取类加载器
     */
    @Test
    public void testGetClassLoader() {
        this.LOGGER.info(ClassUtils.getClassLoader().toString());
    }

    /**
     * 测试加载类，并执行其静态代码块
     */
    @Test
    public void testLoadClass() {
        this.LOGGER.info(ClassUtils.loadClass("java.lang.String", true).toString());
    }

    /**
     * 测试加载框架基本包下所有类
     */
    @Test
    public void testGetClassSet() {
        String appBasePath = ConfigUtils.getAppBasePath();
        Assert.assertNotNull(appBasePath);
        Assert.assertEquals("org.framework", appBasePath);
        Set<Class<?>> classSet = ClassUtils.getClassSet(appBasePath);
        Assert.assertTrue(classSet != null);
        Assert.assertFalse(classSet.isEmpty());
        this.LOGGER.info(classSet.toString());
    }

    /**
     * 测试获取包下的所有controller
     */
    @Test
    public void testGetControllerClassSet() {
        Set<Class<?>> controllerClassSet = ClassUtils.getControllerClassSet();
        Assert.assertTrue(controllerClassSet != null);
        Assert.assertFalse(controllerClassSet.isEmpty());
        this.LOGGER.info(Arrays.toString(controllerClassSet.toArray()));
    }

    /**
     * 测试获取包下所有service
     */
    @Test
    public void testGetServiceClassSet() {
        Set<Class<?>> serviceClassSet = ClassUtils.getServiceClassSet();
        Assert.assertTrue(serviceClassSet != null);
        Assert.assertFalse(serviceClassSet.isEmpty());
        this.LOGGER.info(Arrays.toString(serviceClassSet.toArray()));
    }

    /**
     * 测试加载指定类，并不执行静态代码块
     */
    @Test
    public void testLoadClassWithoutInitial() {
        Class<?> clazz = ClassUtils.loadClass(AopUtils.class.getName());
        System.out.println(clazz);
    }

    /**
     * 测试加载指定类，并执行静态代码块
     */
    @Test
    public void testLoadClassWithInitial() {
        Class<?> clazz = ClassUtils.loadClass(AopUtils.class.getName(), true);
        System.out.println(clazz);
    }

    /**
     * 测试获取所有切面类(AspectProxy子类并且带有Aspect注解)
     */
    @Test
    public void testGetAspectClassSet() {
        Set<Class<?>> aspectClassSet = ClassUtils.getAspectClassSet();
        Assert.assertTrue(aspectClassSet != null);
        Assert.assertFalse(aspectClassSet.isEmpty());
        this.LOGGER.info(Arrays.toString(aspectClassSet.toArray()));
    }

    /**
     * 测试获取指定注解下的所有类
     */
    @Test
    public void testGetClassSetByAnnotation() {
        Set<Class<?>> classSet = ClassUtils.getClassSetByAnnotation(Controller.class);
        Assert.assertFalse(classSet == null);
        Assert.assertFalse(classSet.isEmpty());
        this.LOGGER.info(Arrays.toString(classSet.toArray()));
    }

    /**
     * 测试获取指定父类(接口)的所有子类(实现类)
     */
    @Test
    public void testGetClassSetBySuperClass() {
        Set<Class<?>> classSet = ClassUtils.getClassSetBySuperClass(Proxy.class);
        Assert.assertFalse(classSet == null);
        Assert.assertFalse(classSet.isEmpty());
        this.LOGGER.info(Arrays.toString(classSet.toArray()));
    }

}
