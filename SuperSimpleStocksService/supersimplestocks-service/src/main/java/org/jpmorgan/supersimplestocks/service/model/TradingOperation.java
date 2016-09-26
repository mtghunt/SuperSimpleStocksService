package org.jpmorgan.supersimplestocks.service.model;

import java.util.Date;
import org.jpmorgan.supersimplestocks.persistence.model.TradingOperationType;
import lombok.Data;

/**
 * Trading Operation service level model
 * 
 * @author Chuvilin Sergey
 *
 */
@Data
public class TradingOperation {

	private Date time;

	private TradingOperationType tradingOperationType;

	private Integer stockQuantity;

	private Double price;

	private String stockLabel;

}
