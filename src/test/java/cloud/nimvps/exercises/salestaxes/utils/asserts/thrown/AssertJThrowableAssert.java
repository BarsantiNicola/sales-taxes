package cloud.nimvps.exercises.salestaxes.utils.asserts.thrown;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;

// https://github.com/kolorobot/unit-testing-demo/tree/master/src/test/java/com/github/kolorobot/exceptions/java8
public class AssertJThrowableAssert {
	public static ThrowableAssert assertThrown(ExceptionThrower exceptionThrower) {
		try {
			exceptionThrower.throwException();
		} catch (Throwable throwable) {
			return (ThrowableAssert) Assertions.assertThat(throwable);
		}
		throw new ExceptionNotThrownAssertionError();
	}
}