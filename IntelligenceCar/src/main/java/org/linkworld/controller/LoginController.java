package org.linkworld.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.linkworld.config.LoginSessionParams;
import org.linkworld.persist.emtity.Login;
import org.linkworld.persist.vo.ResultBean;
import org.linkworld.service.LoginService;
import org.linkworld.util.HuffmanTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

@RestController()
public class LoginController {
//
    @Autowired
    LoginService loginService;
    @Autowired
    ObjectMapper objectMapper;

    HuffmanTree huffmanTree=new HuffmanTree();

    @PostMapping("/login/wechatLogin")
    public ResultBean WechatLogin(HttpServletRequest httpServletRequest, HttpServletResponse response, @RequestParam("code") String code){
        HttpSession httpSession=httpServletRequest.getSession();
        try {
            loginService.wechatLogin(code,httpSession);
        } catch (Exception e) {
            e.printStackTrace();
            return loginNum(httpSession,ResultBean.bad());
        }

        Login login=new Login();
        login.setUserId((BigInteger) httpSession.getAttribute(LoginSessionParams.userLogin));
        login.setOpenId((String) httpSession.getAttribute(LoginSessionParams.wechatLogin));
        try {
            response.setHeader("token",new String(huffmanTree.encrypt(objectMapper.writeValueAsString(login)),StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return loginNum(httpSession,ResultBean.ok());
    }

    @PostMapping("/login/userLogin")
    public ResultBean UserLogin(HttpServletRequest httpServletRequest, HttpServletResponse response, @RequestParam("userId") BigInteger userId, @RequestParam("password") String password){
        HttpSession httpSession=httpServletRequest.getSession();
        try {
            loginService.userLogin(userId, password,httpSession);

        }catch (Exception e){
            e.printStackTrace();
            return loginNum(httpSession,ResultBean.bad());
        }
        Login login=new Login();
        login.setUserId((BigInteger) httpSession.getAttribute(LoginSessionParams.userLogin));
        login.setOpenId((String) httpSession.getAttribute(LoginSessionParams.wechatLogin));
        try {
            response.setHeader("token",new String(huffmanTree.encrypt(objectMapper.writeValueAsString(login)), StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
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
