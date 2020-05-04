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
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.BatchConstants;
import com.demo.batch.springbatch.config.AppConfig;
import com.demo.batch.springbatch.model.Order;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class OrderJob extends JobExecutionListenerSupport {
  @NonNull
  private JobBuilderFactory jobBuilderFactory;
  
  @NonNull
  private StepBuilderFactory stepBuilderFactory;
  
  @NonNull
  private OrderProcessor orderProcessor;
  
  @NonNull
  private OrderWriter orderWriter;
  
  @NonNull
  private AppConfig appConfig;
  
  @Bean(name = "orderLoadJob")
  public Job orderLoadJob() {
    Step step = stepBuilderFactory.get(BatchConstants.BATCH_STEP_1)
        .<Order, Order>chunk(5)
        .reader(new OrderReader(appConfig))
        .processor(orderProcessor)
        .writer(orderWriter).build();
    
    Job job = jobBuilderFactory.get(BatchConstants.ORDER_PROCESS_JOB)
        .incrementer(new RunIdIncrementer())
        .listener(this)
        .start(step)
        .build();

    return job;
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
