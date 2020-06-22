package com.demo.batch.springbatch.service;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import javax.xml.bind.JAXBElement;
import org.springframework.stereotype.Service;
import com.demo.batch.springbatch.config.AppProperties;
import com.demo.batch.springbatch.dto.JAXBElementMixin;
import com.demo.batch.springbatch.dto.OrderList;
import com.demo.batch.springbatch.model.Order;
import com.demo.batch.springbatch.model.OrderStage;
import com.demo.batch.springbatch.repository.OrderRepository;
import com.demo.batch.springbatch.util.XmlUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kaihe
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  @NonNull
  private OrderRepository orderRespository;

  @NonNull
  private AppProperties appProperties;

  private static final String JSON_DATE_FORMAT = "YYYY-MM-dd";


  public List<Order> getAll() {
    return orderRespository.findAll();
  }

  @Override
  public boolean validateXml() {
    File file = new File(
        getClass().getClassLoader().getResource(appProperties.getSchemaFilePath()).getFile());
    String schemaFilePath = file.getAbsolutePath();
    return XmlUtils.validateXMLSchema(schemaFilePath, appProperties.getXmlFilePath());
  }

  @Override
  public OrderStage convertToOrderStage(OrderList.Order orderObj) throws Exception {
    // Convert orderObj to Json String
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JaxbAnnotationModule());
    mapper.addMixIn(JAXBElement.class, JAXBElementMixin.class);
    mapper.setDateFormat(new SimpleDateFormat(JSON_DATE_FORMAT));
    String jsonValue = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(orderObj);
    log.info(jsonValue);

    LocalDate orderDate = LocalDate.of(orderObj.getOrderDate().getValue().getYear(),
        orderObj.getOrderDate().getValue().getMonth(), orderObj.getOrderDate().getValue().getDay());

    OrderStage orderStage = OrderStage.builder().orderRef(orderObj.getOrderRef().getValue())
        .amount(BigDecimal.valueOf(orderObj.getAmount().getValue())).orderDate(orderDate)
        .productJson(jsonValue).build();
    return orderStage;
  }

}
