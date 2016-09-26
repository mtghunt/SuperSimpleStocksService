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
 * Tests all the cases with calcPERatio() invocation of StockService
 * 
 * @author Chuvilin Sergey
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SuperSimpleStocksAppConfig.class, loader = AnnotationConfigContextLoader.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class StockServiceCalcPERatioTest {

	private static final double TEST_PRICE_WRONG = -1.0;
	private static final double TEST_PRICE = 10000.0;
	@Autowired
	private StockService stockService;

	/**
	 * Check Exception throwing with empty stock type
	 */
	@Test(expected = StockException.class)
	public void checkEmptyStockType() {
		stockService.calcPERatio("BROKEN_STOCK", 0d);
	}

	/**
	 * Check Exception throwing with Incorrect Price as an input
	 */
	@Test(expected = StockException.class)
	public void checkIncorrectPrice() {
		stockService.calcPERatio("GIN", TEST_PRICE_WRONG);
	}

	/**
	 * Check Exception throwing with null Price as an input
	 */
	@Test(expected = StockException.class)
	public void checkNullPrice() {
		stockService.calcPERatio("GIN", null);
	}

	/**
	 * Check Exception throwing with unsupported stock type
	 */
	@Test(expected = StockException.class)
	public void checkUnSupportedStock() {
		stockService.calcPERatio("GIN1", TEST_PRICE_WRONG);
	}

	@Test
	public void checkResultForCommonStockType() {

		Double pERatio = stockService.calcPERatio("ALE", TEST_PRICE);

		assertNotNull("dividendYield should not be null", pERatio);

		assertEquals("Checks result of dividendYield", new Double(4347826.0869565215), pERatio);

	}

	@Test
	public void checkResultForPreferredStockType() {

		Double pERatio = stockService.calcPERatio("GIN", TEST_PRICE);

		assertNotNull("dividendYield should not be null", pERatio);

		assertEquals("Checks result of dividendYield", new Double(500000.0), pERatio);

	}

}
