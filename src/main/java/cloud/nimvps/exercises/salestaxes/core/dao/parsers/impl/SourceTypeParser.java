package cloud.nimvps.exercises.salestaxes.core.dao.parsers.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import cloud.nimvps.exercises.salestaxes.core.dao.model.SourceType;
import cloud.nimvps.exercises.salestaxes.core.dao.parsers.IParser;
import cloud.nimvps.exercises.salestaxes.core.dao.parsers.ParserException;
import cloud.nimvps.exercises.salestaxes.core.utils.TypesUtils;

/**
 * Parser for types that reads a semicolon-separated line and translates it into a type
 */
public class SourceTypeParser implements IParser<SourceType> {

	private static Logger log = Logger.getLogger(SourceTypeParser.class.getName());

	@Override
	public SourceType parse(String str) throws ParserException {
		log.fine("Parsing type \"" + str + "\"");
		String[] toks = str.split(";");
		if (toks.length != 2) {
			throw new ParserException("String  \"" + str + "\" cannot be parsed to a type");
		}

		try {
			SourceType type = new SourceType();
			type.setName(toks[0].trim());
			String boolStr = toks[1].trim();
			type.setTaxed(TypesUtils.parseBoolean(boolStr));

			if (log.isLoggable(Level.FINER)) {
				log.finer("Parsed type " + type);
			}

			return type;
		} catch (Exception e) {
			throw new ParserException("Error while parsing good \"" + str + "\": " + e.getMessage(), e);
		}
	}

}
