package org.linkworld.controller;

/*
 *@Author  LXC BlueProtocol
 *@Since   2022/3/5
 */


import lombok.extern.slf4j.Slf4j;
import org.linkworld.config.LoginSessionParams;
import org.linkworld.persist.vo.ResultBean;
import org.linkworld.properties.FileProperties;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/picture")
public class PictureController extends BaseController{

 @PostMapping("/savePicture")
 @ResponseBody
 public ResultBean savePictureToServer(@RequestHeader("token") String token,HttpServletRequest httpServletRequest,@RequestParam("file") MultipartFile file) {
  HttpSession session=httpServletRequest.getSession();
  Boolean isTrue = savePicture(file);
  if(isTrue) {
   return loginNum(session,ResultBean.ok());
  }
  return loginNum(session,ResultBean.bad());
 }


 @GetMapping("/getAllPictures")
 @ResponseBody

 public ResultBean getAllPicture(HttpServletRequest httpServletRequest,@RequestHeader("token") String token) {
  HttpSession session=httpServletRequest.getSession();
  File pictureFiles = new File(FileProperties.OUT_FILE_PATH);
  ArrayList<File> fileList = new ArrayList<>();
  if(pictureFiles==null) {
   return ResultBean.ok().setMessage("您还没有拍任何照片").setData(fileList);
  }
  File[] files = pictureFiles.listFiles();
  for (File file : files) {
   fileList.add(file);
  }
  if(fileList.size()==0) {
   return loginNum(session,ResultBean.ok().setData(fileList).setMessage("您还没有怕任何照片"));

  }
  return loginNum(session,ResultBean.ok().setData(fileList));
 }

 private Boolean savePicture(MultipartFile file)  {
  File pictureFile = null;
  File outPictureFile = null;
  InputStream fileInputStream = null;
  FileOutputStream fileOutputStream = null;

  try {
   SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
   String dateFormat = format.format(new Date());
   String dateFormatJPG = dateFormat.concat(".jpg");
   outPictureFile = new File(FileProperties.LINUX_OUT_FILE_PATH+dateFormatJPG);
   fileInputStream = file.getInputStream();
   fileOutputStream = new FileOutputStream(outPictureFile);
   int len = 0;
   byte[] bytes = new byte[1024];
   while (((len=fileInputStream.read(bytes))!=-1)) {
    fileOutputStream.write(bytes,0,len);
   }
   if(len==-1) {
    log.info(AnsiOutput.toString(AnsiColor.BRIGHT_BLUE,"图片上传到服务器成功！"));
   }
  } catch (IOException e) {
   e.printStackTrace();
  } finally {
   if(fileInputStream!=null) {
    try {
     fileInputStream.close();
    } catch (IOException e) {
     e.printStackTrace();
    }
   }
   if(fileOutputStream!=null) {
    try {
     fileOutputStream.close();
    } catch (IOException e) {
     e.printStackTrace();
    }
   }
  }
  return true;
 }

 public ResultBean loginNum(HttpSession session, ResultBean resultBean){

  if(session.getAttribute(LoginSessionParams.userLogin)!=null){
   resultBean.setUserLogin(1);

  }

  if(session.getAttribute(LoginSessionParams.wechatLogin)!=null){
   resultBean.setWechatLogin(1);
  }
  return resultBean;
 }
}
