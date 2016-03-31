package org.framework.java.utils;

import java.sql.SQLException;
import java.util.List;

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

}
