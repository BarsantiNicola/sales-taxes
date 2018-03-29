package cloud.nimvps.exercises.salestaxes.tests.daos.parsers;

import static cloud.nimvps.exercises.salestaxes.utils.asserts.thrown.AssertJThrowableAssert.assertThrown;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cloud.nimvps.exercises.salestaxes.core.dao.model.SourceType;
import cloud.nimvps.exercises.salestaxes.core.dao.parsers.ParserException;
import cloud.nimvps.exercises.salestaxes.core.dao.parsers.impl.SourceTypeParser;
import cloud.nimvps.exercises.salestaxes.tests.AbstractTest;

public class SourceTypeParserTest extends AbstractTest {

	@Test
	public void test() throws ParserException {
		SourceTypeParser parser = new SourceTypeParser();
		assertEquals(new SourceType("book", false), parser.parse("book;false"));
		assertEquals(new SourceType("media", true), parser.parse("media;true"));
		assertEquals(new SourceType("media", true), parser.parse("media; true"));
		assertEquals(new SourceType("other", true), parser.parse("other ;true "));
		assertThrown(() -> parser.parse("media")).isInstanceOf(ParserException.class);
		assertThrown(() -> parser.parse("media;")).isInstanceOf(ParserException.class);
		assertThrown(() -> parser.parse("media;a")).isInstanceOf(ParserException.class);
		assertThrown(() -> parser.parse("other;true;boh")).isInstanceOf(ParserException.class);
		assertThrown(() -> parser.parse("1 book at 12,49")).isInstanceOf(ParserException.class);
	}

}
