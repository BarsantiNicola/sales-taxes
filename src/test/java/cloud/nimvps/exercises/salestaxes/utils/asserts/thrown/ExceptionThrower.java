package cloud.nimvps.exercises.salestaxes.utils.asserts.thrown;

@FunctionalInterface
public interface ExceptionThrower {
	void throwException() throws Throwable;
}