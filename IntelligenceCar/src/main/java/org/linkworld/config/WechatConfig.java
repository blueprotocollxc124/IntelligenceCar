package org.linkworld.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Data
public class WechatConfig {

    String url="https://api.weixin.qq.com/sns/jscode2session";

    String appId="wxeca0d11d9513a807";

    String appSecret="6c9905f7844d65881b3f453b4e6cfc73";

    String grant_type="authorization_code";

}
