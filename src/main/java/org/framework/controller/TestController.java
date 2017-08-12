package org.framework.controller;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.framework.annotation.Controller;
import org.framework.annotation.HandlerMapping;
import org.framework.utils.FileUploadHandler;
import org.framework.web.FileRequest;
import org.framework.web.WebRequestParameterBinder;
import org.framework.web.core.JsonResponseView;
import org.framework.web.core.PageView;

import java.net.URL;
import java.util.Date;

/**
 * Author: jonny
 * Time: 2017-08-11 23:32.
 */
@Controller
public class TestController {


    @HandlerMapping(method = "get", requestUrl = "/time")
    public JsonResponseView getTime(WebRequestParameterBinder parameterBinder) {
        String name = parameterBinder.getString("name");
        String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss");
        return JsonResponseView.buildSuccessResponse("hai," + name + ", date: " + date);
    }

    @HandlerMapping(method = "post", requestUrl = "/upload")
    public JsonResponseView uploadFile(WebRequestParameterBinder parameterBinder) {
        FileRequest file = parameterBinder.getFile("file");
        URL resource = Thread.currentThread().getContextClassLoader().getResource("");
        FileUploadHandler.uploadFile(resource.getPath(), file);
        return JsonResponseView.buildSuccessResponse("upload file success!");
    }

    @HandlerMapping(method = "get", requestUrl = "/")
    public PageView toIndex() {
        return new PageView("index.html");
    }

}
