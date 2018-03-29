package cloud.nimvps.exercises.salestaxes.tests.daos.parsers;

import org.junit.Test;

import cloud.nimvps.exercises.salestaxes.core.dao.model.SourceGood;
import cloud.nimvps.exercises.salestaxes.core.dao.parsers.ParserException;
import cloud.nimvps.exercises.salestaxes.core.dao.parsers.impl.SourceGoodParser;
import cloud.nimvps.exercises.salestaxes.tests.AbstractTest;

import static cloud.nimvps.exercises.salestaxes.utils.asserts.thrown.AssertJThrowableAssert.assertThrown;
import static org.junit.Assert.assertEquals;

public class SourceGoodParserTest extends AbstractTest {

	@Test
	public void test() throws ParserException {
		SourceGoodParser parser = new SourceGoodParser();

		assertEquals(new SourceGood(1, "book", "book", false), parser.parse("1;book;book"));
		assertEquals(new SourceGood(1, "music CD", "media", false), parser.parse("2;music CD;media"));
		assertThrown(() -> parser.parse("1;book")).isInstanceOf(ParserException.class);
		assertThrown(() -> parser.parse("1;music CD;media;maybe")).isInstanceOf(ParserException.class);
		assertThrown(() -> parser.parse("1,music CD;media;maybe")).isInstanceOf(ParserException.class);
	}

}
