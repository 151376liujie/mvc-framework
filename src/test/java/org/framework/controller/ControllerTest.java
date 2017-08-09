package org.framework.controller;

import org.framework.bean.ActionHandler;
import org.framework.utils.ConfigUtils;
import org.framework.utils.ControllerUtils;
import org.framework.web.dispatcher.FrameworkServlet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

import static org.mockito.Mockito.*;

/**
 * Author: jonny
 * Time: 2017-08-06 11:07.
 */
public class ControllerTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FrameworkServlet frameworkServlet;
    private RequestDispatcher requestDispatcher;
    private ServletContext servletContext;


    @Before
    public void setUp() throws Exception {
        ServletConfig servletConfig = mock(ServletConfig.class);
        servletContext = mock(ServletContext.class);
        ServletRegistration jspServletRegistration = mock(ServletRegistration.class);
        ServletRegistration defaultServletRegistration = mock(ServletRegistration.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        requestDispatcher = mock(RequestDispatcher.class);
        frameworkServlet = new FrameworkServlet();

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getServletRegistration("jsp")).thenReturn(jspServletRegistration);

        when(servletContext.getServletRegistration("default")).thenReturn(defaultServletRegistration);

        frameworkServlet.init(servletConfig);
    }

    @Test
    public void testLocateActionHandler() {

        ActionHandler actionHandler = ControllerUtils.getActionHandler("/hello", "get");
        Assert.assertNull(actionHandler);

        ActionHandler handler = ControllerUtils.getActionHandler("/updateUser", "get");
        Assert.assertNotNull(handler);
        System.out.println(handler);

    }

    @Test
    public void testGetAllUser() throws Exception {
        when(request.getPathInfo()).thenReturn("/users");
        when(request.getMethod()).thenReturn("get");
        when(request.getParameterNames()).thenReturn(Collections.emptyEnumeration());
        when(request.getInputStream()).thenReturn(new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        });
        when(request.getContextPath()).thenReturn("/");
        StringWriter stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        frameworkServlet.service(request, response);
        String response = stringWriter.getBuffer().toString();
        Assert.assertNotNull(response);
        Assert.assertFalse(response.isEmpty());
        System.out.println(response);

    }

    @Test
    public void testUpdateUser() throws Exception {
        when(request.getPathInfo()).thenReturn("/updateUser");
        when(request.getMethod()).thenReturn("get");
        Enumeration<String> enumeration = Collections.enumeration(Arrays.asList("id", "newpass"));
        when(request.getParameterNames()).thenReturn(enumeration);
        when(request.getInputStream()).thenReturn(new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        });
        when(request.getContextPath()).thenReturn("/");
        when(request.getParameterValues("id")).thenReturn(new String[]{"1"});
        when(request.getParameterValues("newpass")).thenReturn(new String[]{"newpass"});
        StringWriter stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        frameworkServlet.service(request, response);
        String response = stringWriter.getBuffer().toString();
        Assert.assertNotNull(response);
        Assert.assertFalse(response.isEmpty());
        System.out.println(response);

    }

    @Test
    public void testAccessPage() throws Exception {
        when(request.getPathInfo()).thenReturn("/");
        when(request.getMethod()).thenReturn("get");
        when(request.getParameterNames()).thenReturn(Collections.emptyEnumeration());
        when(request.getInputStream()).thenReturn(new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        });
        when(request.getRequestDispatcher(ConfigUtils.getAppViewPath() + "index.html")).thenReturn(requestDispatcher);
        frameworkServlet.service(request, response);
        verify(requestDispatcher, times(1)).forward(request, response);
    }
}
