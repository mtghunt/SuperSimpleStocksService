package org.jpmorgan.supersimplestocks.service.utils;

import org.apache.log4j.Logger;
import org.jpmorgan.supersimplestocks.exceptions.StockException;
import org.jpmorgan.supersimplestocks.exceptions.TradingOperationException;
import org.jpmorgan.supersimplestocks.persistence.model.StockEntity;
import org.jpmorgan.supersimplestocks.persistence.model.TradingOperationEntity;

/**
 * Validation Utilities
 * 
 * @author Chuvilin Sergey
 *
 */
public class ValidationUtils {

	private static Logger logger = Logger.getLogger(ValidationUtils.class);

	/**
	 * Checks that price is valid
	 * 
	 * @param price
	 */
	public static void validatePrice(Double price) {
		if (price == null || price < 0.0) {
			logger.debug("Dividend Yield cant be counted for unsupported price");
			throw new StockException("Dividend Yield cant be counted for unsupported price");
		}
	}

	/**
	 * Validates Stock Entity
	 * 
	 * @param stockEntity
	 */
	public static void validateStockEntity(StockEntity stockEntity) {
		if (stockEntity == null) {
			logger.debug("This Stock is Not supported by system");
			throw new StockException("This Stock is Not supported by system");
		}
	}

	/**
	 * Validates TradingOperation Entity
	 * 
	 * @param tradingOperationEntity
	 */
	public static void validateTradingOperationEntity(TradingOperationEntity tradingOperationEntity) {
		if (tradingOperationEntity == null || tradingOperationEntity.getPrice() < 0
				|| tradingOperationEntity.getStockQuantity() < 0) {
			logger.debug("Trading Operation can not be performed");
			throw new TradingOperationException("Trading Operation can not be performed");
		}
	}

}
