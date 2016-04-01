package org.framework.java.utils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.framework.java.model.Customer;
import org.junit.Assert;
import org.junit.Test;

public class TestDatabaseUtils {

    @Test
    public void testQueryEntityList() throws Exception {
	String sql = "select * from customer";
	List<Customer> list = DatabaseUtils.queryForList(Customer.class, sql, null);
	Assert.assertTrue(!list.isEmpty());
    }

    @Test
    public void testQueryEntity() throws SQLException {
	String sql = " select * from customer where id=? ";
	Customer entity = DatabaseUtils.queryForEntity(Customer.class, sql, new Object[] { 1 });
	Assert.assertNotNull(entity);
	System.out.println(entity);
    }

	@Test
	public void testInsertEntity() throws SQLException {
		/*
		 * Customer customer = new Customer(); customer.setAddr("河南省固始县");
		 * customer.setMobile("13520443610"); customer.setName("刘杰");
		 */
		Map<String, Object> fieldMap = new HashMap<>();
		fieldMap.put("name", "刘杰");
		fieldMap.put("addr", "河南省固始县");
		fieldMap.put("mobile", "13520443610");
		long row = DatabaseUtils.insertEntity(Customer.class, fieldMap);
		System.out.println(row);
	}

}