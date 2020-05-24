package com.demo.batch.springbatch.batch.stage;

import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.config.AppProperties;
import com.demo.batch.springbatch.util.BatchConstants;
import com.fasterxml.jackson.databind.JsonNode;

/**
 *
 * @author kaihe
 *
 */

@Component
public class OrderStageReader extends JsonItemReader<JsonNode> {

  @Autowired
  public OrderStageReader(AppProperties appProperties) {
    super(new FileSystemResource(appProperties.getJsonFilePath()),
        new JacksonJsonObjectReader<JsonNode>(JsonNode.class));
    setExecutionContextName(BatchConstants.ORDER_STAGE_ITEM_READER);
  }

}
