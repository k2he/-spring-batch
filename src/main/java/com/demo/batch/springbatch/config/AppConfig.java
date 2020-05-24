/**
 * 
 */
package com.demo.batch.springbatch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author kaihe
 *
 */
@Configuration
@PropertySource("file:${app.config.home:src/main/resources}/application.properties")
@EnableJpaAuditing
public class AppConfig {
  
}
