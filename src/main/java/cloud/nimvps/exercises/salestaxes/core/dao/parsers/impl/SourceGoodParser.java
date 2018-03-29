package cloud.nimvps.exercises.salestaxes.core.dao.parsers.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import cloud.nimvps.exercises.salestaxes.core.dao.model.SourceGood;
import cloud.nimvps.exercises.salestaxes.core.dao.parsers.IParser;
import cloud.nimvps.exercises.salestaxes.core.dao.parsers.ParserException;

/**
 * Parser for goods that reads a semicolon-separated line and translates it into a good
 */
public class SourceGoodParser implements IParser<SourceGood> {

	private static Logger log = Logger.getLogger(SourceGoodParser.class.getName());

	@Override
	public SourceGood parse(String str) throws ParserException {
		log.fine("Parsing good \"" + str + "\"");
		String[] toks = str.split(";");
		if (toks.length != 3) {
			throw new ParserException("String  \"" + str + "\" cannot be parsed to a good");
		}

		try {
			SourceGood good = new SourceGood();
			good.setId(Integer.parseInt(toks[0].trim()));
			good.setName(toks[1].trim());
			good.setType(toks[2].trim());

			if (log.isLoggable(Level.FINER)) {
				log.finer("Parsed good " + good);
			}

			return good;
		} catch (Exception e) {
			throw new ParserException("Error while parsing good \"" + str + "\": " + e.getMessage(), e);
		}
	}

}
