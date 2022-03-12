package org.linkworld.controller;

import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.linkworld.config.LoginSessionParams;
import org.linkworld.persist.vo.ResultBean;
import org.linkworld.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController()
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login/wechatLogin")
    public ResultBean WechatLogin(String code){
        try {
            loginService.wechatLogin(code);
        } catch (Exception e) {
            e.printStackTrace();
            return loginNum(ResultBean.bad());
        }
        return loginNum(ResultBean.ok());
    }

    @PostMapping("/login/userLogin")
    public ResultBean UserLogin(BigInteger userId,String password){
        try {
            loginService.userLogin(userId, password);

        }catch (Exception e){
            e.printStackTrace();
            return loginNum(ResultBean.bad());
        }
       return loginNum(ResultBean.ok());
    }


    public ResultBean loginNum(ResultBean resultBean){
        Session session=SecurityUtils.getSubject().getSession();
        if(session.getAttribute(LoginSessionParams.userLogin)!=null){
            resultBean.setUserLogin(1);
        }

        if(session.getAttribute(LoginSessionParams.wechatLogin)!=null){
            resultBean.setWechatLogin(1);
        }
        return resultBean;
    }
}
