package org.framework.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性文件工具类
 *
 * @author 刘杰
 */
public final class PropertiesUtils {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(PropertiesUtils.class);

    /**
     * 加载属性文件
     *
     * @param fileName
     * @return
     */
    public static Properties loadProperties(String fileName) {
        InputStream resource = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(fileName);
        if (resource == null) {
            return null;
        }
        Properties properties = new Properties();
        try {
            properties.load(resource);
            return properties;
        } catch (IOException e) {
            LOGGER.error("filed to load properties", e);
            return null;
        } finally {
            if (resource != null) {
                try {
                    resource.close();
                    resource = null;
                } catch (IOException e) {
                    LOGGER.error("failed to close input stream ", e);
                }
            }
        }
    }

    /**
     * 返回string 型属性值
     *
     * @param properties
     * @param key
     * @return
     */
    public static String getString(Properties properties, String key) {
        return getString(properties, key, "");
    }

    /**
     * 返回string 型属性值(可指定默认值)
     *
     * @param properties
     * @param key
     * @param defaultValue
     * @return
     */

    public static String getString(Properties properties, String key,
                                   String defaultValue) {
        if (properties == null) {
            return key;
        }
        return properties.getProperty(key, defaultValue);
    }

    /**
     * 得到整形属性值
     *
     * @param properties
     * @param key
     * @return
     */
    public static int getInt(Properties properties, String key) {
        return getInt(properties, key, 0);
    }

    /**
     * 得到整形属性值（可指定默认值）
     *
     * @param properties
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(Properties properties, String key, int defaultValue) {
        if (properties == null) {
            return defaultValue;
        }
        String property = properties.getProperty(key);
        if (StringUtils.isNotEmpty(property)) {
            return Integer.valueOf(property);
        }
        return defaultValue;
    }

    /**
     * 获取布尔型的属性值
     *
     * @param properties
     * @param key
     * @return
     */
    public static boolean getBoolean(Properties properties, String key) {
        return getBoolean(properties, key, false);
    }

    /**
     * 获取布尔类型的属性值（可以指定默认值）
     *
     * @param properties
     * @param key
     * @param defaultVal
     * @return
     */
    public static boolean getBoolean(Properties properties, String key,
                                     boolean defaultVal) {
        if (properties == null) {
            return defaultVal;
        }
        String property = properties.getProperty(key);
        if (StringUtils.isNotEmpty(property)) {
            return Boolean.valueOf(property);
        }
        return defaultVal;
    }

}
