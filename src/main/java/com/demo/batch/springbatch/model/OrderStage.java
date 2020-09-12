package com.demo.batch.springbatch.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kaihe
 *
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders_stage")
@NamedStoredProcedureQuery(name = "moveStageToOrderTable",
    procedureName = "move_stage_to_orders_table", resultClasses = OrderStage.class)
public class OrderStage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String orderRef;

  private BigDecimal amount;

  private LocalDate orderDate;

  private String note;

  @Lob
  @Column(columnDefinition = "CLOB NOT NULL")
  private String productJson;

  @CreatedDate
  private LocalDateTime createdDateTime;
}
