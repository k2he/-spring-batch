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
  public static final String BATCH_STEP_2 = "convert-xml-to-json-file-step";
  public static final String BATCH_STEP_3 = "data-cleaning-step";
  public static final String BATCH_STEP_4 = "save-to-order-stage-step";
  public static final String BATCH_STEP_5 = "get-order-sequence-step";
  public static final String BATCH_STEP_6 = "move-data-to-order-step";
  public static final String BATCH_STEP_7 = "remove-old-order-step";
  
  public static final String ORDER_NEXT_SEQ_VALUE = "nextSeqValue";
}
