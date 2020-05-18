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
import com.demo.batch.springbatch.model.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserJobs extends JobExecutionListenerSupport {
  @NonNull
  private JobBuilderFactory jobBuilderFactory;
  
  @NonNull
  private StepBuilderFactory stepBuilderFactory;
  
  @NonNull
  private UserProcessor userProcessor;
  
  @NonNull
  private UserWriter userWriter;
  
  @NonNull
  private AppConfig appConfig;
  
  @Bean(name = "userLoadJob")
  public Job userLoadJob() {
    
    Job job = jobBuilderFactory.get(BatchConstants.USER_PROCESS_JOB)
        .incrementer(new RunIdIncrementer())
        .listener(this)
        .start(step1())
        .build();

    return job;
  }
  
  @Bean
  public Step step1() {
    Step step = stepBuilderFactory.get(BatchConstants.BATCH_STEP_1)
        .<User, User>chunk(5)
        .reader(new UserReader(appConfig))
        .processor(processor())
        .writer(userWriter).build();
    return step;
  }
  
  @Bean
  public ItemProcessor<User, User> processor() {
      return new UserProcessor();
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
