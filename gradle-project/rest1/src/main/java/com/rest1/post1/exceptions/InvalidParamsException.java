package com.rest1.post1.exceptions;

public class InvalidParamsException extends Exception {

  public InvalidParamsException() {
    super();
  }

  public InvalidParamsException(Exception e) {
    super(e);
  }

  public InvalidParamsException(String message) {
    super(message);
  }

}
