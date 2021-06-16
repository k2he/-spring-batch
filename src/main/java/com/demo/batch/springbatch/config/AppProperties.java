package com.demo.batch.springbatch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

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

  private int fileBatchSize;

  private int threadpoolSize;

  private final Report report = new Report();

  @Getter
  @Setter
  public static class Report {
    private String outputPath;
    private String fileName;
    private String archivePath;
    private int archivePeriodInDays;
    private int threadpoolSize;
    private int fileBatchSize;
    private boolean deleteIfFileExists;
    private List<String> csvHeader;
    private List<String> javaField;
    private Map<String, String> orderBy;
  }
}
