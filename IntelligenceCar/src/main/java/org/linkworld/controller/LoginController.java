package org.linkworld.controller;

import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.linkworld.config.LoginSessionParams;
import org.linkworld.persist.vo.ResultBean;
import org.linkworld.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;

@RestController()
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login/wechatLogin")
    public ResultBean WechatLogin(HttpSession httpSession,@RequestParam("code") String code){
        try {
            loginService.wechatLogin(code,httpSession);
        } catch (Exception e) {
            e.printStackTrace();
            return loginNum(httpSession,ResultBean.bad());
        }
        return loginNum(httpSession,ResultBean.ok());
    }

    @PostMapping("/login/userLogin")
    public ResultBean UserLogin(HttpSession httpSession, @RequestParam("userId") BigInteger userId,@RequestParam("password") String password){
        try {
            loginService.userLogin(userId, password,httpSession);

        }catch (Exception e){
            e.printStackTrace();
            return loginNum(httpSession,ResultBean.bad());
        }
       return loginNum(httpSession,ResultBean.ok());
    }


    public ResultBean loginNum(HttpSession session,ResultBean resultBean){
        if(session.getAttribute(LoginSessionParams.userLogin)!=null){
            resultBean.setUserLogin(1);
        }

        if(session.getAttribute(LoginSessionParams.wechatLogin)!=null){
            resultBean.setWechatLogin(1);
        }
        return resultBean;
    }
}
