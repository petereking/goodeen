package com.goodeen.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Favorited implements Serializable {
	private static final long serialVersionUID = -7206579791942630989L;
  private Trip trip;
	private User user;
	private Date dateTime;
}
