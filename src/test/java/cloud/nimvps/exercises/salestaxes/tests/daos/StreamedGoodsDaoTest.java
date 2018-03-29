package cloud.nimvps.exercises.salestaxes.tests.daos;

import static cloud.nimvps.exercises.salestaxes.utils.asserts.thrown.AssertJThrowableAssert.assertThrown;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.junit.Test;

import cloud.nimvps.exercises.salestaxes.core.dao.DaoException;
import cloud.nimvps.exercises.salestaxes.core.dao.impl.StreamedGoodsDao;
import cloud.nimvps.exercises.salestaxes.core.dao.model.SourceGood;
import cloud.nimvps.exercises.salestaxes.tests.AbstractTest;

public class StreamedGoodsDaoTest extends AbstractTest {

	@Test
	public void defaultTest() throws Exception {
		String typesStr = "book;false\r\nmedia;true";
		String goodsStr = "1;book;book\r\n2;music CD;media";
		String[] goodsNames = new String[] { "book", "music CD" };
		try (ByteArrayInputStream streamTypes = new ByteArrayInputStream(typesStr.getBytes()); //
				ByteArrayInputStream streamGoods = new ByteArrayInputStream(goodsStr.getBytes()); //
				StreamedGoodsDao dao = new StreamedGoodsDao(streamTypes, streamGoods)) {
			List<String> daoGoodsNames = dao.listGoods();
			assertThat("Different goods names", daoGoodsNames, containsInAnyOrder(goodsNames));

			assertEquals(dao.getGood("book"), new SourceGood(1, "book", "book", false));
			assertEquals(dao.getGood("music CD"), new SourceGood(1, "music CD", "media", true));

			assertThrown(() -> dao.getGood("box of chocolates")).isInstanceOf(DaoException.class);
		}
	}

}
