package com.study.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HeaderConfig implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        System.out.println("httpRequest.getServletPath() = " + httpRequest.getServletPath());
        if (!httpRequest.getServletPath().contains("static/")) {
            httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        }
        chain.doFilter(request, response);
    }
}
