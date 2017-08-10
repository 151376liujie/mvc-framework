package org.framework.controller;

import org.framework.annotation.Controller;
import org.framework.annotation.HandlerMapping;
import org.framework.annotation.Inject;
import org.framework.model.User;
import org.framework.service.UserService;
import org.framework.web.WebRequestParameterHolder;
import org.framework.web.core.JsonResponseData;
import org.framework.web.core.PageView;

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

    @HandlerMapping(method = "get", requestUrl = "/users")
    public JsonResponseData<User> users() throws Exception {
        List<User> users = userService.getAllUser();
        JsonResponseData responseData = new JsonResponseData(0, "ok");
        responseData.setData(users);
        return responseData;
    }

    @HandlerMapping(requestUrl = "/", method = "get")
    public PageView toIndex() {
        return new PageView("index.html");
    }

    @HandlerMapping(method = "get", requestUrl = "/updateUser")
    public JsonResponseData updateUser(WebRequestParameterHolder parameter) throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String id = parameter.getString("id");
        String newpass = parameter.getString("newpass");
        this.userService.updateUserPassword(Integer.parseInt(id), newpass);
        JsonResponseData responseData = new JsonResponseData(0, "ok");
        responseData.setData(simpleDateFormat.format(new Date()));
        return responseData;
    }

}
