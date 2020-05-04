package com.demo.batch.springbatch.batch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.config.AppConfig;
import com.demo.batch.springbatch.model.Order;

@Component
public class OrderReader extends FlatFileItemReader<Order> {
  
  public OrderReader(AppConfig appConfig) {
    super();
    setResource(new ClassPathResource(appConfig.file));
    setLinesToSkip(appConfig.headersLineCounts);
    
    DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
    lineTokenizer.setNames(new String[] {"order_ref","amount","order_date","note"});
    lineTokenizer.setDelimiter(appConfig.delimiter);
    lineTokenizer.setStrict(false);
    
    BeanWrapperFieldSetMapper<Order> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
    fieldSetMapper.setConversionService(conversionService());
    fieldSetMapper.setTargetType(Order.class);

    DefaultLineMapper<Order> defaultLineMapper = new DefaultLineMapper<>();
    defaultLineMapper.setLineTokenizer(lineTokenizer);
    defaultLineMapper.setFieldSetMapper(fieldSetMapper);
    setLineMapper(defaultLineMapper);
  }
  
  public ConversionService conversionService() {
      DefaultConversionService conversionService = new DefaultConversionService();
      DefaultConversionService.addDefaultConverters(conversionService);
      conversionService.addConverter(new Converter<String, LocalDateTime>() {
          @Override
          public LocalDateTime convert(String text) {
            LocalDate date = LocalDate.parse(text);
            return date.atStartOfDay();
          }
      });

      return conversionService;
  }
}
