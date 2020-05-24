package com.demo.batch.springbatch.batch.stage;

import java.util.List;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import com.demo.batch.springbatch.model.OrderStage;
import com.demo.batch.springbatch.repository.OrderStageRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author kaihe
 *
 */

@Component
@RequiredArgsConstructor
public class OrderStageWriter implements ItemWriter<OrderStage> {

  @NonNull
  private OrderStageRepository orderStageRepository;

  @Override
  public void write(List<? extends OrderStage> items) throws Exception {
    orderStageRepository.saveAll(items);
  }
}
