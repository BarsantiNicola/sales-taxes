package cloud.nimvps.exercises.salestaxes.core.utils;

/**
 * Exception that occurs in processor operations
 */
public class BooleanFormatException extends Exception {

	private static final long serialVersionUID = 1L;

	public BooleanFormatException() {
		super();
	}

	public BooleanFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BooleanFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public BooleanFormatException(String message) {
		super(message);
	}

	public BooleanFormatException(Throwable cause) {
		super(cause);
	}

}
