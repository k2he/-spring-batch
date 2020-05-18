package com.demo.batch.springbatch.batch;

import static org.springframework.batch.core.BatchStatus.COMPLETED;
import static org.springframework.batch.core.BatchStatus.FAILED;
import static org.springframework.batch.core.BatchStatus.STARTED;
import java.time.LocalDateTime;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.BatchConstants;
import com.demo.batch.springbatch.config.AppConfig;
import com.demo.batch.springbatch.model.Order;
import com.demo.batch.springbatch.model.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class OrderJobs extends JobExecutionListenerSupport {
  @NonNull
  private JobBuilderFactory jobBuilderFactory;
  
  @NonNull
  private StepBuilderFactory stepBuilderFactory;
  
  @NonNull 
  private DataCleaningTasklet dataCleaningTasklet;
  
  @NonNull
  private OrderProcessor userProcessor;
  
  @NonNull
  private OrderWriter orderWriter;
  
  @NonNull
  private AppConfig appConfig;
  
  @Bean(name = "orderLoadJob")
  public Job orderLoadJob() {
    
    Job job = jobBuilderFactory.get(BatchConstants.ORDER_PROCESS_JOB)
        .incrementer(new RunIdIncrementer())
        .listener(this)
        .start(step1())
        .next(step2())
        .build();

    return job;
  }
  
  @Bean
  protected Step step1() {
    return stepBuilderFactory.get(BatchConstants.BATCH_STEP_1)
        .tasklet(dataCleaningTasklet)
        .build();
  }
  
  @Bean
  public Step step2() {
    Step step = stepBuilderFactory.get(BatchConstants.BATCH_STEP_2)
        .<Order, Order>chunk(5)
        .reader(new OrderReader(appConfig))
        .processor(processor())
        .writer(orderWriter).build();
    return step;
  }
  
  @Bean
  public ItemProcessor<Order, Order> processor() {
      return new OrderProcessor();
  }
  
  @Override
  public void beforeJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == STARTED) {
      log.info("ORDER BATCH PROCESS STARTED at {}!", jobExecution.getStartTime());
    }
  }
  
  @Override
  public void afterJob(JobExecution jobExecution) {
      if (jobExecution.getStatus() == COMPLETED) {
          log.info("ORDER BATCH PROCESS COMPLETED at {}!", LocalDateTime.now());
      } else if (jobExecution.getStatus() == FAILED) {
        log.info("ORDER BATCH PROCESS FAILED at {}!", LocalDateTime.now());
      }
  }
}
