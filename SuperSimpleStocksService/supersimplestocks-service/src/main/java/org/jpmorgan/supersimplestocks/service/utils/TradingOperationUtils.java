package org.jpmorgan.supersimplestocks.service.utils;

import org.jpmorgan.supersimplestocks.persistence.model.TradingOperationEntity;
import org.jpmorgan.supersimplestocks.service.model.TradingOperation;

/**
 * Trading Operations utilities
 * 
 * @author Chuvilin Sergey
 *
 */
public class TradingOperationUtils {

	/**
	 * Builds TradingOperation based on TradingOperation Entity retrieved from
	 * DB
	 * 
	 * @param tradingOperation
	 * @return TradingOperationEntity or null
	 */
	public static TradingOperationEntity buildTradingOperationEntity(TradingOperation tradingOperation) {

		if (tradingOperation != null) {
			TradingOperationEntity entity = new TradingOperationEntity();
			entity.setPrice(tradingOperation.getPrice());
			entity.setStockQuantity(tradingOperation.getStockQuantity());
			entity.setTime(tradingOperation.getTime());
			entity.setTradingOperationType(tradingOperation.getTradingOperationType());
			return entity;
		}
		return null;
	}

}
