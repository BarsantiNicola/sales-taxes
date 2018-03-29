package cloud.nimvps.exercises.salestaxes.core;

/**
 * Exception that occurs in processor operations
 */
public class SalesTaxesException extends Exception {

	private static final long serialVersionUID = 1L;

	public SalesTaxesException() {
		super();
	}

	public SalesTaxesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SalesTaxesException(String message, Throwable cause) {
		super(message, cause);
	}

	public SalesTaxesException(String message) {
		super(message);
	}

	public SalesTaxesException(Throwable cause) {
		super(cause);
	}

}
