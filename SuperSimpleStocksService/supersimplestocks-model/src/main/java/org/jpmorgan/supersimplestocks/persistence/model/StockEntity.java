package org.jpmorgan.supersimplestocks.persistence.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Describes structure of Stock Entity
 * 
 * @author Chuvilin Sergey
 *
 */
@Entity
@Table(name = "Stocks")
@Data
@EqualsAndHashCode(of = { "id" })
public class StockEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String stockSymbol;
	private StockType type;
	private Double lastDividend = 0.0;
	private Double fixedDividend = 0.0;
	private Integer originalPrice;
	private Double operationalPrice = 0.0;

	
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "STOCK_ID", nullable = false)
	public List<TradingOperationEntity> tradingOperations;

}
