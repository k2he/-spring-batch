package com.demo.batch.springbatch.batch;

import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.config.AppConfig;
import com.demo.batch.springbatch.model.Order;

@Component
public class OrderReader extends StaxEventItemReader<Order> {
  
  public OrderReader(AppConfig appConfig) {
    super();
    setResource(new ClassPathResource(appConfig.file));
    setFragmentRootElementName("order");
    
    Jaxb2Marshaller userMarshaller = new Jaxb2Marshaller();
    userMarshaller.setClassesToBeBound(Order.class);
    setUnmarshaller(userMarshaller);
  }
}
