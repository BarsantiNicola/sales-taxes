package cloud.nimvps.exercises.salestaxes.core.logics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import cloud.nimvps.exercises.salestaxes.core.dao.DaoException;
import cloud.nimvps.exercises.salestaxes.core.dao.IGoodsDao;
import cloud.nimvps.exercises.salestaxes.core.dao.IReceiptDao;
import cloud.nimvps.exercises.salestaxes.core.dao.ITaxesDao;
import cloud.nimvps.exercises.salestaxes.core.dao.model.SourceGood;
import cloud.nimvps.exercises.salestaxes.core.dao.model.SourceItem;
import cloud.nimvps.exercises.salestaxes.core.dao.model.SourceReceipt;
import cloud.nimvps.exercises.salestaxes.core.model.Item;
import cloud.nimvps.exercises.salestaxes.core.model.Receipt;

/**
 * Processor for the receipt. Reads the receipts from the recipt dao and processes them
 */
public class ReceiptProcessor {

	private static Logger log = Logger.getLogger(ReceiptProcessor.class.getName());

	private final Map<String, SourceGood> goodsCache = new HashMap<>();

	private final ITaxesDao taxesDao;
	private final IGoodsDao goodsDao;

	private Float salesTaxes;
	private Float importTaxes;

	/**
	 * Class constructor specifying the taxes dao and the goods dao
	 * 
	 * @param taxesDao
	 * @param goodsDao
	 */
	public ReceiptProcessor(ITaxesDao taxesDao, IGoodsDao goodsDao) {
		this.taxesDao = taxesDao;
		this.goodsDao = goodsDao;
	}

	/**
	 * Processes the receipts and returns the processed results
	 * 
	 * @param receiptDao
	 *            The dao containing the receipts to process
	 * @return The processed receipts
	 * @throws ProcessorException
	 */
	public List<Receipt> processReceipts(IReceiptDao receiptDao) throws ProcessorException {
		log.fine("Precessing receipts");
		try {
			List<Receipt> result = new ArrayList<>();
			List<String> ids = receiptDao.getReceiptsIds();
			for (String id : ids) {
				log.fine("Precessing receipt \"" + id + "\"");
				SourceReceipt receipt = receiptDao.getReceipt(id);
				Receipt processedReceipt = processReceipt(receipt);
				if (log.isLoggable(Level.FINER)) {
					log.finer("Processed receipt " + receipt);
				}
				result.add(processedReceipt);
			}
			return result;
		} catch (ProcessorException e) {
			throw e;
		} catch (Exception e) {
			throw new ProcessorException("Error processing receipts: " + e.getMessage(), e);
		}
	}

	/*
	 * Processes a receipt
	 * 
	 * @param receiptInput The source receipt
	 * 
	 * @return The processed receipt
	 * 
	 * @throws ProcessorException
	 */
	private Receipt processReceipt(SourceReceipt receiptInput) throws ProcessorException {
		Receipt receiptOutput = new Receipt(receiptInput.getId());
		for (SourceItem itemInput : receiptInput.getItems()) {
			try {
				receiptOutput.getItems().add(processItem(itemInput));
			} catch (DaoException e) {
				throw new ProcessorException("Error processing receipt: " + e.getMessage(), e);
			}
		}
		return receiptOutput;
	}

	/*
	 * Processes a single item row in the receipt
	 * 
	 * @param itemInput The source item
	 * 
	 * @return The processed item
	 * 
	 * @throws DaoException
	 */
	private Item processItem(SourceItem itemInput) throws DaoException {
		log.fine("Precessing item \"" + itemInput.getName() + "\"");
		Item item = new Item();

		// Calculating single item costs
		item.setAmount(itemInput.getAmount());
		item.setImported(itemInput.isImported());
		item.setName(itemInput.getName());
		item.setPrice(itemInput.getPrice());
		item.setSalesTaxesAmount(calculateSalesTaxesAmount(item));
		item.setImportTaxesAmount(calculateImportTaxesAmount(item));
		item.setTotalPrice(item.getPrice() + item.getSalesTaxesAmount() + item.getImportTaxesAmount());

		// Multiplying for the number of items
		item.setPrice(item.getPrice() * item.getAmount());
		item.setSalesTaxesAmount(item.getSalesTaxesAmount() * item.getAmount());
		item.setImportTaxesAmount(item.getImportTaxesAmount() * item.getAmount());
		item.setTotalPrice(item.getTotalPrice() * item.getAmount());

		log.fine("Price: " + item.getPrice());
		log.fine("Sales taxes: " + item.getSalesTaxesAmount());
		log.fine("Import taxes: " + item.getImportTaxesAmount());
		log.fine("Total price: " + item.getTotalPrice());

		return item;
	}

	/*
	 * Calculates sales taxes on target item
	 * 
	 * @param item The item of which I want to know the sales taxes
	 * 
	 * @return The sales taxes amount
	 * 
	 * @throws DaoException
	 */
	private float calculateSalesTaxesAmount(Item item) throws DaoException {
		SourceGood good = getGood(item.getName());
		return good.isTaxed() ? calculateTaxesAmount(item.getPrice(), getSalesTaxes()) : 0f;
	}

	/*
	 * Calculates import taxes on target item
	 * 
	 * @param item The item of which I want to know the sales taxes
	 * 
	 * @return The import taxes amount
	 * 
	 * @throws DaoException
	 */
	private float calculateImportTaxesAmount(Item item) throws DaoException {
		return item.isImported() ? calculateTaxesAmount(item.getPrice(), getImportTaxes()) : 0f;
	}

	/*
	 * Calculates the taxes amount using a 0.05 precision (rounded up)
	 * 
	 * @param price The original price
	 * 
	 * @param taxes The taxes
	 * 
	 * @return
	 */
	private float calculateTaxesAmount(float price, float taxes) {
		float taxedAmount = price * taxes / 100;
		return (float) (Math.ceil(taxedAmount * 20.0) / 20.0);
	}

	/*
	 * Returns the sales taxes
	 * 
	 * @return The sales taxes
	 * 
	 * @throws DaoException
	 */
	protected float getSalesTaxes() throws DaoException {
		if (salesTaxes == null) {
			salesTaxes = taxesDao.getSalesTaxes();
		}
		return salesTaxes;
	}

	/*
	 * Returns the import taxes
	 * 
	 * @return The import taxes
	 * 
	 * @throws DaoException
	 */
	protected float getImportTaxes() throws DaoException {
		if (importTaxes == null) {
			importTaxes = taxesDao.getImportTaxes();
		}
		return importTaxes;
	}

	/*
	 * Returns the good details
	 * 
	 * @param name The good name
	 * 
	 * @return The good details
	 * 
	 * @throws DaoException
	 */
	protected SourceGood getGood(String name) throws DaoException {
		SourceGood good = goodsCache.get(name);
		if (good == null) {
			good = goodsDao.getGood(name);
			goodsCache.put(name, good);
		}
		return good;
	}

}
