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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.batch.springbatch.model.Order;
import com.demo.batch.springbatch.service.OrderService;
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
  private OrderService orderService;
  
  @NonNull
  private Job orderLoadJob;
  
  @RequestMapping("/run-batch-job")
  public String handle() throws Exception {
    JobParameters jobParameters = new JobParametersBuilder()
        .addString("source", "Spring Boot").toJobParameters();
    jobLauncher.run(orderLoadJob, jobParameters);

    return "Batch job has been invoked at " + LocalDateTime.now();
  }
  
  @GetMapping("/orders")
  List<Order> all() {
    return orderService.getAll();
  }
}
