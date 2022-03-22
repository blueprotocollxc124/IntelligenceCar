package org.linkworld.persist.emtity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    BigInteger userId;

    Date date;

    String openId;

    public Login(BigInteger userId,String openId){
        this.setUserId(userId);
        this.setOpenId(openId);
        this.date=new Date();
    }
}
