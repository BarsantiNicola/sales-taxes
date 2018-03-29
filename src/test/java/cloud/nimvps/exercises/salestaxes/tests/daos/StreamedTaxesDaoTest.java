package cloud.nimvps.exercises.salestaxes.tests.daos;

import static cloud.nimvps.exercises.salestaxes.utils.asserts.thrown.AssertJThrowableAssert.assertThrown;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Properties;

import org.junit.Test;

import cloud.nimvps.exercises.salestaxes.core.dao.DaoException;
import cloud.nimvps.exercises.salestaxes.core.dao.ITaxesDao;
import cloud.nimvps.exercises.salestaxes.core.dao.impl.StreamedTaxesDao;
import cloud.nimvps.exercises.salestaxes.tests.AbstractTest;

public class StreamedTaxesDaoTest extends AbstractTest {

	@Test
	public void propertiesTest() throws Exception {
		Properties properties = new Properties();
		properties.setProperty(StreamedTaxesDao.SALES_TAXES_KEY, "10");
		properties.setProperty(StreamedTaxesDao.IMPORT_TAXES_KEY, "5");
		try (ITaxesDao taxesDao = new StreamedTaxesDao(properties)) {
			assertEquals(taxesDao.getSalesTaxes(), 10f, 0f);
			assertEquals(taxesDao.getImportTaxes(), 5f, 0f);
			properties.setProperty(StreamedTaxesDao.IMPORT_TAXES_KEY, "e");
			assertThrown(() -> taxesDao.getImportTaxes()).isInstanceOf(DaoException.class);
			properties.remove(StreamedTaxesDao.IMPORT_TAXES_KEY);
			assertThrown(() -> taxesDao.getImportTaxes()).isInstanceOf(DaoException.class);
		}
	}

	@Test
	public void streamTest() throws Exception {
		Properties properties = new Properties();
		properties.setProperty(StreamedTaxesDao.SALES_TAXES_KEY, "10");
		properties.setProperty(StreamedTaxesDao.IMPORT_TAXES_KEY, "5");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		properties.store(baos, null);
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		try (ITaxesDao taxesDao = new StreamedTaxesDao(bais)) {
			assertEquals(taxesDao.getSalesTaxes(), 10f, 0f);
			assertEquals(taxesDao.getImportTaxes(), 5f, 0f);
		}
	}

}
