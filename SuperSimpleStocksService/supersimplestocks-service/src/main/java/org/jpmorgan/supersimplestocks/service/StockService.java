package org.jpmorgan.supersimplestocks.service;

import org.jpmorgan.supersimplestocks.exceptions.TradingOperationException;
import org.jpmorgan.supersimplestocks.persistence.model.StockEntity;
import org.jpmorgan.supersimplestocks.service.model.TradingOperation;

/**
 * Service to work with Stocks
 * 
 * @author Chuvilin Sergey
 *
 */
public interface StockService {

	/**
	 * Performs Trade Operation on selected Stock
	 * 
	 * @return StockEntity which was updated
	 */
	StockEntity performTradeOperation(TradingOperation tradingOperation) throws TradingOperationException;

	/**
	 * Calculate DividendYield for selected Stock
	 * 
	 * @return Double as the result
	 */
	Double calcDividendYield(String stockSymbol, Double price);

	/**
	 * Calculate calcPERatio for selected Stock and price
	 * 
	 * @return Double as the result
	 */
	Double calcPERatio(String stockSymbol, Double price);

	/**
	 * Calculate VolumeWeightedStockPrice based on Trade operation inPast 5 Min
	 * for selected Stock
	 * 
	 * @return Double as the result
	 */
	Double calcVolumeWeightedStockPriceOnTradesInPast5Min(String stockSymbol);

	/**
	 * Calculate GBCEAllShareIndex for all stock, based on calculation of
	 * calcVolumeWeightedStockPriceOnTradesInPast5Min() for each Stock
	 * 
	 * @return Double as the result
	 */
	Double calcGBCEAllShareIndex();

}
