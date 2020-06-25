/**
 * 
 */
package com.demo.batch.springbatch.config;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.xml.bind.JAXBElement;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.demo.batch.springbatch.dto.JAXBElementMixin;
import com.demo.batch.springbatch.dto.OrderList;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author kaihe
 *
 */
@RequiredArgsConstructor
@Configuration
@PropertySource("file:${app.config.home:src/main/resources}/application.properties")
@EnableJpaAuditing
@EnableBatchProcessing
@EnableScheduling
public class AppConfig {

  @NonNull
  private AppProperties appProperties;

  private static final String JSON_DATE_FORMAT = "YYYY-MM-dd";

  @Bean
  public Jaxb2Marshaller jaxb2Marshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    marshaller.setClassesToBeBound(OrderList.Order.class);
    marshaller.setMarshallerProperties(new HashMap<String, Object>() {
      {
        put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
        // set more properties here...
      }
    });
    return marshaller;
  }

  @Bean
  @StepScope
  public TaskExecutor taskExecutor() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setMaxPoolSize(appProperties.getThreadpoolSize());
    taskExecutor.afterPropertiesSet();
    taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
    return taskExecutor;
  }

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JaxbAnnotationModule());
    mapper.addMixIn(JAXBElement.class, JAXBElementMixin.class);
    mapper.setDateFormat(new SimpleDateFormat(JSON_DATE_FORMAT));
    mapper.setSerializationInclusion(Include.NON_NULL);
    return mapper;
  }
}
