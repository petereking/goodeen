package com.goodeen.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件名：DateUtils.java 日期处理相关工具类 版本信息：V1.0 日期：2013-03-11 Copyright BDVCD Corporation 2013 版权所有
 * http://www.bdvcd.com
 */
public class DateUtils {
  /** 定义常量 **/
  public static final String DATE_SMALL_STR = "yyyy-MM-dd";
  public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";

  /**
   * 使用预设格式提取字符串日期
   * 
   * @param strDate 日期字符串
   * @return
   */
  public static Date parse(String strDate) {
    return parse(strDate, DATE_FULL_STR);
  }

  /**
   * 使用用户格式提取字符串日期
   * 
   * @param strDate 日期字符串
   * @param pattern 日期格式
   * @return
   */
  public static Date parse(String strDate, String pattern) {
    SimpleDateFormat df = new SimpleDateFormat(pattern);
    try {
      return df.parse(strDate);
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Date getCurrentDate() {
    return new Date();
  }
}
