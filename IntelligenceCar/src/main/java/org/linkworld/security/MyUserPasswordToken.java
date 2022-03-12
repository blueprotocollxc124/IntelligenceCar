package org.linkworld.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

@Data
@NoArgsConstructor
public class MyUserPasswordToken extends UsernamePasswordToken {


    String realmType;


    public MyUserPasswordToken(String id,String password,String realmType){
        super(id,password);
        this.realmType=realmType;
    }



    public MyUserPasswordToken(BigInteger userId,String password,String realmType){
        super(String.valueOf(userId),password);
        this.realmType=realmType;
    }

}
