package org.linkworld.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Data
public class WechatConfig {

    String url="https://api.weixin.qq.com/sns/jscode2session";

    String appId="wx2883cdadeafa7b63";

    String appSecret="258083396fcc5ee9a11cc1adcb7275df";

    String grant_type="authorization_code";

}
