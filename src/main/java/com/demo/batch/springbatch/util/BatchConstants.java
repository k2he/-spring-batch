package com.demo.batch.springbatch.util;

/**
 * @author kaihe
 *
 */

public class BatchConstants {

  //Job Name
  public static final String ORDER_PROCESS_JOB = "orderProcessJob";
  
  //Item Reader Names
  public static final String ORDER_STAGE_ITEM_READER = "orderStageItemReader";
  
  //Step Names
  public static final String BATCH_STEP_1 = "schema-validation-step";
  public static final String BATCH_STEP_2 = "stage-data-cleaning-step";
  public static final String BATCH_STEP_3 = "save-xml-to-order-stage-step";
  public static final String BATCH_STEP_4 = "move-stage-to-order-table-step";
  
  public static final String XML_TO_STAGETABLE_TASK_EXECUTOR ="xmltoStageTableTaskExecutor";
}
