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
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.batch.order.MoveStageToOrderAndBackupTasklet;
import com.demo.batch.springbatch.batch.stage.DataCleaningTasklet;
import com.demo.batch.springbatch.batch.stage.OrderStageProcessor;
import com.demo.batch.springbatch.batch.stage.OrderStageReader;
import com.demo.batch.springbatch.batch.stage.OrderStageWriter;
import com.demo.batch.springbatch.batch.stage.SchemaValidationTasklet;
import com.demo.batch.springbatch.config.AppProperties;
import com.demo.batch.springbatch.dto.OrderList.Order;
import com.demo.batch.springbatch.model.OrderStage;
import com.demo.batch.springbatch.util.BatchConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kaihe
 *
 */

@Component
@Slf4j
public class OrderJobListener extends JobExecutionListenerSupport {

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
