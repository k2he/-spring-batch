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
  
  @Value("${app.input.file}") 
  public String file;
}
