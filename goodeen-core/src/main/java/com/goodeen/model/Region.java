package com.goodeen.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Region implements Serializable {
  private static final long serialVersionUID = 5562366308773016631L;
  private Integer id;
  private String name;
  private String fullname;
  private String location;
  private String spell;
  private String threeword;
  private Integer parent;
  private Integer level;
  private Integer order;
}
