package org.linkworld.service;

import java.math.BigInteger;

public interface LoginService {

    int wechatLogin(String code)throws Exception;

    int userLogin(BigInteger user,String password);


}
