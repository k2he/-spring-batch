package com.demo.batch.springbatch.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.GregorianCalendar;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.demo.batch.springbatch.config.AppProperties;
import com.demo.batch.springbatch.dto.ObjectFactory;
import com.demo.batch.springbatch.dto.OrderList;
import com.demo.batch.springbatch.exception.BatchJobException;
import com.demo.batch.springbatch.model.OrderStage;
import com.demo.batch.springbatch.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@EnableConfigurationProperties
@TestPropertySource("/application.properties")
@ContextConfiguration(classes = AppProperties.class)
public class OrderServiceTests {

  @Mock 
  private OrderRepository orderRepository;
  
  @Autowired
  private AppProperties appProperties;

  @Autowired
  private ResourceLoader resourceLoader;

  @Mock
  private MessageService messageService;

  @Mock
  private ObjectMapper objectMapper;

  private OrderService orderService;

  @Before
  public void setUp() {
    orderService =
        new OrderServiceImpl(orderRepository, resourceLoader, messageService, appProperties, objectMapper);
  }
  
/* 
 * Below code need config how to load xml file from test/resources directory, otherwise, it will work.
 * 
  @Test
  public void whenValidateXmlUsingValidInput_thenReturnTrue() throws Exception {
    // when
    Boolean result = orderService.validateXml();

    // then
    assertTrue(result);
  }

  @Test(expected = BatchJobException.class)
  public void whenValidateXmlUsingInValidInput_thenReturnException() throws Exception {
    // given
    Mockito.when(appProperties.getXmlFilePath()).thenReturn("src/test/resources/input/invalid-input.xml");

    // when
    Boolean result = orderService.validateXml();
  }
*/
  @Test
  public void whenConvertToOrderStage_thenReturnResult() throws Exception {
    OrderList.Order orderObj = new OrderList.Order();

    GregorianCalendar gcal = new GregorianCalendar();
    ObjectFactory factory = new ObjectFactory();
    
    XMLGregorianCalendar orderDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
    JAXBElement<XMLGregorianCalendar> orderDateElement = factory.createOrderListOrderOrderDate(orderDate);
    orderObj.setOrderDate(orderDateElement);
    
    JAXBElement<String> refElement = factory.createOrderListOrderOrderRef("Ref");
    orderObj.setOrderRef(refElement);
    
    JAXBElement<Float> amountElement = factory.createOrderListOrderAmount(2.356f);
    orderObj.setAmount(amountElement);
    
    // given
    Mockito.when(objectMapper.writeValueAsString(orderObj)).thenReturn("{}");

    // when
    OrderStage result = orderService.convertToOrderStage(orderObj);

    // then
    assertNotNull(result);
    assertEquals(false, result.getProductJson().isEmpty());
  }
}
