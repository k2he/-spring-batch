package com.demo.batch.springbatch.exception;

public class BatchJobException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public BatchJobException(String message) {
    super(message);
  }
  
  public BatchJobException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
