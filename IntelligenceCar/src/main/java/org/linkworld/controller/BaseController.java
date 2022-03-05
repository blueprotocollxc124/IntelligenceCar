package org.linkworld.controller;

/*
 *@Author  LXC BlueProtocol
 *@Since   2022/3/5
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public class BaseController {

 @Autowired
 protected HttpServletRequest request;

 @Autowired
 protected HttpServletResponse response;

 protected String getUserId() {
  Object userIdObj = request.getSession().getAttribute("userId");
  Object userId = Optional.ofNullable(userIdObj).orElseThrow(()->{
   return new RuntimeException("userId为null");
  });
  return userId.toString();
 }
}
