package org.framework.utils;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 数据库操作工具类
 *
 * @author 刘杰
 */
public final class DatabaseUtils {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(DatabaseUtils.class);
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();
    private static final String PROP_DRIVER_CLASS_NAME = "driverClassName";
    private static final String PROP_URL = "url";
    private static final String PROP_USERNAME = "username";
    private static final String PROP_PASSWORD = "password";
    private static final String JDBC_PROPERTIES_FILE = "jdbc.properties";
    private static final String KEY_JDBC_DRIVER = "jdbc.driver";
    private static final String KEY_JDBC_URL = "jdbc.url";
    private static final String KEY_JDBC_USERNAME = "jdbc.username";
    private static final String KEY_JDBC_PASSWORD = "jdbc.password";
    private static BasicDataSource DATA_SOURCE = null;

    static {
        Properties properties = PropertiesUtils
                .loadProperties(JDBC_PROPERTIES_FILE);

        Properties jdbcProperties = new Properties();
        jdbcProperties.setProperty(PROP_DRIVER_CLASS_NAME, PropertiesUtils.getString(properties, KEY_JDBC_DRIVER));
        jdbcProperties.setProperty(PROP_URL, PropertiesUtils.getString(properties, KEY_JDBC_URL));
        jdbcProperties.setProperty(PROP_USERNAME, PropertiesUtils.getString(properties, KEY_JDBC_USERNAME));
        jdbcProperties.setProperty(PROP_PASSWORD, PropertiesUtils.getString(properties, KEY_JDBC_PASSWORD));
        try {
            DATA_SOURCE = BasicDataSourceFactory.createDataSource(jdbcProperties);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * 获取链接
     *
     * @return
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = CONNECTION_HOLDER.get();
            if (connection == null) {
                connection = DATA_SOURCE.getConnection();
                CONNECTION_HOLDER.set(connection);
            }
        } catch (Exception e) {
            LOGGER.error("failed to fetch a connection!", e);
        }
        return connection;
    }

    /**
     * 关闭链接
     */
    public static void closeConnection() {
        Connection connection = CONNECTION_HOLDER.get();
        if (connection != null) {
            try {
                connection.close();
                CONNECTION_HOLDER.remove();
            } catch (Exception e) {
                LOGGER.error("failed to close connection !", e);
            }
        }
    }

    /**
     * 执行update语句，返回受影响行数
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static int executeUpdate(String sql, Object[] params)
            throws SQLException {
        Connection connection = getConnection();
        int row = QUERY_RUNNER.update(connection, sql, params);
//        closeConnection();
        return row;
    }

    /**
     * 查询列表
     *
     * @param clazz
     * @param sql
     * @param params
     * @return
     * @throws Exception
     */
    public static <T> List<T> queryForList(Class<T> clazz, String sql,
                                           Object[] params) throws Exception {
        Connection connection = getConnection();
        List<T> list = QUERY_RUNNER.query(connection, sql,
                new BeanListHandler<>(clazz), params);
        closeConnection();
        return list;
    }

    /**
     * 查询单个实体对象
     *
     * @param clazz
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static <T> T queryForEntity(Class<T> clazz, String sql,
                                       Object[] params) throws SQLException {
        Connection connection = getConnection();
        T entity = QUERY_RUNNER.query(connection, sql,
                new BeanHandler<>(clazz), params);
        closeConnection();
        return entity;
    }

    /**
     * 插入单个实体
     *
     * @param clazz
     * @param fieldMap
     * @return
     * @throws SQLException
     */
    public static <T> long insertEntity(Class<T> clazz,
                                        Map<String, Object> fieldMap) throws SQLException {
        StringBuilder sql = new StringBuilder();
        StringBuilder placeHolder = new StringBuilder();
        sql.append(" INSERT INTO ").append(getTableName(clazz)).append("(");
        List<Object> paramsList = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            int modifiers = field.getModifiers();
            if (Modifier.isFinal(modifiers)) {
                continue;
            }
            sql.append(field.getName()).append(",");
            placeHolder.append("?").append(",");
            paramsList.add(fieldMap.get(field.getName()));
        }
        sql.deleteCharAt(sql.length() - 1);
        placeHolder.deleteCharAt(placeHolder.length() - 1);
        sql.append(")").append(" VALUES").append("(");
        sql.append(placeHolder).append(")");
        long row = executeUpdate(sql.toString(), paramsList.toArray());
        return row;
    }

    /**
     * 更新一个实体
     *
     * @param clazz
     * @param id
     * @param fieldMap
     * @return
     * @throws SQLException
     */
    public static <T> int updateEntity(Class<T> clazz, long id,
                                       Map<String, Object> fieldMap) throws SQLException {
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE ").append(getTableName(clazz)).append(" SET ");
        for (Entry<String, Object> entry : fieldMap.entrySet()) {
            sql.append(entry.getKey()).append("=?").append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(" WHERE id = ?");
        params.addAll(fieldMap.values());
        params.add(id);
        return executeUpdate(sql.toString(), params.toArray());
    }

    /**
     * 根据id删除实体
     *
     * @param clazz
     * @param id
     * @return
     * @throws SQLException
     */
    public static <T> int deleteEntity(Class<T> clazz, long id)
            throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(getTableName(clazz))
                .append(" WHERE id =?");
        return executeUpdate(sql.toString(), new Object[]{id});
    }

    /**
     * 提交事务
     */
    public static void beginTransaction() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.setAutoCommit(false);
                CONNECTION_HOLDER.set(connection);
            } catch (SQLException e) {
                LOGGER.error("error to start a transaction", e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.commit();
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("error to commit a transaction", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("error to rollback a transaction", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 根据class获取表名
     *
     * @param clazz
     * @return
     */
    private static <T> String getTableName(Class<T> clazz) {
        return clazz.getSimpleName();
    }

}
