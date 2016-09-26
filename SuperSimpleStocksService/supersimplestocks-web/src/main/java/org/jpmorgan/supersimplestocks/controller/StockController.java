package org.jpmorgan.supersimplestocks.controller;

import java.util.Date;
import org.apache.log4j.Logger;
import org.jpmorgan.supersimplestocks.persistence.model.TradingOperationType;
import org.jpmorgan.supersimplestocks.service.StockService;
import org.jpmorgan.supersimplestocks.service.model.TradingOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.Data;

/**
 * Expose stock Service as REST services
 * 
 *
 */
@RestController
@RequestMapping("/stocks")
@Data
public class StockController {

	private Logger logger = Logger.getLogger(StockController.class);

	@Autowired
	private StockService stockService;

	/**
	 * Perform Trading Operation
	 * 
	 * @param stockLabel
	 * @param tradingOperationType
	 * @param stockQuantity
	 * @param price
	 * @param model
	 * @return Success or Fail message
	 */
	@RequestMapping("/performTradingOperation/{stockSymbol}/{tradingOperationType}/{stockQuantity}/{price}")
	public String performTradingOperation(@PathVariable String stockSymbol, @PathVariable String tradingOperationType,
			@PathVariable Integer stockQuantity, @PathVariable Double price) {

		try {
			TradingOperation tradingOperation = new TradingOperation();

			tradingOperation.setStockLabel(stockSymbol);
			tradingOperation.setTradingOperationType(TradingOperationType.valueOf(tradingOperationType));
			tradingOperation.setStockQuantity(stockQuantity);
			tradingOperation.setTime(new Date());
			tradingOperation.setPrice(price);

			stockService.performTradeOperation(tradingOperation);
		} catch (Exception e) {
			logger.debug("Trading Operation Not performed!", e);
			return "Trading Operation Not performed!";
		}

		logger.debug("Trading Operation Not performed!");
		return "Trading Operation performed!";
	}

	/**
	 * Calculate DividendYield for given Stock and Price
	 * 
	 * @param stockSymbol
	 * @param price
	 * @return Double
	 */
	@RequestMapping("/calcDividendYield/{stockSymbol}/{price}")
	public Double calcDividendYield(@PathVariable String stockSymbol, @PathVariable Double price) {
		return stockService.calcDividendYield(stockSymbol, price);
	}

	/**
	 * Calculate PERatio for given Stock and Price
	 * 
	 * @param stockSymbol
	 * @param price
	 * @return Double
	 */
	@RequestMapping("/calcPERatio/{stockSymbol}/{price}")
	public Double calcPERatio(@PathVariable String stockSymbol, @PathVariable Double price) {
		return stockService.calcPERatio(stockSymbol, price);
	}

	/**
	 * Calculate VolumeWeightedStockPrice of trades in past 5 min for a given
	 * Stock
	 * 
	 * @param stockSymbol
	 * @return Double
	 */
	@RequestMapping("/calcVolumeWeightedStockPriceOnTradesInPast5Min/{stockSymbol}")
	public Double calcVolumeWeightedStockPriceOnTradesInPast5Min(@PathVariable String stockSymbol) {
		return stockService.calcVolumeWeightedStockPriceOnTradesInPast5Min(stockSymbol);
	}

	/**
	 * Calculate GBCEAllShareIndex for all Stocks
	 * 
	 * @return Double
	 */
	@RequestMapping("/calculateGBCEAllShareIndex")
	public Double calcGBCEAllShareIndex() {
		return stockService.calcGBCEAllShareIndex();
	}

}
