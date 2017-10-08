package com.goodeen.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * 常用实体类基类(非强制)
 * @author peter.e.king
 * @param <K> 主键id的类型
 */
@Data
public class BaseModel<K extends Serializable> implements Serializable {
  private static final long serialVersionUID = 8789018956335666713L;
  /** 主键 **/
  private K id;
  /** 创建者 **/
  private Integer creator;
  /** 创建时间 **/
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;
  /** 修改者 **/
  private Integer updator;
  /** 修改时间 **/
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updateTime;
  /** 备注说明 **/
  private String summary;
}
