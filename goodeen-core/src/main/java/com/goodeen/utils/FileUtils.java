package com.goodeen.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
  /**
   * 上传文件 
   * @param file 上传文件
   * @param dir  上传路径
   * @throws IOException 
   */
  public static String upload(MultipartFile multipartFile,String dir) throws IOException{
    String[] names = multipartFile.getOriginalFilename().split("[.]");
    String tmpImagePath = dir + "/" + UUID.randomUUID().toString()+"."+names[names.length-1];
    File saveFile = new File(tmpImagePath);
    multipartFile.transferTo(saveFile);
    return tmpImagePath;
  }
}
