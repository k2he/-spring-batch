package com.demo.batch.springbatch.batch.stage;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.config.AppProperties;
import com.demo.batch.springbatch.exception.BatchJobException;
import com.demo.batch.springbatch.service.OrderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
*
* @author kaihe
*
*/

@Slf4j
@Component
@RequiredArgsConstructor
public class SchemaValidationTasklet implements Tasklet {

  @NonNull
  private OrderService orderService;

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    log.info("Validating product xml with schema...");
    boolean isValid = orderService.validateXml();
    if (isValid) {
      return RepeatStatus.FINISHED;
    } else {
      throw new BatchJobException("Xml file doesn't match schema");
    }
  }

}
