package org.framework.controller;

import org.framework.annotation.ActionMapping;
import org.framework.annotation.Controller;
import org.framework.annotation.Inject;
import org.framework.service.UserService;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liujie on 2016/5/6 19:56.
 */

@Controller
public class HelloController {

    @Inject
    private UserService userService;

    @ActionMapping(method = "get", requestUrl = "/getTime")
    public String getServerTime() throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int update = this.userService.update("haha");
        System.out.println(update);
        return simpleDateFormat.format(new Date());
    }

}
