package com.goodeen.db;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RebackTasks {
  @Value("${spring.profiles.active:dev}")
  private String active;

  @Value("${reback.filepathpre:D:/db_reback/}")
  private String filepathpre;
  
  @Value("${spring.datasource.password:root}")
  private String password;
  

  /**
   * 每天凌晨两点重新备份，如果要手动备份直接运行main函数
   */
  @Scheduled(cron = "${db.reback.cron:0 0 2 * * ? }")
  public void rebackDb() {
    /** 如果文件夹不存在创建文件夹 **/
    File dir = new File(filepathpre);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
    String filePath = filepathpre + "goodeen" + df.format(new Date()) + ".sql";
    String fow_windows = "cmd /c mysqldump goodeen -uroot  -p" + password + " >" + filePath;
    String[] for_linux = new String[] {"/bin/bash", "-c",
        "/usr/bin/mysqldump goodeen -uroot  -p" + password + " >" + filePath};
    try {
      if (active.equals("prod")) {
        Runtime.getRuntime().exec(for_linux);
      } else {
        Runtime.getRuntime().exec(fow_windows);
      }
      System.out.println("备份数据库成功！文件路径为：" + filePath);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
