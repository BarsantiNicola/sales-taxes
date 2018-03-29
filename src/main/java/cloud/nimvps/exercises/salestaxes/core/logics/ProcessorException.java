package cloud.nimvps.exercises.salestaxes.core.logics;

/**
 * Exception that occurs in processor operations
 */
public class ProcessorException extends Exception {

	private static final long serialVersionUID = 1L;

	public ProcessorException() {
		super();
	}

	public ProcessorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ProcessorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProcessorException(String message) {
		super(message);
	}

	public ProcessorException(Throwable cause) {
		super(cause);
	}

}
