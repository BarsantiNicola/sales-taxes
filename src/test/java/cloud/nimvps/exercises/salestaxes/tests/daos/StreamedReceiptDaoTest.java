package cloud.nimvps.exercises.salestaxes.tests.daos;

import static cloud.nimvps.exercises.salestaxes.utils.asserts.thrown.AssertJThrowableAssert.assertThrown;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import cloud.nimvps.exercises.salestaxes.core.dao.DaoException;
import cloud.nimvps.exercises.salestaxes.core.dao.impl.StreamedReceiptDao;
import cloud.nimvps.exercises.salestaxes.core.dao.model.SourceItem;
import cloud.nimvps.exercises.salestaxes.core.dao.model.SourceReceipt;
import cloud.nimvps.exercises.salestaxes.tests.AbstractTest;

public class StreamedReceiptDaoTest extends AbstractTest {

	@Test
	public void defaultTest() throws Exception {
		String receiptStr = "1 book at 12.49\r\n1 music CD at 14.99\r\n1 imported bottle of perfume at 47.50";
		try (ByteArrayInputStream bis = new ByteArrayInputStream(receiptStr.getBytes()); //
				StreamedReceiptDao dao = new StreamedReceiptDao(bis)) {
			SourceReceipt receipt = dao.getReceipt(dao.getReceiptsIds().get(0));
			assertEquals(receipt.getItems().size(), 3);
			assertEquals(new SourceItem(1, "book", 12.49f, false), receipt.getItems().remove(0));
			assertEquals(new SourceItem(1, "music CD", 14.99f, false), receipt.getItems().remove(0));
			assertEquals(new SourceItem(1, "bottle of perfume", 47.50f, true), receipt.getItems().remove(0));
		}
	}

	@Test
	public void errorTest() throws Exception {
		String receipt = "1 book at 12.49\r\n1 music CD at 14.99\r\n1 imported bottle of perfume at 47.50\r\npippo";
		try (ByteArrayInputStream bis = new ByteArrayInputStream(receipt.getBytes()); //
				StreamedReceiptDao dao = new StreamedReceiptDao(bis)) {
			assertThrown(() -> dao.getReceipt(dao.getReceiptsIds().get(0))).isInstanceOf(DaoException.class);
			assertThrown(() -> dao.getReceipt("error")).isInstanceOf(DaoException.class);
		}
	}

}
