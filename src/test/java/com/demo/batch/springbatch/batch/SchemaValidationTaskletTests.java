package com.demo.batch.springbatch.batch;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.demo.batch.springbatch.batch.stage.SchemaValidationTasklet;
import com.demo.batch.springbatch.exception.BatchJobException;
import com.demo.batch.springbatch.service.OrderService;


/**
 *
 * @author hekai27
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class SchemaValidationTaskletTests {
  @Mock
  private OrderService orderService;

  private SchemaValidationTasklet schemaValidationTasklet;

  @Before
  public void setUp() {
    schemaValidationTasklet = new SchemaValidationTasklet(orderService);
  }

  @Test
  public void whenValidateXmlSuccess_thenReturnFinishedStatus() throws Exception {
    // given
    Mockito.when(orderService.validateXml()).thenReturn(true);

    // when
    schemaValidationTasklet.execute(null, null);

    // then
    verify(orderService, times(1)).validateXml();
  }

  @Test(expected = BatchJobException.class)
  public void whenValidateXmlFailed_thenGetException() throws Exception {
    // given
    Mockito.when(orderService.validateXml()).thenThrow(new BatchJobException("", ""));

    // when
    schemaValidationTasklet.execute(null, null);

    // then
    verify(orderService, times(1)).validateXml();
  }
}
