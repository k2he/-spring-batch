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
import com.demo.batch.springbatch.batch.order.OrderProcessor;
import com.demo.batch.springbatch.batch.order.OrderReader;
import com.demo.batch.springbatch.batch.order.OrderWriter;
import com.demo.batch.springbatch.batch.order.ReplaceOrderDataTasklet;
import com.demo.batch.springbatch.batch.order.StoreOrderNextSeqTasklet;
import com.demo.batch.springbatch.batch.stage.DataCleaningTasklet;
import com.demo.batch.springbatch.batch.stage.OrderStageProcessor;
import com.demo.batch.springbatch.batch.stage.OrderStageReader;
import com.demo.batch.springbatch.batch.stage.OrderStageWriter;
import com.demo.batch.springbatch.batch.stage.SchemaValidationTasklet;
import com.demo.batch.springbatch.batch.stage.XmltoJsonFileTasklet;
import com.demo.batch.springbatch.config.AppProperties;
import com.demo.batch.springbatch.model.Order;
import com.demo.batch.springbatch.model.OrderStage;
import com.demo.batch.springbatch.util.BatchConstants;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kaihe
 *
 */

@RequiredArgsConstructor
@Component
@Slf4j
public class OrderJobs extends JobExecutionListenerSupport {
  @NonNull
  private JobBuilderFactory jobBuilderFactory;

  @NonNull
  private StepBuilderFactory stepBuilderFactory;

  @NonNull
  private SchemaValidationTasklet schemaValidationTasklet;

  @NonNull
  private XmltoJsonFileTasklet xmltoJsonFileTasklet;

  @NonNull
  private DataCleaningTasklet dataCleaningTasklet;

  @NonNull
  private OrderStageReader orderStageReader;

  @NonNull
  private OrderStageProcessor orderStageProcessor;

  @NonNull
  private OrderStageWriter orderStageWriter;

  @NonNull
  private ReplaceOrderDataTasklet replaceOrderDataTasklet;
  
  @NonNull
  private StoreOrderNextSeqTasklet storeOrderNextSeqTasklet;
  
  @NonNull
  private OrderReader orderReader;
  
  @NonNull
  private OrderProcessor orderProcessor;
  
  @NonNull
  private OrderWriter orderWriter;
  
  @NonNull
  private AppProperties appProperties;

  @Bean(name = "orderLoadJob")
  public Job orderLoadJob() {
    Job job = jobBuilderFactory.get(BatchConstants.ORDER_PROCESS_JOB)
        .incrementer(new RunIdIncrementer()).listener(this).start(step1()).next(step2())
        .next(step3()).next(step4()).next(step5()).next(step6()).next(step7()).build();
    return job;
  }


  // Validate Schema
  @Bean
  protected Step step1() {
    return stepBuilderFactory.get(BatchConstants.BATCH_STEP_1).tasklet(schemaValidationTasklet)
        .build();
  }

  // Convert Xml file to Json file
  @Bean
  protected Step step2() {
    return stepBuilderFactory.get(BatchConstants.BATCH_STEP_2).tasklet(xmltoJsonFileTasklet)
        .build();
  }

  // Clean Existing data in Stage table
  @Bean
  protected Step step3() {
    return stepBuilderFactory.get(BatchConstants.BATCH_STEP_3).tasklet(dataCleaningTasklet)
        .build();
  }

  // Parse Json, Convert to ProductCatalogExtractStage and save into Stage table
  @Bean
  public Step step4() {
    Step step = stepBuilderFactory.get(BatchConstants.BATCH_STEP_4)
        .<JsonNode, OrderStage>chunk(appProperties.getFileBatchSize())
        .reader(orderStageReader)
        .processor(orderStageProcessor)
        .writer(orderStageWriter)
        .build();
    return step;
  }

  // Read and store current sequence for ProductCatalogExtrat table for later use
  @Bean
  public Step step5() {
    return stepBuilderFactory.get(BatchConstants.BATCH_STEP_5)
        .tasklet(storeOrderNextSeqTasklet).build();
  }

  // Parse Json, Convert to ProductCatalogExtractStage and save into Stage table
  @Bean
  public Step step6() {
    Step step = stepBuilderFactory.get(BatchConstants.BATCH_STEP_6)
        .<OrderStage, Order>chunk(appProperties.getDatabaseBatchSize())
        .reader(orderReader)
        .processor(orderProcessor)
        .writer(orderWriter).build();
    return step;
  }

  @Bean
  protected Step step7() {
    return stepBuilderFactory.get(BatchConstants.BATCH_STEP_7).tasklet(replaceOrderDataTasklet)
        .build();
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
