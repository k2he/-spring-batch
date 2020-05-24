package com.demo.batch.springbatch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

/**
 * @author kaihe
 *
 */

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app") // prefix app, find app.* values
public class AppProperties {

  private String schemaFilePath;
  
  private String xmlFilePath;

  private String jsonFilePath;

  private int fileBatchSize;

  private int databaseBatchSize;

}
