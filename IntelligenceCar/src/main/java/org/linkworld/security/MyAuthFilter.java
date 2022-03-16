package org.linkworld.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.linkworld.config.LoginSessionParams;
import org.linkworld.dao.UserMapper;
import org.linkworld.persist.emtity.User;
import org.linkworld.persist.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyAuthFilter extends AuthorizationFilter {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ObjectMapper objectMapper;

    static List<String> list=new ArrayList<>();

    static {
        list.add("/doc.html");
        list.add("/login/**");
        list.add("/picture/**");
        list.add("/pattern/**");
        list.add("/swagger-ui.html#/");
        list.add("/css/**");
        list.add("/fonts/**");
        list.add("/img/**");
        list.add("/js/**");
        list.add("/v2/api-docs");
        list.add("/swagger-ui.html");
        list.add("/webjars/**");
        list.add("/v2/**");
        list.add("/swagger-resources/**");
    }
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {


        HttpServletRequest request=(HttpServletRequest) servletRequest;

        String url=request.getRequestURL().toString();

        if(list.stream().anyMatch(usl1->url.matches(url))){
            return true;
        }


        HttpSession session= request.getSession();

        BigInteger userId =(BigInteger) session.getAttribute(LoginSessionParams.userLogin);

        String openId=(String) session.getAttribute(LoginSessionParams.wechatLogin);

        if(userId==null){
            return  false;
        }

        if(openId!=null) {
            User user =userMapper.getUserByOpen(openId);
            if(!user.getId().equals(userId)){
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)   {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = resp.getWriter();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        System.out.println(objectMapper);
        try {
            out.write(objectMapper.writeValueAsString(ResultBean.notAuth()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }

        out.flush();
        out.close();
        return false;
    }

}
