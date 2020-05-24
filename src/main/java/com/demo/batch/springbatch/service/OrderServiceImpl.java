package com.demo.batch.springbatch.service;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import com.demo.batch.springbatch.config.AppProperties;
import com.demo.batch.springbatch.exception.BatchJobException;
import com.demo.batch.springbatch.model.Order;
import com.demo.batch.springbatch.model.OrderHistory;
import com.demo.batch.springbatch.model.OrderStage;
import com.demo.batch.springbatch.repository.OrderRepository;
import com.demo.batch.springbatch.util.XmlUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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

  public static final String SCHEMA_FILE_NAME = "PCv11.xsd";

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public List<Order> getAll() {
    return orderRespository.findAll();
  }

  @Override
  public boolean validateXml() {
    File file = new File(getClass().getClassLoader().getResource(appProperties.getSchemaFilePath()).getFile());
    String schemaFilePath = file.getAbsolutePath();
    return XmlUtils.validateXMLSchema(schemaFilePath, appProperties.getXmlFilePath());
  }

  @Override
  public OrderStage convertToOrderStage(JsonNode orderJson) {
    try {
      OrderStage orderStage = OrderStage.builder()
          .orderRef(orderJson.get("orderRef").asText())
          .amount(BigDecimal.valueOf(orderJson.get("amount").asDouble()))
          .orderDate(LocalDate.parse(orderJson.get("orderDate").asText()))
          .note(orderJson.get("note").asText())
          .productJson(objectMapper.writeValueAsString(orderJson)).build();
      return orderStage;
    } catch (JsonProcessingException e) {
      log.error("Failed to save source codes", e);
      throw new BatchJobException("Failed to save source code", e);
    }
  }

  @Override
  public Order convertToOrder(OrderStage orderStage) {
    return Order.builder().orderRef(orderStage.getOrderRef())
        .amount(orderStage.getAmount())
        .orderDate(orderStage.getOrderDate())
        .note(orderStage.getNote())
        .productJson(orderStage.getProductJson())
        .build();
  }

  @Override
  public OrderHistory convertToOrderHist(Order order) {
    return OrderHistory.builder().orderRef(order.getOrderRef())
        .amount(order.getAmount())
        .orderDate(order.getOrderDate())
        .note(order.getNote())
        .productJson(order.getProductJson())
        .build();
  }

}
