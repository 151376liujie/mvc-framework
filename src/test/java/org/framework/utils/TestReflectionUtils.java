package org.framework.utils;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射测试用例
 *
 * @author liujie
 */
public class TestReflectionUtils {

    @Test
    public void testNewInstance() {
        Object newInstance = ReflectionUtils.newInstance(String.class);
        Assert.assertNotNull(newInstance);
    }

    @Test
    public void testInvokeMethod() throws NoSuchMethodException,
            SecurityException {
        Object newInstance = ReflectionUtils.newInstance(String.class);
        Method method = String.class.getDeclaredMethod("length", null);
        Object result = ReflectionUtils.invokeMethod(newInstance, method, null);
        Assert.assertEquals(result, 0);
    }

    @Test
    public void testSetField() throws NoSuchFieldException, SecurityException {
        Object newInstance = ReflectionUtils.newInstance(String.class);
        Field field = String.class.getDeclaredField("hash");
        ReflectionUtils.setField(newInstance, field, 124556);
        Assert.assertEquals(124556, newInstance.hashCode());
    }


}
