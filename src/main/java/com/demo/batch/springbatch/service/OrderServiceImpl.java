package com.demo.batch.springbatch.service;

import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.xml.transform.stream.StreamResult;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import com.demo.batch.springbatch.config.AppProperties;
import com.demo.batch.springbatch.dto.OrderList;
import com.demo.batch.springbatch.model.Order;
import com.demo.batch.springbatch.model.OrderStage;
import com.demo.batch.springbatch.repository.OrderRepository;
import com.demo.batch.springbatch.util.XmlUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
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

  @NonNull
  private Jaxb2Marshaller marshaller;

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
  public OrderStage convertToOrderStage(OrderList.Order orderObj)
      throws Exception {
    // Convert SourceCode into XML format, then convert the xml into Json
    final StringWriter xmlOut = new StringWriter();
    marshaller.marshal(orderObj, new StreamResult(xmlOut));
    String xmlValue = xmlOut.toString();
    xmlOut.close();
    String productJson = XmlUtils.convertXmlToJsonString(xmlValue);
    
    LocalDate orderDate = LocalDate.of(orderObj.getOrderDate().getYear(),
        orderObj.getOrderDate().getMonth(),
        orderObj.getOrderDate().getDay());

    OrderStage orderStage = OrderStage.builder()
        .orderRef(orderObj.getOrderRef())
        .amount(BigDecimal.valueOf(orderObj.getAmount()))
        .orderDate(orderDate)
        .productJson(productJson)
        .build();
    return orderStage;
  }

}
