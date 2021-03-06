package org.jpmorgan.supersimplestocks.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.jpmorgan.supersimplestocks.app.config.SuperSimpleStocksAppConfig;
import org.jpmorgan.supersimplestocks.persistence.model.TradingOperationType;
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
 * 
 * @author Chuvilin Sergey
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SuperSimpleStocksAppConfig.class, loader = AnnotationConfigContextLoader.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class StockServiceCalculateGBCEAllShareIndexTest {

	private static final double GBCE_ALL_SHARE_INDEX_RESULT = 3.1972323377929706;

	TradingOperation to1, to2, to3, to4, to5 = null;

	@Autowired
	private StockService stockService;

	/**
	 * Populate trading operations for all test cases
	 */
	@Before
	public void configExamplesOfTradingOperations() {

		// Stock - GIN

		// Trading operation in current Time
		to1 = new TradingOperation();
		to1.setPrice(1000.0);
		to1.setStockLabel("GIN");
		to1.setStockQuantity(10);
		to1.setTime(new Date());
		to1.setTradingOperationType(TradingOperationType.BUY);

		stockService.performTradeOperation(to1);

		// Trading operation 2 min ago
		to2 = new TradingOperation();
		to2.setPrice(100.0);
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

		// Stock - ALE

		// Trading operation in current Time
		to4 = new TradingOperation();
		to4.setPrice(300.0);
		to4.setStockLabel("ALE");
		to4.setStockQuantity(15);
		to4.setTime(new Date());
		to4.setTradingOperationType(TradingOperationType.BUY);

		stockService.performTradeOperation(to1);

		// Trading operation 2 min ago
		to5 = new TradingOperation();
		to5.setPrice(150.0);
		to5.setStockLabel("ALE");
		to5.setStockQuantity(25);
		to5.setTime(DateTimeUtils.getCurrentTimeMinusProvidedMinutes(2));
		to5.setTradingOperationType(TradingOperationType.SELL);

		stockService.performTradeOperation(to5);
	}

	@Test
	public void checkCalculateGBCEAllShareIndex() {

		Double gBCEAllShareIndex = stockService.calcGBCEAllShareIndex();

		assertNotNull("gBCEAllShareIndex should not be null", gBCEAllShareIndex);

		assertEquals("Checks result of gBCEAllShareIndex", Double.valueOf(GBCE_ALL_SHARE_INDEX_RESULT),
				gBCEAllShareIndex);

	}

}
