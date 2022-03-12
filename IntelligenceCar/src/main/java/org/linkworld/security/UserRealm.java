package org.linkworld.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.linkworld.config.Salt;
import org.linkworld.dao.UserMapper;
import org.linkworld.persist.emtity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserMapper userMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        MyUserPasswordToken token=(MyUserPasswordToken) authenticationToken;

        User user=userMapper.selectById(BigInteger.valueOf(Long.parseLong(token.getUsername())));

        if(user==null){
            throw new RuntimeException();
        }
        return new SimpleAuthenticationInfo(token.getUsername(), user.getPassword(), ByteSource.Util.bytes(Salt.salt),getName());
    }
}
