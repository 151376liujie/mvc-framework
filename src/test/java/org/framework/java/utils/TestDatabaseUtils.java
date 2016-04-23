package org.framework.java.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.framework.model.Customer;
import org.framework.utils.DatabaseUtils;
import org.junit.Assert;
import org.junit.Ignore;

public class TestDatabaseUtils {

    @Ignore
    public void testGetConnection() {
	Connection connection = DatabaseUtils.getConnection();
	Assert.assertNotNull(connection);
	System.out.println(connection);
    }

    @Ignore
    public void testQueryEntityList() throws Exception {
	String sql = "select * from customer";
	List<Customer> list = DatabaseUtils.queryForList(Customer.class, sql,
		null);
	Assert.assertTrue(!list.isEmpty());
	System.out.println(list);
    }

    @Ignore
    public void testQueryEntity() throws SQLException {
	String sql = " select * from customer where id=? ";
	Customer entity = DatabaseUtils.queryForEntity(Customer.class, sql,
		new Object[] { 1 });
	Assert.assertNotNull(entity);
	System.out.println(entity);
    }

    @Ignore
    public void testInsertEntity() throws SQLException {
	/*
	 * Customer customer = new Customer(); customer.setAddr("河南省固始县");
	 * customer.setMobile("13520443610"); customer.setName("刘杰");
	 */
	Map<String, Object> fieldMap = new HashMap<String, Object>();
	fieldMap.put("name", "刘杰");
	fieldMap.put("addr", "河南省固始县");
	fieldMap.put("mobile", "13520443610");
	long row = DatabaseUtils.insertEntity(Customer.class, fieldMap);
	System.out.println(row);
    }

    @Ignore
    public void testUpdateEntity() throws SQLException {
	Map<String, Object> fieldMap = new HashMap<String, Object>();
	fieldMap.put("name", "XXX");
	long row = DatabaseUtils.updateEntity(Customer.class, 3, fieldMap);
	System.out.println(row);
    }

    @Ignore
    public void testDeleteEntity() throws SQLException {
	long row = DatabaseUtils.deleteEntity(Customer.class, 3);
	System.out.println(row);
    }

}
