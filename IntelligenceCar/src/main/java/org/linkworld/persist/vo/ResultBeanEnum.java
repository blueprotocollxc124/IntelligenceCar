package org.linkworld.persist.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 *@Author  LXC BlueProtocol
 *@Since   2022/3/5
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResultBeanEnum {

 RESULT_OK(200,"成功",true),

 RESULT_FAILURE(500,"失败",false),

 Not_Auth(500,"无权限",false);

 ;
 private Integer code;

 private String message;

 private Boolean success;

}