package org.jpmorgan.supersimplestocks.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.jpmorgan.supersimplestocks.app.config.SuperSimpleStocksAppConfig;
import org.jpmorgan.supersimplestocks.exceptions.StockException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Tests all the cases with calcDividendYield() invocation of StockService
 * 
 * @author Chuvilin Sergey
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SuperSimpleStocksAppConfig.class, loader = AnnotationConfigContextLoader.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class StockServiceCalcDividendYieldTest {

	private static final double WRONG_PRICE = -1.0;
	private static final double VALID_PRICE = 10.0;

	@Autowired
	private StockService stockService;

	/**
	 * Check Exception throwing with empty stock type
	 */
	@Test(expected = StockException.class)
	public void checkEmptyStockType() {
		stockService.calcDividendYield("BROKEN_STOCK", 0d);
	}

	/**
	 * Check Exception throwing with Incorrect Price as an input
	 */
	@Test(expected = StockException.class)
	public void checkIncorrectPrice() {
		stockService.calcDividendYield("GIN", WRONG_PRICE);
	}

	/**
	 * Check Exception throwing with null Price as an input
	 */
	@Test(expected = StockException.class)
	public void checkNullPrice() {
		stockService.calcDividendYield("GIN", null);
	}

	/**
	 * Check Exception throwing with unsupported stock type
	 */
	@Test(expected = StockException.class)
	public void checkUnSupportedStock() {
		stockService.calcDividendYield("GIN1", VALID_PRICE);
	}

	@Test
	public void checkResultForCommonStockType() {

		Double dividendYield = stockService.calcDividendYield("ALE", VALID_PRICE);

		assertNotNull("dividendYield should not be null", dividendYield);

		assertEquals("Checks result of dividendYield", new Double(2.3), dividendYield);

	}

	@Test
	public void checkResultForPreferredStockType() {

		Double dividendYield = stockService.calcDividendYield("GIN", VALID_PRICE);

		assertNotNull("dividendYield should not be null", dividendYield);

		assertEquals("Checks result of dividendYield", new Double(20.0), dividendYield);

	}

}
