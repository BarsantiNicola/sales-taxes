package cloud.nimvps.exercises.salestaxes.core.dao;

/**
 * Dao for accessing taxes values
 */
public interface ITaxesDao extends AutoCloseable {

	/**
	 * Returns the sales taxes
	 * 
	 * @return The sales taxes
	 * @throws DaoException
	 */
	float getSalesTaxes() throws DaoException;

	/**
	 * Retruns the import taxes
	 * 
	 * @return The import taxes
	 * @throws DaoException
	 */
	float getImportTaxes() throws DaoException;

}
