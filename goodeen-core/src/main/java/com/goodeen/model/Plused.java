package com.goodeen.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Plused implements Serializable {
  private static final long serialVersionUID = -6955020075641909890L;
  private Trip trip;
  private User user;
  private Date dateTime;
}
