package com.goodeen.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 图片实体类
 * @author peter.e.king
 */
@Getter
@Setter
public class Image extends BaseModel<Integer> {
  private static final long serialVersionUID = 4016706315958176380L;
  /** 图片url **/
  private String url; 
  /** 所属相册 **/
  private Gallery gallery; 
}
