/**
 * 
 */
package com.demo.batch.springbatch.batch;

import java.time.LocalDateTime;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kaihe
 *
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {

  @NonNull
  private JobLauncher jobLauncher;

//  @NonNull
//  private Job orderLoadJob;
//  
////  @Scheduled(cron = "0 30 13 * * ?") // Fire at 13:30 every day
//  @Scheduled(cron = "0 40 13 ? * MON-FRI") // Fire at 13:40 every Monday to Friday
//  public void scheduleTaskWithCronExpression() throws Exception {
//      log.info("Cron Task :: Execution Time - {}", LocalDateTime.now());
//      JobParameters jobParameters = new JobParametersBuilder()
//          .addString("source", "Spring Boot").toJobParameters();
//      jobLauncher.run(orderLoadJob, jobParameters);
//      log.info("Batch job has been invoked at {}", LocalDateTime.now());
//  }
}
