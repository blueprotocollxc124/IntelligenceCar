package org.linkworld.persist.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WechatResult {

    String	openid;

    String  session_key;

    String  unionid;

    int     errcode;

    String  errmsg;
}
