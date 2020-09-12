package com.demo.batch.springbatch;

import java.util.Date;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.demo.batch.springbatch.util.BatchConstants;

@SpringBootApplication
public class SpringBatchApplication implements CommandLineRunner {

  @Autowired
  private JobLauncher jobLauncher;

  @Autowired
  private Job orderLoadJob;

  public static void main(String[] args) {
    SpringApplication.run(SpringBatchApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    JobParametersBuilder paramsBuilder = new JobParametersBuilder();
    paramsBuilder.addDate("date", new Date(), true).toJobParameters();
    JobExecution jobExecution = jobLauncher.run(orderLoadJob, paramsBuilder.toJobParameters());
    System.exit(getExitCode(jobExecution));
  }

  private int getExitCode(JobExecution jobExecution) {
    ExitStatus exitStatus = jobExecution.getExitStatus();
    if (ExitStatus.FAILED.getExitCode().equals(exitStatus.getExitCode())) {
      return BatchConstants.BATCH_FAILED_EXIT_CODE;
    }
    return BatchConstants.BATCH_SUCCESS_EXIT_CODE;
  }
}
