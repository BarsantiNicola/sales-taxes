package cloud.nimvps.exercises.salestaxes.core.dao.parsers.impl;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cloud.nimvps.exercises.salestaxes.core.dao.model.SourceItem;
import cloud.nimvps.exercises.salestaxes.core.dao.parsers.IParser;
import cloud.nimvps.exercises.salestaxes.core.dao.parsers.ParserException;

/**
 * Parser for items that uses a regular expression to parse the item. The importedKeywork is used to check against imported items.<br/>
 */
public class SourceItemParser implements IParser<SourceItem> {

	private static Logger log = Logger.getLogger(SourceItemParser.class.getName());

	private static final String DEFAULT_ITEM_REGEX = "^[ ]*([0-9]+)[ ]+(.+)[ ]+at[ ]+(\\d+(?:\\.\\d{1,2})?)[ ]*$";
	private static final String DEFAULT_IMPORTED_KEYWORK = "imported";

	private final Pattern itemPattern;
	private final String importedKeyword;

	private PriceParser priceParser = new PriceParser();

	public SourceItemParser() {
		this(null, null);
	}

	public SourceItemParser(String itemRegex, String importedKeyword) {
		itemPattern = Pattern.compile(itemRegex == null ? DEFAULT_ITEM_REGEX : itemRegex);
		this.importedKeyword = importedKeyword == null ? DEFAULT_IMPORTED_KEYWORK : importedKeyword;
	}

	@Override
	public SourceItem parse(String itemStr) throws ParserException {
		SourceItem res;
		try {
			res = parse(itemStr, itemPattern);
		} catch (ParseException e) {
			throw new ParserException("Error parsing item string \"" + itemStr + "\": " + e.getMessage(), e);
		}

		if (res == null) {
			throw new ParserException("Could not parse string \"" + itemStr + "\"");
		}

		updateImportedItem(res);

		res.setName(res.getName().trim());

		return res;
	}

	private void updateImportedItem(SourceItem item) {
		if (item.getName().startsWith(importedKeyword + " ")) { // Checking against item name "imported xxxx"
			item.setName(item.getName().replaceFirst(importedKeyword + " ", ""));
			item.setImported(true);
			return;
		}

		if (item.getName().endsWith(" " + importedKeyword)) { // Checking against item name "xxx imported"
			// Reversing name and keyphare to replace only the matched occurrence
			StringBuilder sb = new StringBuilder();
			String reversedName = sb.append(item.getName()).reverse().toString();
			sb.setLength(0);
			String reversedKeyword = sb.append(" " + importedKeyword).reverse().toString();
			sb.setLength(0);
			reversedName = reversedName.replaceFirst(reversedKeyword, "");
			item.setName(sb.append(reversedName).reverse().toString());
			item.setImported(true);
			return;
		}

		if (item.getName().contains(" " + importedKeyword + " ")) { // Checking against "xxx imported yyy"
			item.setName(item.getName().replaceFirst(" " + importedKeyword + " ", " "));
			item.setImported(true);
			return;
		}
	}

	/*
	 * Parses an item string into an item object
	 * 
	 * @param itemStr The item string
	 * 
	 * @param pattern The pattern to apply
	 * 
	 * @return
	 */
	private SourceItem parse(String itemStr, Pattern pattern) throws ParseException {

		log.fine("Parsing item \"" + itemStr + "\" using pattern \"" + pattern + "\"");
		Matcher matcher = pattern.matcher(itemStr);
		if (!matcher.matches()) {
			return null;
		}

		SourceItem item = new SourceItem();
		item.setAmount(Integer.parseInt(matcher.group(1)));
		item.setName(matcher.group(2));
		item.setPrice(priceParser.parse(matcher.group(3)));

		if (log.isLoggable(Level.FINER)) {
			log.finer("Parsed item " + item);
		}

		return item;
	}

	public PriceParser getPriceParser() {
		return priceParser;
	}

	public void setPriceParser(PriceParser priceParser) {
		this.priceParser = priceParser;
	}

	public static class PriceParser {
		public float parse(String price) throws ParseException {
			return Float.parseFloat(price);
		}
	}

}
