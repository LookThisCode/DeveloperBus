package com.alow.exception;

import java.io.Serializable;


@SuppressWarnings("serial")
public class NotLoggedInException extends Exception implements Serializable{
  
  public NotLoggedInException() {
  }

  public NotLoggedInException(String message) {
    super(message);
  }

  public NotLoggedInException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotLoggedInException(Throwable cause) {
    super(cause);
  }

}
