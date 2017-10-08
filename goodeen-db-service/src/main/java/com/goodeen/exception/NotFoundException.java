package com.goodeen.exception;

public class NotFoundException extends Exception {
	private static final long serialVersionUID = 1630533223816107069L;

  public NotFoundException(String msg) {
      super(msg);
  }

  public NotFoundException(String msg, Exception e) {
      super(msg + " because of " + e.toString());
  }
}