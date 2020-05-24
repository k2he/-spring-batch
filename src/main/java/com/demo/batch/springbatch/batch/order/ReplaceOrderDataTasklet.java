package com.demo.batch.springbatch.batch.order;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.model.Order;
import com.demo.batch.springbatch.model.OrderHistory;
import com.demo.batch.springbatch.repository.OrderHistoryRepository;
import com.demo.batch.springbatch.repository.OrderRepository;
import com.demo.batch.springbatch.service.OrderService;
import com.demo.batch.springbatch.util.BatchConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author kaihe
 *
 */

@Component
@RequiredArgsConstructor
public class ReplaceOrderDataTasklet implements Tasklet {
  
  @NonNull
  private OrderRepository orderRepository;

  @NonNull
  private OrderHistoryRepository orderHistoryRepository;

  @NonNull
  private OrderService orderService;

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    ExecutionContext jobExecutionContext =
        contribution.getStepExecution().getJobExecution().getExecutionContext();
    Long currentSeqValue =
        jobExecutionContext.getLong(BatchConstants.ORDER_NEXT_SEQ_VALUE, 0);

    if (currentSeqValue > 0) {
      // Move old data from PROD_CAT_EXTRACT to PROD_CAT_EXTRACT_HIST tables
      List<Order> oldOrders = orderRepository.findAllLessThanGivenId(currentSeqValue);
      if (!oldOrders.isEmpty()) {
        List<OrderHistory> orderHistList = oldOrders.stream()
            .map(order -> orderService.convertToOrderHist(order))
            .collect(Collectors.toList());

        // Save into PROD_CAT_EXTRACT_HIST table
        orderHistoryRepository.saveAll(orderHistList);

        // Remove all from PROD_CAT_EXTRACT Table
        orderRepository.deleteInBatch(oldOrders);
      }
    }
    return RepeatStatus.FINISHED;
  }

}
