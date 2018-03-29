package cloud.nimvps.exercises.salestaxes.core.dao;

import java.util.List;

import cloud.nimvps.exercises.salestaxes.core.dao.model.SourceReceipt;

/**
 * Dao for accessing receipts
 */
public interface IReceiptDao extends AutoCloseable {

	/**
	 * Returns the list of receipts ids to retrieve
	 * 
	 * @return The list of receipts ids to retrieve
	 * @throws DaoException
	 */
	List<String> getReceiptsIds() throws DaoException;

	/**
	 * Returns the receipt details
	 * 
	 * @param receiptId
	 *            The id of the receipt
	 * @return The details of the receipt
	 * @throws DaoException
	 */
	SourceReceipt getReceipt(String receiptId) throws DaoException;

}
