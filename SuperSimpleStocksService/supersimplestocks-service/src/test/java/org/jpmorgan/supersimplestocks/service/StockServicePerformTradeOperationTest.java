package org.jpmorgan.supersimplestocks.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jpmorgan.supersimplestocks.app.config.SuperSimpleStocksAppConfig;
import org.jpmorgan.supersimplestocks.exceptions.StockException;
import org.jpmorgan.supersimplestocks.exceptions.TradingOperationException;
import org.jpmorgan.supersimplestocks.persistence.model.StockEntity;
import org.jpmorgan.supersimplestocks.persistence.model.TradingOperationType;
import org.jpmorgan.supersimplestocks.service.model.TradingOperation;
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
 * Tests all the cases with performTradeOperation() invocation of StockService
 * 
 * @author Chuvilin Sergey
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SuperSimpleStocksAppConfig.class, loader = AnnotationConfigContextLoader.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class StockServicePerformTradeOperationTest {

	TradingOperation to1, to2 = null;

	@Autowired
	private StockService stockService;

	/**
	 * Populate trading operations for all test cases
	 */
	@Before
	public void configExamplesOfTradingOperations() {
		to1 = new TradingOperation();
		to1.setPrice(12.3);
		to1.setStockLabel("GIN");
		to1.setStockQuantity(230);
		to1.setTime(new Date());
		to1.setTradingOperationType(TradingOperationType.BUY);

		to2 = new TradingOperation();
		to2.setPrice(333.3);
		to2.setStockLabel("GIN");
		to2.setStockQuantity(100);
		to2.setTime(new Date());
		to2.setTradingOperationType(TradingOperationType.SELL);
	}

	/**
	 * Check TradingOperation saved correctly
	 */
	@Test
	public void checkSuccesfullSavedTradingOperation() {
		StockEntity stockEntity = stockService.performTradeOperation(to1);

		assertNotNull("StockEntity should not be null", stockEntity);
		assertNotNull("Trading Operations should not be null", stockEntity.getTradingOperations());
		assertNotNull("First Trading Operation should not be null", stockEntity.getTradingOperations().get(0));

		assertEquals("Checks that Price successfully saved", to1.getPrice(),
				stockEntity.getTradingOperations().get(0).getPrice());
		assertEquals("Checks that Quantity successfully saved", to1.getStockQuantity(),
				stockEntity.getTradingOperations().get(0).getStockQuantity());
		assertEquals("Checks that TradingOperationType successfully saved", to1.getTradingOperationType(),
				stockEntity.getTradingOperations().get(0).getTradingOperationType());
	}

	/**
	 * Check Stock updated correctly after a new TradingOperation performed
	 */
	@Test
	public void checkSuccesfullUpdateOfStockAftedTradingOperationPerformed() {

		StockEntity stockEntity = stockService.performTradeOperation(to1);

		assertNotNull("StockEntity should not be null", stockEntity);
		assertNotNull("Trading Operations should not be null", stockEntity.getTradingOperations());

		assertEquals("Stock has only one Trading Operation", 1, stockEntity.getTradingOperations().size());
		assertNotNull("First Trading Operation should not be null", stockEntity.getTradingOperations().get(0));

		assertEquals("Checks that Price successfully updated", to1.getPrice(), stockEntity.getOperationalPrice());

		stockEntity = stockService.performTradeOperation(to2);

		assertEquals("Stock has two Trading Operation", 2, stockEntity.getTradingOperations().size());
		assertEquals("Checks that Price successfully updated from last Trading Operation", to2.getPrice(),
				stockEntity.getOperationalPrice());

	}

	/**
	 * Check date-time saved correctly
	 */
	@Test
	public void checkSuccesfullSavedTradingOperationsTime() {
		StockEntity stockEntity = stockService.performTradeOperation(to1);

		assertNotNull("StockEntity should not be null", stockEntity);
		assertNotNull("Trading Operations should not be null", stockEntity.getTradingOperations());
		assertNotNull("First Trading Operation should not be null", stockEntity.getTradingOperations().get(0));

		assertNotNull("Original Trading Operation Time should not be null", to1.getTime());

		Date originalDateNotFormated = stockEntity.getTradingOperations().get(0).getTime();
		assertNotNull("Saved Trading Operation Time should not be null", originalDateNotFormated);

		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		String originalDate = formatter.format(to1.getTime());
		String savedDate = formatter.format(stockEntity.getTradingOperations().get(0).getTime());

		assertEquals("Time should be saved corretly for Trading Operation", originalDate, savedDate);
	}

	/**
	 * Check perform Trading operation with negative price (-100.0)
	 */
	@Test(expected = TradingOperationException.class)
	public void checkUnSupprotedTradingOperationWithNegativePrice() {
		to1.setPrice(-100.0);
		stockService.performTradeOperation(to1);
	}

	/**
	 * Check perform Trading operation with negative stock quantity (-10)
	 */
	@Test(expected = TradingOperationException.class)
	public void checkUnSupprotedTradingOperationWithNegativeStockQuantity() {
		to1.setStockQuantity(-10);
		stockService.performTradeOperation(to1);
	}

	/**
	 * Check unsupported stock type
	 */
	@Test(expected = StockException.class)
	public void checkUnSupportedStock() {
		to1.setStockLabel("GIN1");
		stockService.performTradeOperation(to1);
	}

}