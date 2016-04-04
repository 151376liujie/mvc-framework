package org.framework.java.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.framework.java.HelloService;
import org.framework.java.annotation.Controller;
import org.framework.java.annotation.Inject;

@Controller
public class HelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    private HelloService helloService;

    public HelloService getHelloService() {
	return helloService;
    }

    public HelloServlet() {
	super();
    }

    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	this.doPost(request, response);
    }

    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String now = sdf.format(new Date());
	request.setAttribute("currentTime", now);
	request.getRequestDispatcher("WEB-INF/views/hello.jsp").forward(
		request, response);
    }

}
