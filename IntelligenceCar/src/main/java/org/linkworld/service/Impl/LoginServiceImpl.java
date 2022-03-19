package org.linkworld.service.Impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.linkworld.config.LoginSessionParams;
import org.linkworld.dao.UserMapper;
import org.linkworld.persist.emtity.User;
import org.linkworld.security.MyUserPasswordToken;
import org.linkworld.security.UserRealm;
import org.linkworld.service.LoginService;
import org.linkworld.util.WechatUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    WechatUnit wechatUnit;

    @Autowired
    HashedCredentialsMatcher hashedMatcher;

    @Autowired
    UserMapper userMapper;

    @Override
    public void wechatLogin(String code, HttpSession session) throws Exception{
        String openId=wechatUnit.getOpenId(code);

        User user=userMapper.getUserByOpen(openId);

        Subject subject=SecurityUtils.getSubject();


        BigInteger userId=(BigInteger) session.getAttribute(LoginSessionParams.userLogin);

        session.setAttribute(LoginSessionParams.wechatLogin,openId);


        if(userId!=null){//判断以有用户登录
            if(user==null){
                //无微信与用户绑定的情况下
                userMapper.saveWechatUser(openId,userId);
                session.setAttribute(LoginSessionParams.userLogin,userId);
                return ;
            }else {
                //有绑定情况
                if(!user.getId().equals(userId)){//绑定情况不一致
                    userMapper.Update(openId,userId);
                    session.setAttribute(LoginSessionParams.userLogin,userId);
                    return ;
                }
            }
        }else {//用户未登录
            //用户未登录，微信在数据库中有绑定的用户
            if(user!=null){
                subject.login(new MyUserPasswordToken(user.getId(), user.getPassword(), UserRealm.class.getTypeName()));
                session.setAttribute(LoginSessionParams.userLogin,user.getId());
                return ;
            }

        }
        return ;

    }

    @Override
    public void userLogin(BigInteger userId, String password,HttpSession session) {

        Subject subject=SecurityUtils.getSubject();
        MyUserPasswordToken token =new MyUserPasswordToken(userId,password, UserRealm.class.getTypeName());
        subject.login(token);
        session.setAttribute(LoginSessionParams.userLogin,userId);
        String openId=(String) session.getAttribute(LoginSessionParams.wechatLogin);

        if(openId!=null){
            User user=userMapper.getWechatUserByUserId(openId);
            if(!user.getId().equals(userId)) {
                //已有微信用户登录的情况下，该用户与微信绑定用户不一致
                userMapper.Update(openId,userId);
            }

         }

        //未有微信用户
    }
}
