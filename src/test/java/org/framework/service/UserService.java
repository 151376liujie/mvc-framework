package org.framework.service;

import org.framework.annotation.NeedTransaction;
import org.framework.annotation.Service;
import org.framework.utils.DatabaseUtils;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * Author: jonny
 * Time: 2017-08-07 22:54.
 */
@Service
public class UserService {

    @NeedTransaction
    public int update(String name) throws SQLException {
        int update = DatabaseUtils.executeUpdate("update t_user set password=? where id=?", Arrays.asList("456", 1).toArray());
        return update;
    }

}
