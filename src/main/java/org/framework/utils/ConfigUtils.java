package org.framework.utils;

import org.framework.ConfigConstant;

import java.util.Properties;

/**
 * 配置工具类，提供获取各种配置的值
 *
 * @author liujie
 */
public final class ConfigUtils {

    private static final Properties PROPERTIES = PropertiesUtils
            .loadProperties(ConfigConstant.CONFIG_FILE);

    /**
     * 获取jdbc驱动
     *
     * @return
     */
    public static String getJdbcDriver() {
        return PropertiesUtils
                .getString(PROPERTIES, ConfigConstant.JDBC_DRIVER);
    }

    /**
     * 获取jdbc url
     *
     * @return
     */
    public static String getJdbcUrl() {
        return PropertiesUtils.getString(PROPERTIES, ConfigConstant.JDBC_URL);
    }

    /**
     * 获取jdbc username
     *
     * @return
     */
    public static String getJdbcUsername() {
        return PropertiesUtils.getString(PROPERTIES,
                ConfigConstant.JDBC_USERNAME);
    }

    /**
     * 获取jdbc password
     *
     * @return
     */
    public static String getJdbcPassword() {
        return PropertiesUtils.getString(PROPERTIES,
                ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * 获取应用基础包名
     *
     * @return
     */
    public static String getAppBasePath() {
        return PropertiesUtils.getString(PROPERTIES,
                ConfigConstant.FRAMEWORK_BASE_PACKAGE);
    }

    /**
     * 获取视图路径
     *
     * @return
     */
    public static String getAppViewPath() {
        return PropertiesUtils.getString(PROPERTIES,
                ConfigConstant.FRAMEWORK_VIEW_PATH, "/WEB-INF/views/ ");
    }

    /**
     * 获取静态资源包路径
     *
     * @return
     */
    public static String getAppWebResourcePath() {
        return PropertiesUtils.getString(PROPERTIES,
                ConfigConstant.FRAMEWORK_WEB_RESOURCE_PATH, "/assert/");
    }

    /**
     * 获取单个上传文件最大size(单位M)
     *
     * @return
     */
    public static long getUploadFileSizeMax() {
        return PropertiesUtils.getLong(PROPERTIES, ConfigConstant.FRAMEWORK_UPLOAD_FILE_MAX_SIZE, 10);
    }
}
