package cloud.nimvps.exercises.salestaxes.experiments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cloud.nimvps.exercises.salestaxes.core.dao.model.SourceItem;
import cloud.nimvps.exercises.salestaxes.core.dao.parsers.ParserException;
import cloud.nimvps.exercises.salestaxes.core.utils.BooleanFormatException;
import cloud.nimvps.exercises.salestaxes.core.utils.TypesUtils;

public class ConfigurableSourceItemParser {

	public enum Targets {
		Amount, Name, Price, Imported
	}

	private final List<SourceItemParserPatternConfiguration> patternsConfigurations;

	public ConfigurableSourceItemParser(List<SourceItemParserPatternConfiguration> patternsConfigurations) {
		this.patternsConfigurations = patternsConfigurations;
	}

	public SourceItem parse(String itemStr) throws ParserException, BooleanFormatException {
		for (SourceItemParserPatternConfiguration patternsConfiguration : patternsConfigurations) {
			SourceItem item = parseWithPatternConfiguration(patternsConfiguration, itemStr);
			if (item != null) {
				return item;
			}
		}

		throw new ParserException("Could not parse string \"" + itemStr + "\"");
	}

	private SourceItem parseWithPatternConfiguration(SourceItemParserPatternConfiguration patternsConfiguration, String itemStr) throws ParserException, BooleanFormatException {
		if (patternsConfiguration.getPattern() == null) {
			patternsConfiguration.setPattern(Pattern.compile(patternsConfiguration.getRegex()));
		}
		Matcher matcher = patternsConfiguration.getPattern().matcher(itemStr);
		System.out.println("\"" + itemStr + "\" over \"" + patternsConfiguration.getPattern() + "\" -> " + matcher.matches());
		if (!matcher.matches()) {
			return null;
		}
		Object[] resultGroups = resultsToArray(matcher);
		return parseItem(patternsConfiguration, resultGroups);
	}

	private Object[] resultsToArray(Matcher matcher) {
		List<String> resultGroups = new ArrayList<>();
		for (int i = 0; i < matcher.groupCount(); i++) {
			resultGroups.add(matcher.group(i + 1));
		}
		return resultGroups.toArray();
	}

	private SourceItem parseItem(SourceItemParserPatternConfiguration patternsConfiguration, Object[] objects) throws ParserException, BooleanFormatException {
		SourceItem item = new SourceItem();
		for (Entry<Targets, String> entry : patternsConfiguration.getInstructions().entrySet()) {
			parseIntruction(item, entry.getKey(), entry.getValue(), objects);
		}
		return item;
	}

	private void parseIntruction(SourceItem item, Targets target, String format, Object[] objects) throws ParserException, BooleanFormatException {
		if (target == null) {
			throw new ParserException("Invalid target for format \"" + format + "\"");
		}
		String val = String.format(format, objects);
		switch (target) {
		case Amount:
			item.setAmount(Integer.parseInt(val));
			break;
		case Name:
			item.setName(val);
			break;
		case Price:
			item.setPrice(Float.parseFloat(val));
			break;
		case Imported:
			item.setImported(TypesUtils.parseBoolean(val));
			break;
		}
	}

	public static void main(String[] args) throws Exception {
		List<SourceItemParserPatternConfiguration> confs = readConfs();

		ConfigurableSourceItemParser parser = new ConfigurableSourceItemParser(confs);

		System.out.println(parser.parse("1 box of imported chocolates at 12.49"));
	}

	private static List<SourceItemParserPatternConfiguration> readConfs() {
		List<SourceItemParserPatternConfiguration> confs = new ArrayList<>();

		SourceItemParserPatternConfiguration conf = new SourceItemParserPatternConfiguration();
		conf.setRegex("^[ ]*([0-9]+)[ ]+imported[ ]+(.+)[ ]+at[ ]+(\\d+(?:\\.\\d{1,2})?)[ ]*$");
		conf.getInstructions().put(Targets.Amount, "%1$s");
		conf.getInstructions().put(Targets.Name, "%2$s");
		conf.getInstructions().put(Targets.Price, "%3$s");
		conf.getInstructions().put(Targets.Imported, "true");
		confs.add(conf);

		conf = new SourceItemParserPatternConfiguration();
		conf.setRegex("^[ ]*([0-9]+)[ ]+(.+)[ ]+imported[ ]+(.+)[ ]+at[ ]+(\\d+(?:\\.\\d{1,2})?)[ ]*$");
		conf.getInstructions().put(Targets.Amount, "%1$s");
		conf.getInstructions().put(Targets.Name, "%2$s %3$s");
		conf.getInstructions().put(Targets.Price, "%4$s");
		conf.getInstructions().put(Targets.Imported, "true");
		confs.add(conf);

		conf = new SourceItemParserPatternConfiguration();
		conf.setRegex("^[ ]*([0-9]+)[ ]+(.+)[ ]+at[ ]+(\\d+(?:\\.\\d{1,2})?)[ ]*$");
		conf.getInstructions().put(Targets.Amount, "%1$s");
		conf.getInstructions().put(Targets.Name, "%2$s");
		conf.getInstructions().put(Targets.Price, "%3$s");
		conf.getInstructions().put(Targets.Imported, "false");
		confs.add(conf);
		return confs;
	}

	public static class SourceItemParserPatternConfiguration {
		private String regex;
		private Pattern pattern;
		private Map<Targets, String> instructions;

		public SourceItemParserPatternConfiguration() {
		}

		public String getRegex() {
			return regex;
		}

		public void setRegex(String regex) {
			this.regex = regex;
			setPattern(null);
		}

		protected Pattern getPattern() {
			return pattern;
		}

		protected void setPattern(Pattern pattern) {
			this.pattern = pattern;
		}

		public Map<Targets, String> getInstructions() {
			if (instructions == null) {
				instructions = new HashMap<>();
			}
			return instructions;
		}

		public void setInstructions(Map<Targets, String> instructions) {
			this.instructions = instructions;
		}

	}

}
