package org.jpmorgan.supersimplestocks.service.impl;

import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import org.jpmorgan.supersimplestocks.exceptions.StockException;
import org.jpmorgan.supersimplestocks.exceptions.TradingOperationException;
import org.jpmorgan.supersimplestocks.persistence.model.StockEntity;
import org.jpmorgan.supersimplestocks.persistence.model.StockType;
import org.jpmorgan.supersimplestocks.persistence.model.TradingOperationEntity;
import org.jpmorgan.supersimplestocks.repository.StockRepository;
import org.jpmorgan.supersimplestocks.service.StockService;
import org.jpmorgan.supersimplestocks.service.model.TradingOperation;
import org.jpmorgan.supersimplestocks.service.utils.DateTimeUtils;
import org.jpmorgan.supersimplestocks.service.utils.TradingOperationUtils;
import org.jpmorgan.supersimplestocks.service.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.Data;

/**
 * Implementation of StockService to work with Stocks
 * 
 * @author Chuvilin Sergey
 *
 */

@Service
@Data
public class StockServiceImpl implements StockService {

	private Logger logger = Logger.getLogger(StockServiceImpl.class);

	@Autowired
	private StockRepository stockRepository;

	/**
	 * Performs Trade Operation on selected Stock
	 * 
	 * @return StockEntity which was updated
	 */
	@Transactional
	@Override
	public StockEntity performTradeOperation(TradingOperation tradingOperation) throws TradingOperationException {

		StockEntity stockEntity = stockRepository.findByLabel(tradingOperation.getStockLabel());

		ValidationUtils.validateStockEntity(stockEntity);

		TradingOperationEntity tradingOperationEntity = TradingOperationUtils
				.buildTradingOperationEntity(tradingOperation);

		ValidationUtils.validateTradingOperationEntity(tradingOperationEntity);

		stockEntity.setOperationalPrice(tradingOperation.getPrice());
		stockEntity.getTradingOperations().add(tradingOperationEntity);

		return stockRepository.save(stockEntity);

	}

	/**
	 * Calculate DividendYield for selected Stock
	 * 
	 * @return Double as the result
	 */
	@Transactional(readOnly = true)
	@Override
	public Double calcDividendYield(String stockSymbol, Double price) {

		Double dividendYield = null;

		ValidationUtils.validatePrice(price);

		StockEntity stockEntity = stockRepository.findByLabel(stockSymbol);

		ValidationUtils.validateStockEntity(stockEntity);

		if (stockEntity.getType() == StockType.COMMON) {
			dividendYield = stockEntity.getLastDividend() / price;
		} else if (stockEntity.getType() == StockType.PREFERRED) {
			dividendYield = stockEntity.getFixedDividend() * stockEntity.getOriginalPrice() / price;
		} else {
			logger.debug("Selected Stock has un unsupported stockType");
			throw new StockException("Selected Stock has un unsupported stockType");
		}

		return dividendYield;

	}

	/**
	 * Calculate calcPERatio for selected Stock and price
	 * 
	 * @return Double as the result
	 */
	@Transactional(readOnly = true)
	@Override
	public Double calcPERatio(String stockSymbol, Double price) {

		ValidationUtils.validatePrice(price);

		StockEntity stockEntity = stockRepository.findByLabel(stockSymbol);

		ValidationUtils.validateStockEntity(stockEntity);

		Double pERatio = price / calcDividendYield(stockSymbol, price);

		return pERatio;
	}


	@Transactional(readOnly = true)
	@Override
	public Double calcVolumeWeightedStockPriceOnTradesInPast5Min(String stockSymbol) {

		List<TradingOperationEntity> tradingOperations = stockRepository.getAllTradingOperationAfterProvidedTime(
				stockSymbol, DateTimeUtils.getCurrentTimeMinusProvidedMinutes(5));

		double sumOfTradingPriceWithQuantity = 0.0;
		double sumOfQuantity = 0.0;

		for (TradingOperationEntity toe : tradingOperations) {
			sumOfTradingPriceWithQuantity += (toe.getPrice() * toe.getStockQuantity());
			sumOfQuantity += toe.getStockQuantity();
		}

		double volumeWeightedStockPrice = 0.0;

		if (sumOfQuantity > 0.0) {
			volumeWeightedStockPrice = sumOfTradingPriceWithQuantity / sumOfQuantity;
		} else {
			logger.debug("SumOfQuantity in less then 0");
			// throw new StockException("SumOfQuantity in less then 0");
		}

		return volumeWeightedStockPrice;
	}

	/**
	 * Calculate GBCEAllShareIndex for all stock, based on calculation of
	 * calcVolumeWeightedStockPriceOnTradesInPast5Min() for each Stock
	 * 
	 * @return Double as the result
	 */
	@Transactional(readOnly = true)
	@Override
	public Double calcGBCEAllShareIndex() {

		double volumeWeightedForAllStocks = 0d;

		Collection<StockEntity> allStocks = (Collection<StockEntity>) stockRepository.findAll();

		if (allStocks == null || allStocks.size() <= 0) {
			logger.debug("No Stocks Found");
			throw new StockException("No Stocks Found");
		}

		for (StockEntity se : allStocks) {
			volumeWeightedForAllStocks += calcVolumeWeightedStockPriceOnTradesInPast5Min(se.getStockSymbol());
		}

		return Math.pow(volumeWeightedForAllStocks, 1.0 / allStocks.size());
	}

}
