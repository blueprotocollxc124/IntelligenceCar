package org.linkworld.service;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;

public interface LoginService {

    void wechatLogin(String code,HttpSession httpSession)throws Exception;

    void userLogin(BigInteger user, String password, HttpSession httpSession);


}
