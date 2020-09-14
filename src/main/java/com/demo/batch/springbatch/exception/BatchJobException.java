package com.demo.batch.springbatch.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author kaihe
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BatchJobException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  protected String serverStatusCode;

  protected String statusDesc;

  protected Throwable rootCause;

  public BatchJobException(String serverStatusCode, String statusDesc) {
    this.serverStatusCode = serverStatusCode;
    this.statusDesc = statusDesc;
  }

  public BatchJobException(String serverStatusCode, String statusDesc, Throwable rootCause) {
    this.serverStatusCode = serverStatusCode;
    this.statusDesc = statusDesc;
    this.rootCause = rootCause;
  }
}
