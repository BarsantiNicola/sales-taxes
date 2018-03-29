package cloud.nimvps.exercises.salestaxes.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

public abstract class UnitUtils {

	public static InputStream localStream(String path) {
		return UnitUtils.class.getResourceAsStream(path);
	}

	public static List<String> getResourceFiles(String path) throws IOException {
		List<String> filenames = new ArrayList<>();

		try (InputStream in = UnitUtils.localStream(path); //
				BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			String resource;
			while ((resource = br.readLine()) != null) {
				filenames.add(resource);
			}
		}

		return filenames;
	}

	public static void assertEquals(String expectedReceipt, String receipt) {
		expectedReceipt = expectedReceipt.replaceAll("\r\n", "\n").trim();
		receipt = receipt.replaceAll("\r\n", "\n").trim();
		Assert.assertEquals(expectedReceipt, receipt);
	}

}
