package com.goodeen.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Tag implements Serializable {
	private static final long serialVersionUID = -7662470637424688318L;
  private Integer id;
	private String name;
}
