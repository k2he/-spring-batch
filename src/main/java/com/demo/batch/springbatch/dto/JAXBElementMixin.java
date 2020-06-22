package com.demo.batch.springbatch.dto;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author kaihe
 *
 */

public interface JAXBElementMixin {

  @JsonValue
  Object getValue();

}
