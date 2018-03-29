package cloud.nimvps.exercises.salestaxes.core.dao;

import java.util.List;

import cloud.nimvps.exercises.salestaxes.core.dao.model.SourceGood;

/**
 * Dao for accessing goods details
 */
public interface IGoodsDao extends AutoCloseable {

	/**
	 * Returns the good details
	 * 
	 * @param name
	 *            The name of the good
	 * @return The good detail
	 * @throws DaoException
	 *             If no good was found or an underlying error occurred
	 */
	SourceGood getGood(String name) throws DaoException;

	/**
	 * Returns the list of goods names
	 * 
	 * @return The list of goods names
	 * @throws DaoException
	 */
	List<String> listGoods() throws DaoException;

}
