package cloud.nimvps.exercises.salestaxes.core.utils;

public abstract class TypesUtils {

	public static Boolean parseBoolean(String boolStr) throws BooleanFormatException {
		if (!boolStr.equalsIgnoreCase("true") && !boolStr.equalsIgnoreCase("false")) {
			throw new BooleanFormatException("Boolean value must be 'true' or 'false'");
		}
		return Boolean.parseBoolean(boolStr);
	}

}
