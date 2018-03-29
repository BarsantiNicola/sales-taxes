package cloud.nimvps.exercises.salestaxes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import cloud.nimvps.exercises.salestaxes.core.SalesTaxesException;
import cloud.nimvps.exercises.salestaxes.core.dao.IGoodsDao;
import cloud.nimvps.exercises.salestaxes.core.dao.ITaxesDao;
import cloud.nimvps.exercises.salestaxes.core.dao.impl.StreamedGoodsDao;
import cloud.nimvps.exercises.salestaxes.core.dao.impl.StreamedReceiptDao;
import cloud.nimvps.exercises.salestaxes.core.dao.impl.StreamedTaxesDao;
import cloud.nimvps.exercises.salestaxes.core.logics.ReceiptProcessor;
import cloud.nimvps.exercises.salestaxes.core.model.Receipt;
import cloud.nimvps.exercises.salestaxes.core.utils.StreamUtils;
import cloud.nimvps.exercises.salestaxes.view.AbstractReceiptPrinter;
import cloud.nimvps.exercises.salestaxes.view.SysoReceiptPrinter;

public class SalesTaxesMain {

	static {
		final String logginfConfigFileKey = "java.util.logging.config.file";
		String fName = System.getProperty(logginfConfigFileKey);
		if (fName == null) {
			System.setProperty(logginfConfigFileKey, "logging.properties");
		}
	}

	private static Logger log = Logger.getLogger(SalesTaxesMain.class.getName());

	public static void main(String[] args) throws Exception {
		args = new String[] { "tests/receipt1_input.txt" };
		new SalesTaxesMain().run(args);
	}

	public void run(String... args) throws Exception {
		if (args.length == 0) {
			throw new SalesTaxesException("No receipt input file passed as argument");
		}

		if (args.length > 1) {
			throw new SalesTaxesException("Too many receipt input files passed as argument");
		}

		String receiptFile = args[0];

		if (log.isLoggable(Level.INFO)) {
			log.info("Executing with input file \"" + receiptFile + "\"");
		}

		ITaxesDao taxesDao = new StreamedTaxesDao(readStream("data/taxes.properties"));
		IGoodsDao goodsDao = new StreamedGoodsDao(readStream("data/good_types.txt"), readStream("data/goods.txt"));

		ReceiptProcessor processor = new ReceiptProcessor(taxesDao, goodsDao);

		AbstractReceiptPrinter printer = new SysoReceiptPrinter();

		if (log.isLoggable(Level.FINE)) {
			try (InputStream receiptStream = readStream(receiptFile)) {
				log.fine("Receipt content:" + System.lineSeparator() + StreamUtils.streamToString(receiptStream));
			}
		}

		try (StreamedReceiptDao receiptDao = new StreamedReceiptDao(readStream(receiptFile))) {
			List<Receipt> receipts = processor.processReceipts(receiptDao);
			Receipt receipt = receipts.get(0);
			receipt.setId(receiptFile);
			printer.printReceipt(receipt);
		}

	}

	protected InputStream readStream(String path) throws FileNotFoundException {
		return new FileInputStream(path);
	}
}
