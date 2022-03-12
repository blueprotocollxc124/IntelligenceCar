package org.linkworld.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.linkworld.config.WebConfig;
import org.linkworld.config.WechatConfig;
import org.linkworld.persist.vo.WechatResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class WechatUnit {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    WechatConfig wechatConfig;
    @Autowired
    ObjectMapper objectMapper;


    public String getOpenId(String code) throws Exception{


        String url = wechatConfig.getUrl()+"?"
                +"appid="+wechatConfig.getAppId()
                +"&secret="+wechatConfig.getAppSecret()
                +"&grant_type="+wechatConfig.getGrant_type()
                +"&js_code="+code;



        String mess=restTemplate.getForObject(url,String.class);

        WechatResult wechatResult = objectMapper.readValue(mess,WechatResult.class);
        if(wechatResult.getOpenid()==null){
            throw new Exception();
        }
        return wechatResult.getOpenid();
    }



}
