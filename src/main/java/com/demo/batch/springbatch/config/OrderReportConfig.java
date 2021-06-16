package com.demo.batch.springbatch.config;

import com.demo.batch.springbatch.batch.OrderJobListener;
import com.demo.batch.springbatch.batch.csv.*;
import com.demo.batch.springbatch.repository.OrderHistoryRepository;
import com.demo.batch.springbatch.repository.OrderRepository;
import com.demo.batch.springbatch.model.Order;
import com.demo.batch.springbatch.util.BatchConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

@RequiredArgsConstructor
@EnableBatchProcessing
@Configuration
public class OrderReportConfig {

  @NonNull
  private JobBuilderFactory jobBuilderFactory;

  @NonNull
  private StepBuilderFactory stepBuilderFactory;

  @NonNull
  private TaskExecutor taskExecutor;
  
  @NonNull
  private OrderRepository orderRepository;

  @NonNull
  private OrderHistoryRepository orderHistoryRepository;

  @NonNull
  private OrderJobListener orderJobListener;

  @NonNull
  private AppProperties appProperties;

  @NonNull
  private OrderReportReader orderReportReader;

  @NonNull
  private OrderReportWriter orderReportWriter;

  private FileArchiveTasklet fileArchiveTasklet() {
    return new FileArchiveTasklet(appProperties);
  }

  private BackupAndCleanDataTasklet backupAndCleanDataTasklet() {
    return new BackupAndCleanDataTasklet(orderRepository,
            orderHistoryRepository);
  }

  private ArchiveFileCleanTasklet archiveFileCleanTasklet() {
    return new ArchiveFileCleanTasklet(appProperties);
  }

  @Bean(name = "orderReportJob")
  public Job orderReportJob() {
    Job job = jobBuilderFactory.get(BatchConstants.ORDER_REPORT_JOB)
            .incrementer(new RunIdIncrementer()).listener(orderJobListener)
            .start(step1())
            .next(step2())
            .next(step3())
            .next(step4()).build();
    return job;
  }

  /*
   * Move old csv file into archive folder.
   */
  @Bean
  public Step step1() {
    return stepBuilderFactory.get(BatchConstants.BATCH_STEP_1)
            .tasklet(fileArchiveTasklet())
            .build();
  }

  /*
   * Generate csv file.
   */
  @Bean
  public Step step2() {
    return stepBuilderFactory.get(BatchConstants.REPORT_BATCH_STEP_2)
            .<Order, Order>chunk(appProperties.getReport().getFileBatchSize())
            .reader(orderReportReader)
            .writer(orderReportWriter)
            .taskExecutor(taskExecutor)
            .throttleLimit(appProperties.getReport().getThreadpoolSize())
            .build();
  }

  /*
   * Move all data into history document/table.
   */
  @Bean
  public Step step3() {
    return stepBuilderFactory.get(BatchConstants.BATCH_STEP_3)
            .tasklet(backupAndCleanDataTasklet())
            .build();
  }

  /*
   * Delete files older than given days.
   */
  @Bean
  public Step step4() {
    return stepBuilderFactory.get(BatchConstants.BATCH_STEP_4)
            .tasklet(archiveFileCleanTasklet())
            .build();
  }
}
