package cloud.nimvps.exercises.salestaxes.utils.asserts.thrown;

public class ThrowableCaptor {
	public static Throwable captureThrowable(ExceptionThrower exceptionThrower) {
		try {
			exceptionThrower.throwException();
			return null;
		} catch (Throwable caught) {
			return caught;
		}
	}
}