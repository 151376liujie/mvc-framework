package org.framework.service;

import org.framework.annotation.NeedTransaction;
import org.framework.annotation.Service;
import org.framework.model.User;
import org.framework.utils.DatabaseUtils;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Author: jonny
 * Time: 2017-08-07 22:54.
 */
@Service
public class UserService {

    @NeedTransaction
    public int updateUserPassword(int id, String newpass) throws SQLException {
        int update = DatabaseUtils.executeUpdate("update t_user set password=? where id=?", Arrays.asList(newpass, id).toArray());
        return update;
    }

    public List<User> getAllUser() throws Exception {
        List<User> users = DatabaseUtils.queryForList(User.class, "select * from t_user", null);
        return users;
    }

}
