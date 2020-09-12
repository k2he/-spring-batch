package com.demo.batch.springbatch;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.demo.batch.springbatch.batch.OrderJobListener;
import com.demo.batch.springbatch.batch.order.MoveStageToOrderAndBackupTasklet;
import com.demo.batch.springbatch.config.BatchConfig;
import com.demo.batch.springbatch.repository.OrderRepository;
import com.demo.batch.springbatch.repository.OrderStageRepository;

@RunWith(SpringRunner.class)
@SpringBatchTest
@EnableConfigurationProperties
@TestPropertySource("/application.properties")
@ContextConfiguration(
    classes = {SpringBatchApplication.class, BatchConfig.class, OrderJobListener.class},
    initializers = ConfigFileApplicationContextInitializer.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class})
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class OrderBatchIntegrationTests {

  @MockBean
  private OrderStageRepository orderStageRepository;

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;

  @Configuration
  public class JobconfigurationTest {
    @Bean
    public JobLauncherTestUtils jobLauncherTestUtils() {
      return new JobLauncherTestUtils();
    }

    // We need move repository since test Database (hsqldb) doesn't support store procedure.
    @Bean
    public MoveStageToOrderAndBackupTasklet getMoveStageToPccExtraxtAndBackupTasklet() {
      return new MoveStageToOrderAndBackupTasklet(orderStageRepository);
    }
  }

  @Test
  public void testJob() throws Exception {
    JobExecution jobExecution = jobLauncherTestUtils.launchJob();
    OrderJobListener jobListener = new OrderJobListener();
    jobListener.beforeJob(jobExecution);
    jobListener.afterJob(jobExecution);
    assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode()); // This doesn't work,
                                                                           // need more time to
                                                                           // figure out why.
  }
}
