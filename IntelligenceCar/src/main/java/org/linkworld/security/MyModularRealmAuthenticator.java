package org.linkworld.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import java.util.Collection;

public class MyModularRealmAuthenticator extends ModularRealmAuthenticator {

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {

        assertRealmsConfigured();//判断realm是否为空

        MyUserPasswordToken userPasswordToken=(MyUserPasswordToken) authenticationToken;

       String type= userPasswordToken.getRealmType();

       Collection<Realm> realms=getRealms();

       for(Realm realm: realms){
            if(realm.getClass().getName().equals(type)){
               return doSingleRealmAuthentication(realm,userPasswordToken);
            }
        }
        throw new AuthenticationException();
    }
}
