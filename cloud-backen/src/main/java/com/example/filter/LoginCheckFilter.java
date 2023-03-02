package com.example.filter;


import com.alibaba.fastjson.JSON;

import com.example.utils.BaseContext;
import com.example.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
//@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        String requestURI= request.getRequestURI();
        String[] urls=new String[]{
                "**",
        };

        /*boolean check= check(urls,requestURI);
        System.out.println(check);
        if(check){*/
            Long userId= (Long) request.getSession().getAttribute("user");
            System.out.println(userId);
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request,response);
            return ;
        /*}*/

       /* if(request.getSession().getAttribute("manager")!=null){

            Long empId= (Long) request.getSession().getAttribute("manager");
            BaseContext.setCurrentId(empId);
//            Long id=Thread.currentThread().getId();
//            log.info("线程id为：{}",id);
            filterChain.doFilter(request,response);
            return;
        }*/

        //
        /*if(request.getSession().getAttribute("user")!=null){

            Long userId= (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
//            Long id=Thread.currentThread().getId();
//            log.info("线程id为：{}",id);
            filterChain.doFilter(request,response);
            return;
        }
        response.sendRedirect("/front/page/login-register/login.html");

        return;*/
    }
    public boolean check(String[] urls,String requsetURI){
        for(String url:urls){
            boolean match=PATH_MATCHER.match(url,requsetURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
