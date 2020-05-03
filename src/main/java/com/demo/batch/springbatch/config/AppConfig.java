/**
 * 
 */
package com.demo.batch.springbatch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author kaihe
 *
 */
@Configuration
public class AppConfig {
  
  @Value("${app.csv.fileHeaders}")
  public String[] headers;
  
  @Value("${app.csv.headersLineCount}")
  public int headersLineCounts;
  
  @Value("${app.csv.delimiter}")
  public String delimiter;
  
  @Value("${app.input.file}") 
  public String file;
}
