package org.jpmorgan.supersimplestocks.exceptions;

/**
 * Exception of all kind of circumstances with Stocks
 * 
 * @author Chuvilin Sergey
 *
 */
public class StockException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234234324L;

	public StockException(String description) {
		super(description);
	}

}
