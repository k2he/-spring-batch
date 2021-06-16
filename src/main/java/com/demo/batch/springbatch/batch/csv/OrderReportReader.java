package com.demo.batch.springbatch.batch.csv;

import com.demo.batch.springbatch.config.AppProperties;
import com.demo.batch.springbatch.model.Order;
import com.demo.batch.springbatch.repository.OrderRepository;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OrderReportReader extends RepositoryItemReader<Order> {

    @Autowired
    public OrderReportReader(AppProperties appProperties, OrderRepository orderRepository) {
        super();
        this.setRepository(orderRepository);
        this.setMethodName("findAll");

        Map<String, String> orderBy = appProperties.getReport().getOrderBy();
        final Map<String, Sort.Direction> sorts = orderBy.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> Sort.Direction.fromString(e.getValue())));

        this.setSort(sorts);
    }
}