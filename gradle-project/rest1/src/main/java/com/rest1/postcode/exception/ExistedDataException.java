package com.rest1.postcode.exception;

public class ExistedDataException extends Exception {

  public ExistedDataException() {
    super();
  }

  public ExistedDataException(Exception e) {
    super(e);
  }

  public ExistedDataException(String message) {
    super(message);
  }

  public ExistedDataException(String message, Exception e) {
    super(message, e);
  }

}
