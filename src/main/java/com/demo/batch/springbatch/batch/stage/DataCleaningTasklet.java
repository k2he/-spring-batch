package com.demo.batch.springbatch.batch.stage;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.repository.OrderStageRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kaihe
 *
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class DataCleaningTasklet implements Tasklet {

  @NonNull
  private OrderStageRepository orderStageRepository;
  
  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    log.info("Truncate all old data from OrderStage table");
    orderStageRepository.truncate();
    return RepeatStatus.FINISHED;
  }

}
