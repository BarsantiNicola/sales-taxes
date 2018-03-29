package cloud.nimvps.exercises.salestaxes.utils.asserts.thrown;

public class ExceptionNotThrownAssertionError extends AssertionError {

	private static final long serialVersionUID = 1L;

	public ExceptionNotThrownAssertionError() {
		super("Expected exception was not thrown.");
	}
}