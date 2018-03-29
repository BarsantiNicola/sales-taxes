package cloud.nimvps.exercises.salestaxes.tests;

import static cloud.nimvps.exercises.salestaxes.utils.asserts.thrown.AssertJThrowableAssert.assertThrown;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import cloud.nimvps.exercises.salestaxes.SalesTaxesMain;
import cloud.nimvps.exercises.salestaxes.core.SalesTaxesException;
import cloud.nimvps.exercises.salestaxes.core.utils.StreamUtils;
import cloud.nimvps.exercises.salestaxes.utils.UnitUtils;

public class SalesTaxesMainTest extends AbstractTest {

	@Test
	public void test() throws Exception {
		SalesTaxesMain main = new SalesTaxesMain() {
			@Override
			protected InputStream readStream(String path) throws FileNotFoundException {
				InputStream stream = UnitUtils.localStream(path.startsWith("/") ? path : "/" + path);
				if (stream == null) {
					throw new FileNotFoundException(path);
				}
				return stream;
			}
		};

		assertThrown(() -> main.run("1", "2")).isInstanceOf(SalesTaxesException.class);
		assertThrown(() -> main.run()).isInstanceOf(SalesTaxesException.class);
		assertThrown(() -> main.run("1")).isInstanceOf(FileNotFoundException.class);

		List<String> receiptFiles = UnitUtils.getResourceFiles("/tests").parallelStream() //
				.filter(name -> name.endsWith("_input.txt")) //
				.map(name -> name.replace("_input.txt", "")) //
				.collect(Collectors.toList());

		PrintStream originalPrintStream = System.out;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (PrintStream ps = new PrintStream(baos)) {
			System.setOut(ps);

			for (String receiptFile : receiptFiles) {
				String receiptFileInputPath = "/tests/" + receiptFile + "_input.txt";
				String receiptFileOutputPath = "/tests/" + receiptFile + "_output.txt";
				testMain(main, receiptFileInputPath, receiptFileOutputPath, baos);
				baos.reset();
			}
		} finally {
			System.setOut(originalPrintStream);
		}
	}

	private void testMain(SalesTaxesMain main, String inputFile, String outputPath, ByteArrayOutputStream baos) throws Exception {
		try (InputStream outputFileStream = UnitUtils.localStream(outputPath)) {
			main.run(inputFile);
			UnitUtils.assertEquals(StreamUtils.streamToString(outputFileStream), new String(baos.toByteArray()));
		}
	}

}
