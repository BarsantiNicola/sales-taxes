package cloud.nimvps.exercises.salestaxes.tests.daos.parsers;

import static cloud.nimvps.exercises.salestaxes.utils.asserts.thrown.AssertJThrowableAssert.assertThrown;
import static org.junit.Assert.assertEquals;

import java.text.DecimalFormat;
import java.text.ParseException;

import org.junit.Test;

import cloud.nimvps.exercises.salestaxes.core.dao.model.SourceItem;
import cloud.nimvps.exercises.salestaxes.core.dao.parsers.ParserException;
import cloud.nimvps.exercises.salestaxes.core.dao.parsers.impl.SourceItemParser;
import cloud.nimvps.exercises.salestaxes.tests.AbstractTest;

public class SourceItemparserTest extends AbstractTest {

	@Test
	public void defaultTest() throws ParserException {
		SourceItemParser parser = new SourceItemParser();
		assertEquals(new SourceItem(1, "book", 12.49f, false), parser.parse("1 book at 12.49"));
		assertEquals(new SourceItem(1, "book", 12.49f, true), parser.parse("1 imported  book at 12.49"));
		assertEquals(new SourceItem(1, "dont't  know what", 12f, true), parser.parse("1 imported dont't  know what at 12"));
		assertEquals(new SourceItem(1, "dont't know what (imported)", 12.34f, false), parser.parse("1 dont't know what (imported)  at  12.34"));
		assertThrown(() -> parser.parse("1 book at 12,49")).isInstanceOf(ParserException.class);
	}

	@Test
	public void customTest() throws ParserException {
		String regex = "^[ ]*([0-9]+)[ ]+(.+)[ ]+a[ ]+(\\d+(?:\\,\\d{1,2})?)[ ]*$";
		String importedKeyword = "importato";
		SourceItemParser parser = new SourceItemParser(regex, importedKeyword);
		parser.setPriceParser(new SourceItemParser.PriceParser() {
			@Override
			public float parse(String price) throws ParseException {
				DecimalFormat df = new DecimalFormat("#0,00");
				return df.parse(price).floatValue();
			}
		});

		assertEquals(new SourceItem(1, "libro", 12.49f, false), parser.parse("1  libro a 12,49"));
		assertEquals(new SourceItem(1, "libro", 12.49f, true), parser.parse("1 libro importato a 12,49"));
		assertEquals(new SourceItem(1, "non so cosa", 12f, true), parser.parse("1 non so cosa importato a 12"));
		assertEquals(new SourceItem(1, "non so cosa (importato)", 12.34f, false), parser.parse("1 non so cosa (importato) a 12,34"));
		assertThrown(() -> parser.parse("1 book at 12,49")).isInstanceOf(ParserException.class);
	}

}
