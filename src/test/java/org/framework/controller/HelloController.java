package org.framework.controller;

import org.framework.annotation.ActionMapping;
import org.framework.annotation.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liujie on 2016/5/6 19:56.
 */

@Controller
public class HelloController {

    @ActionMapping(method = "post", requestUrl = "/getTime")
    public String getServerTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

}
