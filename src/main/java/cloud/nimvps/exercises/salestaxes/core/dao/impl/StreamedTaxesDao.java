package cloud.nimvps.exercises.salestaxes.core.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import cloud.nimvps.exercises.salestaxes.core.dao.DaoException;
import cloud.nimvps.exercises.salestaxes.core.dao.ITaxesDao;

/**
 * Taxes dao based on input stream. Stream must contains a properties file like content
 */
public class StreamedTaxesDao implements ITaxesDao {

	public static final String SALES_TAXES_KEY = "taxes.sales";
	public static final String IMPORT_TAXES_KEY = "taxes.import";

	// Taxes stream
	private InputStream stream;
	// Properties to contain taxes
	private Properties properties;

	public StreamedTaxesDao(InputStream stream) {
		this.stream = stream;
	}

	public StreamedTaxesDao(Properties properties) {
		this.properties = properties;
	}

	@Override
	public float getSalesTaxes() throws DaoException {
		try {
			return Float.parseFloat(getProperties().getProperty(SALES_TAXES_KEY));
		} catch (Exception e) {
			throw new DaoException("Error retrieving sales taxes: " + e.getMessage(), e);
		}
	}

	@Override
	public float getImportTaxes() throws DaoException {
		try {
			return Float.parseFloat(getProperties().getProperty(IMPORT_TAXES_KEY));
		} catch (Exception e) {
			throw new DaoException("Error retrieving import taxes: " + e.getMessage(), e);
		}
	}

	/*
	 * Returns the cached properties object or the one from the stream. Caches the object if not already cached.
	 */
	public Properties getProperties() throws IOException {
		if (properties == null) {
			properties = new Properties();
			properties.load(stream);
		}
		return properties;
	}

	/**
	 * Closes the stream and cleans the cache
	 */
	@Override
	public void close() throws Exception {
		if (stream != null) {
			stream.close();
		}
		if (properties != null) {
			properties.clear();
		}
	}

}
