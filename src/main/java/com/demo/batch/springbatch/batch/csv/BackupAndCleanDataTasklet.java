package com.demo.batch.springbatch.batch.csv;

import com.demo.batch.springbatch.model.Order;
import com.demo.batch.springbatch.model.OrderHistory;
import com.demo.batch.springbatch.repository.OrderHistoryRepository;
import com.demo.batch.springbatch.repository.OrderRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This Tasklet move FulfillmentStatusRecord into FulfillmentStatusRecordHistory documents
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BackupAndCleanDataTasklet implements Tasklet {

    @NonNull
    private OrderRepository orderRepository;

    @NonNull
    private OrderHistoryRepository orderHistoryRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
            throws Exception {
        List<Order> allRecords = orderRepository.findAll();
        log.info("Moving {} records from fulfillmentAudit into fulfillmentAuditHistory.", allRecords.size());
        List<OrderHistory> allRecordsHistory = allRecords.stream()
                .map(OrderHistory::mapToHistory)
                .collect(Collectors.toList());

        /** TODO: Ideally, we should do in
         *   1) stored procedure
         *   2) mongoDB aggreggate merge
         *  so all data movement happens in Database or MongoDB server, but current Spring Data Mongo version doesn't support this, need newer version of
         *  spring-boot-starter-data-mongodb
         */
        orderHistoryRepository.saveAll(allRecordsHistory);

        // Remove all data from FulfillmentAudit
        orderRepository.deleteAll();

        return RepeatStatus.FINISHED;
    }
}
