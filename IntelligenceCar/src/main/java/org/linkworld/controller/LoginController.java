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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@RestController()
public class LoginController {
//
    @Autowired
    LoginService loginService;
    @Autowired
    ObjectMapper objectMapper;



    @PostMapping("/login/wechatLogin")
    public ResultBean WechatLogin(HttpServletRequest httpServletRequest, @RequestHeader(value = "token",required = false) String token, HttpServletResponse response, @RequestParam("code") String code){
        HttpSession httpSession=httpServletRequest.getSession();
        try {
            loginService.wechatLogin(code,httpSession);
        } catch (Exception e) {
            e.printStackTrace();
            return loginNum(httpSession,ResultBean.bad());
        }

        Login login=new Login((BigInteger) httpSession.getAttribute(LoginSessionParams.userLogin),(String) httpSession.getAttribute(LoginSessionParams.wechatLogin));

        try {
            HuffmanTree huffmanTree=new HuffmanTree();
            response.setHeader("token",byteToString(huffmanTree.encrypt(objectMapper.writeValueAsString(login))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return loginNum(httpSession,ResultBean.ok());
    }

    @PostMapping("/login/userLogin")
    public ResultBean UserLogin(HttpServletRequest httpServletRequest, HttpServletResponse response, @RequestHeader(value = "token",required = false) String token, @RequestParam("userId") BigInteger userId, @RequestParam("password") String password){
        HttpSession httpSession=httpServletRequest.getSession();
        try {
            loginService.userLogin(userId, password,httpSession);

        }catch (Exception e){
            e.printStackTrace();
            return loginNum(httpSession,ResultBean.bad());
        }
        Login login=new Login((BigInteger) httpSession.getAttribute(LoginSessionParams.userLogin),(String) httpSession.getAttribute(LoginSessionParams.wechatLogin));
        try {
            HuffmanTree huffmanTree=new HuffmanTree();
            response.setHeader("token",byteToString(huffmanTree.encrypt(objectMapper.writeValueAsString(login))));


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

    String byteToString(byte[] b){
        String s=new String(""+b[0]);
        for(int i=1;i<b.length;i++){
            s=s+","+(b[i]);
        }

        return s;
    }

}
