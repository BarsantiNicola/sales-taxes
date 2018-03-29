package cloud.nimvps.exercises.salestaxes.tests;

public abstract class AbstractTest {

	static {
		final String logginfConfigFileKey = "java.util.logging.config.file";
		String fName = System.getProperty(logginfConfigFileKey);
		if (fName == null) {
			System.setProperty(logginfConfigFileKey, "logging.properties");
		}
	}

}
