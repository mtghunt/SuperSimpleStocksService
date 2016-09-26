package org.jpmorgan.supersimplestocks.persistence.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Describes structure of TradingOperation type
 * 
 * @author Chuvilin Sergey
 *
 */
@Entity
@Table(name = "Trading_Opetations")
@Data
@EqualsAndHashCode(of = { "id" })
public class TradingOperationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Date time;

	private TradingOperationType tradingOperationType;

	private Integer stockQuantity = 0;

	private Double price = 0.0;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STOCK_ID", insertable = false, updatable = false)
	private StockEntity stock;

}
