package org.framework.java.utils;

import org.framework.utils.PropertiesUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

public class TestPropertiesUtils {

    private Properties properties;

    @Before
    public void init() {
        properties = PropertiesUtils.loadProperties("jdbc.properties");
    }

    @Test
    public void testLoadProperties() {
        Assert.assertNotNull(properties);
        System.out.println(properties);
    }

    @Test
    public void testGetStringPropertiesString() {
        Assert.assertNotNull(properties);
        System.out.println(PropertiesUtils.getString(properties, "jdbc.url"));
    }

    @Test
    public void testGetStringPropertiesStringString() {
        Assert.assertNotNull(properties);
        System.out.println(PropertiesUtils.getString(properties, "jdbc.url",
                "default jdbc url"));
        System.out.println(PropertiesUtils.getString(properties, "jdbc.urls",
                "default jdbc url value"));
    }

    @Test
    public void testGetIntPropertiesString() {
        Assert.assertNotNull(properties);
        System.out.println(PropertiesUtils.getInt(properties, "jdbc.int"));
    }

    @Test
    public void testGetIntPropertiesStringString() {
        Assert.assertNotNull(properties);
        System.out.println(PropertiesUtils.getInt(properties, "jdbc.ints", 99));
    }

    @Test
    public void testBooleanPropertiesString() {
        Assert.assertNotNull(properties);
        System.out.println(PropertiesUtils.getBoolean(properties,
                "jdbc.boolean"));
    }

    @Test
    public void testBooleanPropertiesStringBoolean() {
        Assert.assertNotNull(properties);
        System.out.println(PropertiesUtils.getBoolean(properties,
                "jdbc.booleans", false));
    }

}
