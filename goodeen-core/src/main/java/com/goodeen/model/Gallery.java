package com.goodeen.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 图片实体类
 * @author peter.e.king
 */
@Getter
@Setter
public class Gallery extends BaseModel<Integer> {
  private static final long serialVersionUID = -2861880776625376384L;
  /** 相册名称 **/
  private String name;
  /** 封面图片 **/
  private Image cover;
  /** 图片数 **/
  private Integer imageCount; 
  /** 相册列表 **/
  private List<Image> images = new ArrayList<Image>();
}
