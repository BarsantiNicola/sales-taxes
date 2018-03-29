package cloud.nimvps.exercises.salestaxes.core.dao.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import cloud.nimvps.exercises.salestaxes.core.dao.DaoException;
import cloud.nimvps.exercises.salestaxes.core.dao.IReceiptDao;
import cloud.nimvps.exercises.salestaxes.core.dao.model.SourceReceipt;
import cloud.nimvps.exercises.salestaxes.core.dao.parsers.impl.SourceItemParser;

/**
 * Receipt dao based on input stream. Can read only a receipt at a time
 */
public class StreamedReceiptDao implements IReceiptDao {

	// Receipt reader
	private final BufferedReader reader;

	// Receipt items parser
	private final SourceItemParser parser;

	// Receipt id
	private final String receiptId;

	/**
	 * Class constructor specifying the stream for the receipt. Receipt id will be "default".
	 */
	public StreamedReceiptDao(InputStream stream) {
		this("default", stream);
	}

	/**
	 * Class constructor specifying the id of the receipt and the stream for the receipt.
	 */
	public StreamedReceiptDao(String receiptId, InputStream stream) {
		this(receiptId, stream, new SourceItemParser());
	}

	/**
	 * Class constructor specifying the id of the receipt, the stream for the receipt and the parser to use to parse receipt items.
	 */
	public StreamedReceiptDao(String receiptId, InputStream stream, SourceItemParser parser) {
		this.receiptId = receiptId;
		reader = new BufferedReader(new InputStreamReader(stream));
		this.parser = parser;
	}

	/**
	 * Returns a list containing the receipt id specified in constructor
	 */
	@Override
	public List<String> getReceiptsIds() throws DaoException {
		return Collections.singletonList(receiptId);
	}

	/**
	 * Returns the receipt having the given id.
	 */
	@Override
	public SourceReceipt getReceipt(String receiptId) throws DaoException {
		if (!this.receiptId.equals(receiptId)) {
			throw new DaoException("No receipt found for the given id: \"" + receiptId + "\"");
		}
		SourceReceipt result = new SourceReceipt();
		result.setId(receiptId);
		try {
			String itemLine;
			while ((itemLine = reader.readLine()) != null) {
				result.getItems().add(parser.parse(itemLine));
			}
		} catch (Exception e) {
			throw new DaoException("Exception reading from input stream: " + e.getMessage(), e);
		}
		return result;
	}

	/**
	 * Closes the reader
	 */
	public void close() throws Exception {
		reader.close();
	}

}
