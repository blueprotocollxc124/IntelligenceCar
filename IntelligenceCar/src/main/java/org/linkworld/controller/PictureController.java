package org.linkworld.controller;

/*
 *@Author  LXC BlueProtocol
 *@Since   2022/3/5
 */


import lombok.extern.slf4j.Slf4j;
import org.linkworld.persist.vo.ResultBean;
import org.linkworld.properties.FileProperties;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
@Slf4j
public class PictureController extends BaseController{

 @PostMapping("/savePicture")
 @ResponseBody
 public ResultBean savePictureToServer(@RequestParam("file") MultipartFile file) {
  Boolean isTrue = savePicture(file);
  if(isTrue==true) {
   return ResultBean.ok();
  }
  return ResultBean.bad();
 }


 @GetMapping("/getAllPictures")
 @ResponseBody
 public ResultBean getAllPicture() {
  File pictureFiles = new File(FileProperties.OUT_FILE_PATH);
  File[] files = pictureFiles.listFiles();
  ArrayList<File> fileList = new ArrayList<>();
  for (File file : files) {
   fileList.add(file);
  }
  return ResultBean.ok().setData(fileList);
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
   outPictureFile = new File(FileProperties.OUT_FILE_PATH+dateFormatJPG);
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


}
