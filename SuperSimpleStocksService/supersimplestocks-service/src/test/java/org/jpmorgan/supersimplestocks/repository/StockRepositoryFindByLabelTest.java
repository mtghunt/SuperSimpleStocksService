package org.jpmorgan.supersimplestocks.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.jpmorgan.supersimplestocks.app.config.SuperSimpleStocksAppConfig;
import org.jpmorgan.supersimplestocks.persistence.model.StockEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Tests all the cases with findByLabel() invocation of StockRepository
 * 
 * @author Chuvilin Sergey
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SuperSimpleStocksAppConfig.class, loader = AnnotationConfigContextLoader.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class StockRepositoryFindByLabelTest {

	@Autowired
	private StockRepository stockRepository;

	/**
	 * Check Exception throwing with empty stock type
	 */
	@Test
	public void checkFindByLabelUnSupportedStockEntity() {
		StockEntity stockEntity = stockRepository.findByLabel("GIN1");

		assertNull("StockEntity should be not Found", stockEntity);
	}

	@Test
	public void checkFindByLabelSupportedStockEntity() {
		StockEntity stockEntity = stockRepository.findByLabel("GIN");

		assertNotNull("StockEntity should be Found", stockEntity);
	}

}
