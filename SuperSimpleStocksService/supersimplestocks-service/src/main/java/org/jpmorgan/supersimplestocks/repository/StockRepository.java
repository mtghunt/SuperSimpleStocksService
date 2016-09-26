package org.jpmorgan.supersimplestocks.repository;

import java.util.Date;
import java.util.List;
import org.jpmorgan.supersimplestocks.persistence.model.StockEntity;
import org.jpmorgan.supersimplestocks.persistence.model.TradingOperationEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to work with StockEntity instances
 * 
 * @author Chuvilin Sergey
 *
 */
@Repository
public interface StockRepository extends CrudRepository<StockEntity, Long>, JpaSpecificationExecutor<StockEntity> {

	/**
	 * Finds a Stock by its stockSymbol
	 * 
	 * @param stockSymbol
	 * @return StockEntity
	 */
	@Query("select stock from StockEntity stock where stock.stockSymbol = ?1")
	StockEntity findByLabel(String stockSymbol);

	/**
	 * Finds all Trading Operation Entities for defined stock and before difed
	 * time stamp
	 * 
	 * @param stockSymbol
	 * @param time
	 * @return collection of TradingOperationEntities
	 */
	@Query("select to from TradingOperationEntity to where to.stock.stockSymbol = ?1 and to.time > ?2")
	List<TradingOperationEntity> getAllTradingOperationAfterProvidedTime(String stockSymbol, Date time);

}
