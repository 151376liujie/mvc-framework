package org.framework.java.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库操作工具类
 * 
 * @author 刘杰
 * 
 */
public final class DatabaseUtils {

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(DatabaseUtils.class);
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();

    private static final String driverClass;
    private static final String url;
    private static final String userName;
    private static final String pass;
    private static final String JDBC_PROPERTIES_FILE = "jdbc.properties";
    private static final String KEY_JDBC_DRIVER = "jdbc.driver";
    private static final String KEY_JDBC_URL = "jdbc.url";
    private static final String KEY_JDBC_USERNAME = "jdbc.username";
    private static final String KEY_JDBC_PASSWORD = "jdbc.password";

    static {
	Properties properties = PropertiesUtils
		.loadProperties(JDBC_PROPERTIES_FILE);
	driverClass = PropertiesUtils.getString(properties, KEY_JDBC_DRIVER);
	url = PropertiesUtils.getString(properties, KEY_JDBC_URL);
	userName = PropertiesUtils.getString(properties, KEY_JDBC_USERNAME);
	pass = PropertiesUtils.getString(properties, KEY_JDBC_PASSWORD);
    }

    /**
     * 获取链接
     * 
     * @return
     */
    public static Connection getConnection() {
	try {
	    Connection connection = CONNECTION_HOLDER.get();
	    if (connection == null) {
		Class.forName(driverClass);
		connection = DriverManager.getConnection(url, userName, pass);
		CONNECTION_HOLDER.set(connection);
		return connection;
	    }
	} catch (Exception e) {
	    LOGGER.error("failed to fetch a connection!", e);
	}
	return null;
    }

    /**
     * 关闭链接
     * 
     * @param connection
     */
    public static void closeConnection() {
	Connection connection = CONNECTION_HOLDER.get();
	if (connection != null) {
	    try {
		connection.close();
		connection = null;
		CONNECTION_HOLDER.remove();
	    } catch (Exception e) {
		LOGGER.error("failed to close connection !", e);
	    }
	}
    }

    /**
     * 执行update语句，返回受影响行数
     * 
     * @param connection
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static int executeUpdate(String sql, Object[] params)
	    throws SQLException {
	Connection connection = getConnection();
	int row = QUERY_RUNNER.update(connection, sql, params);
	closeConnection();
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
		new BeanListHandler<T>(clazz), params);
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
		new BeanHandler<T>(clazz), params);
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
	List<Object> paramsList = new ArrayList<Object>();
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
	List<Object> params = new ArrayList<Object>();
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
	return executeUpdate(sql.toString(), new Object[] { id });

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
