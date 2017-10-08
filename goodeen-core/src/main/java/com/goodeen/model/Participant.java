package com.goodeen.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Participant implements Serializable {
  private static final long serialVersionUID = 4760376917967938242L;
  private Trip trip;
  private User copartner;
  private Date joinTime;
}
