package com.demo.batch.springbatch.batch;

import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.config.AppConfig;
import com.demo.batch.springbatch.model.User;

@Component
public class UserReader extends StaxEventItemReader<User> {
  
  public UserReader(AppConfig appConfig) {
    super();
    setResource(new ClassPathResource(appConfig.file));
    setFragmentRootElementName("user");
    
    Jaxb2Marshaller userMarshaller = new Jaxb2Marshaller();
    userMarshaller.setClassesToBeBound(User.class);
    setUnmarshaller(userMarshaller);
    
  }
}
