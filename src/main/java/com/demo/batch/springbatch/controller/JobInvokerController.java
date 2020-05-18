/**
 * 
 */
package com.demo.batch.springbatch.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.batch.springbatch.model.User;
import com.demo.batch.springbatch.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author kaihe
 *
 */

@RestController
@RequiredArgsConstructor
public class JobInvokerController {

  @NonNull
  private JobLauncher jobLauncher;

  @NonNull
  private UserService userService;
  
  @NonNull
  private Job userLoadJob;
  
  @RequestMapping("/run-batch-job")
  public String handle() throws Exception {
    JobParameters jobParameters = new JobParametersBuilder()
        .addString("source", "Spring Boot").toJobParameters();
    jobLauncher.run(userLoadJob, jobParameters);

    return "Batch job has been invoked at " + LocalDateTime.now();
  }
  
  @GetMapping("/users")
  List<User> all() {
    return userService.getAll();
  }
}
