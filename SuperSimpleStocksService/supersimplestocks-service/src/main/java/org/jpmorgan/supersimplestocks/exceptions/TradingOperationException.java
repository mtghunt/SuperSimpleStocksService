package org.jpmorgan.supersimplestocks.exceptions;

/**
 * Exception of all kind of circumstances with Trading Operations
 * 
 * @author Chuvilin Sergey
 *
 */
public class TradingOperationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234234L;

	public TradingOperationException(String description) {
		super(description);
	}

}
