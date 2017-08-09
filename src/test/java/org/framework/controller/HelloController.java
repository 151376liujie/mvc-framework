package org.framework.controller;

import org.framework.annotation.ActionMapping;
import org.framework.annotation.Controller;
import org.framework.annotation.Inject;
import org.framework.model.JsonResponseData;
import org.framework.model.PageView;
import org.framework.model.RequestParameter;
import org.framework.model.User;
import org.framework.service.UserService;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by liujie on 2016/5/6 19:56.
 */

@Controller
public class HelloController {

    @Inject
    private UserService userService;

    @ActionMapping(method = "get", requestUrl = "/users")
    public JsonResponseData<User> users() throws Exception {
        List<User> users = userService.getAllUser();
        JsonResponseData responseData = new JsonResponseData(0, "ok");
        responseData.setData(users);
        return responseData;
    }

    @ActionMapping(requestUrl = "/", method = "get")
    public PageView toIndex() {
        return new PageView("index.html");
    }

    @ActionMapping(method = "get", requestUrl = "/updateUser")
    public JsonResponseData updateUser(RequestParameter parameter) throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String id = parameter.getString("id");
        String newpass = parameter.getString("newpass");
        this.userService.updateUserPassword(Integer.parseInt(id), newpass);
        JsonResponseData responseData = new JsonResponseData(0, "ok");
        responseData.setData(simpleDateFormat.format(new Date()));
        return responseData;
    }

}
