package com.demo.batch.springbatch.batch.stage;

import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.config.AppProperties;
import com.demo.batch.springbatch.dto.OrderList;
import com.demo.batch.springbatch.dto.OrderList.Order;
import com.demo.batch.springbatch.util.BatchConstants;

/**
 *
 * @author kaihe
 *
 */

@Component
public class OrderStageReader extends StaxEventItemReader<Order> {

  @Autowired
  public OrderStageReader(AppProperties appProperties) throws Exception {
    super();
//    setResource(new FileSystemResource(appProperties.getXmlFilePath()));
    setResource(new ClassPathResource(appProperties.getXmlFilePath()));
    setFragmentRootElementName("order");
    setName(BatchConstants.ORDER_STAGE_ITEM_READER);

    Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
    jaxb2Marshaller.setClassesToBeBound(OrderList.Order.class);
    jaxb2Marshaller.afterPropertiesSet();
    
    setUnmarshaller(jaxb2Marshaller);
  }

}
