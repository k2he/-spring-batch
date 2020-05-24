package com.demo.batch.springbatch.batch.stage;

import java.io.FileWriter;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.config.AppProperties;
import com.demo.batch.springbatch.exception.BatchJobException;
import com.demo.batch.springbatch.util.XmlUtils;
import com.fasterxml.jackson.databind.JsonNode;
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
public class XmltoJsonFileTasklet implements Tasklet {

  @NonNull
  private AppProperties appProperties;

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    log.info("Converting Xml file to json file...");
    convertXml2JsonFile();
    return RepeatStatus.FINISHED;
  }

  private void convertXml2JsonFile() throws Exception {
    String xmlFile = appProperties.getXmlFilePath();
    JsonNode rootNode = XmlUtils.convertToJson(xmlFile);
    JsonNode order_list = rootNode.get("order_list");
    JsonNode orders = order_list.get("order");
    if (orders == null) {
      throw new BatchJobException("Can't find any data.");
    }
    String jsonFileString = orders.toPrettyString();

    // Write to Json file.
    String jsonFile = appProperties.getJsonFilePath();
    FileWriter fileWriter = new FileWriter(jsonFile);
    fileWriter.write(jsonFileString);
    fileWriter.close();
  }
}
