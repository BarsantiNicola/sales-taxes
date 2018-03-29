package cloud.nimvps.exercises.salestaxes.tests.logics;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import cloud.nimvps.exercises.salestaxes.core.dao.IGoodsDao;
import cloud.nimvps.exercises.salestaxes.core.dao.ITaxesDao;
import cloud.nimvps.exercises.salestaxes.core.dao.impl.StreamedGoodsDao;
import cloud.nimvps.exercises.salestaxes.core.dao.impl.StreamedReceiptDao;
import cloud.nimvps.exercises.salestaxes.core.dao.impl.StreamedTaxesDao;
import cloud.nimvps.exercises.salestaxes.core.logics.ProcessorException;
import cloud.nimvps.exercises.salestaxes.core.logics.ReceiptProcessor;
import cloud.nimvps.exercises.salestaxes.core.model.Receipt;
import cloud.nimvps.exercises.salestaxes.core.utils.StreamUtils;
import cloud.nimvps.exercises.salestaxes.tests.AbstractTest;
import cloud.nimvps.exercises.salestaxes.utils.UnitUtils;
import cloud.nimvps.exercises.salestaxes.view.AbstractReceiptPrinter;
import cloud.nimvps.exercises.salestaxes.view.SBReceiptPrinter;

public class ReceiptProcessorTest extends AbstractTest {

	@Test
	public void test() throws Exception {
		try (ITaxesDao taxesDao = new StreamedTaxesDao(UnitUtils.localStream("/data/taxes.properties")); //
				IGoodsDao goodsDao = new StreamedGoodsDao(UnitUtils.localStream("/data/good_types.txt"), UnitUtils.localStream("/data/goods.txt"))) {

			ReceiptProcessor processor = new ReceiptProcessor(taxesDao, goodsDao);

			List<String> receiptFiles = UnitUtils.getResourceFiles("/tests").parallelStream() //
					.filter(name -> name.endsWith("_input.txt")) //
					.map(name -> name.replace("_input.txt", "")) //
					.collect(Collectors.toList());

			for (String receiptFile : receiptFiles) {
				String receiptFileInputPath = "/tests/" + receiptFile + "_input.txt";
				String receiptFileOutputPath = "/tests/" + receiptFile + "_output.txt";
				testProcessor(processor, receiptFileInputPath, receiptFileOutputPath);
			}
		}
	}

	private void testProcessor(ReceiptProcessor processor, String receiptFileInputPath, String receiptFileOutputPath) throws ProcessorException, IOException, Exception {
		try (StreamedReceiptDao receiptDao = new StreamedReceiptDao(UnitUtils.localStream(receiptFileInputPath)); //
				InputStream outputFileStream = UnitUtils.localStream(receiptFileOutputPath)) {

			StringBuilder sb = new StringBuilder();
			AbstractReceiptPrinter printer = new SBReceiptPrinter(sb);

			List<Receipt> receipts = processor.processReceipts(receiptDao);
			Receipt receipt = receipts.get(0);
			printer.printReceipt(receipt);

			UnitUtils.assertEquals(StreamUtils.streamToString(outputFileStream), sb.toString());
		}
	}

}
