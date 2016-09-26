package org.jpmorgan.supersimplestocks.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Date;
import java.util.List;
import org.jpmorgan.supersimplestocks.app.config.SuperSimpleStocksAppConfig;
import org.jpmorgan.supersimplestocks.persistence.model.TradingOperationEntity;
import org.jpmorgan.supersimplestocks.persistence.model.TradingOperationType;
import org.jpmorgan.supersimplestocks.service.StockService;
import org.jpmorgan.supersimplestocks.service.model.TradingOperation;
import org.jpmorgan.supersimplestocks.service.utils.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Tests all the cases with getAllTradingOperationAfterProvidedTime() invocation
 * of StockRepository
 * 
 * @author Chuvilin Sergey
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SuperSimpleStocksAppConfig.class, loader = AnnotationConfigContextLoader.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class StockRepositoryGetAllTradingOperationAfterProvidedTime {

	TradingOperation to1, to2, to3 = null;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private StockService stockService;

	/**
	 * Populate trading operations for all test cases
	 */
	@Before
	public void configExamplesOfTradingOperations() {
		// Trading operation in current Time
		to1 = new TradingOperation();
		to1.setPrice(100.0);
		to1.setStockLabel("GIN");
		to1.setStockQuantity(10);
		to1.setTime(new Date());
		to1.setTradingOperationType(TradingOperationType.BUY);

		stockService.performTradeOperation(to1);

		// Trading operation 2 min ago
		to2 = new TradingOperation();
		to2.setPrice(10.0);
		to2.setStockLabel("GIN");
		to2.setStockQuantity(2);
		to2.setTime(DateTimeUtils.getCurrentTimeMinusProvidedMinutes(2));
		to2.setTradingOperationType(TradingOperationType.SELL);

		stockService.performTradeOperation(to2);

		// Trading operation 6 min ago
		to3 = new TradingOperation();
		to3.setPrice(3331.3);
		to3.setStockLabel("GIN");
		to3.setStockQuantity(1001);
		to3.setTime(DateTimeUtils.getCurrentTimeMinusProvidedMinutes(6));
		to3.setTradingOperationType(TradingOperationType.BUY);

		stockService.performTradeOperation(to3);
	}

	@Test
	public void checkGetAllTradingOperationAfterProvidedTime() {
		List<TradingOperationEntity> tradingOperations = stockRepository.getAllTradingOperationAfterProvidedTime("GIN",
				DateTimeUtils.getCurrentTimeMinusProvidedMinutes(5));

		assertNotNull("TradingOperations should not be null", tradingOperations);
		assertEquals("TradingOperations in last 5 min should not be only 2", 2, tradingOperations.size());
	}

}
