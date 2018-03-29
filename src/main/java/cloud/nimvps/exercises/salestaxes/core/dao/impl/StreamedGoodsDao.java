package cloud.nimvps.exercises.salestaxes.core.dao.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import cloud.nimvps.exercises.salestaxes.core.dao.DaoException;
import cloud.nimvps.exercises.salestaxes.core.dao.IGoodsDao;
import cloud.nimvps.exercises.salestaxes.core.dao.model.SourceGood;
import cloud.nimvps.exercises.salestaxes.core.dao.model.SourceType;
import cloud.nimvps.exercises.salestaxes.core.dao.parsers.IParser;
import cloud.nimvps.exercises.salestaxes.core.dao.parsers.ParserException;
import cloud.nimvps.exercises.salestaxes.core.dao.parsers.impl.SourceGoodParser;
import cloud.nimvps.exercises.salestaxes.core.dao.parsers.impl.SourceTypeParser;

/**
 * Goods dao based on InputStream for goods and good types
 */
public class StreamedGoodsDao implements IGoodsDao {

	private static Logger log = Logger.getLogger(StreamedGoodsDao.class.getName());

	// Readers
	private final BufferedReader readerTypes;
	private final BufferedReader readerGoods;

	// Goiods cache
	private Map<String, SourceGood> goods;

	/**
	 * Class constructor specifying the stream for the types and the stream for the goods
	 */
	public StreamedGoodsDao(InputStream streamTypes, InputStream streamGoods) {
		readerTypes = new BufferedReader(new InputStreamReader(streamTypes));
		readerGoods = new BufferedReader(new InputStreamReader(streamGoods));
	}

	@Override
	public List<String> listGoods() throws DaoException {
		try {
			return getGoods().keySet().stream().collect(Collectors.toList());
		} catch (IOException | ParserException e) {
			throw new DaoException("Error getting goods list: " + e.getMessage(), e);
		}
	}

	@Override
	public SourceGood getGood(String name) throws DaoException {
		try {
			SourceGood good = getGoods().get(name);
			if (good == null) {
				throw new DaoException("No good found with name \"" + name + "\"");
			}
			return good;
		} catch (IOException | ParserException e) {
			throw new DaoException("Error getting good details: " + e.getMessage(), e);
		}
	}

	/*
	 * Returns a map of goods having their name as the key. The goods are loaded from local cache if possible. <br/> If not, then they will be loaded from streams and then cached.
	 * 
	 * @return The map of goods having they name as key
	 * 
	 * @throws IOException
	 * 
	 * @throws ParserException
	 * 
	 * @throws DaoException
	 */
	private Map<String, SourceGood> getGoods() throws IOException, ParserException, DaoException {
		if (goods == null) {
			goods = parseGoodsWithTypes().stream().collect(Collectors.toMap(SourceGood::getName, Function.identity()));
		}
		return goods;
	}

	/*
	 * Returns the list of goods filled with their type details
	 * 
	 * @return The list of goods
	 * 
	 * @throws IOException
	 * 
	 * @throws ParserException
	 * 
	 * @throws DaoException
	 */
	private List<SourceGood> parseGoodsWithTypes() throws IOException, ParserException, DaoException {
		log.fine("Reading types");
		List<SourceType> types = getItems(readerTypes, new SourceTypeParser());
		Map<String, SourceType> typesMap = types.stream().collect(Collectors.toMap(SourceType::getName, Function.identity()));
		log.fine("Reding goods");
		List<SourceGood> goods = getItems(readerGoods, new SourceGoodParser());
		log.fine("Parsing goods with types");
		for (SourceGood good : goods) {
			SourceType type = typesMap.get(good.getType());
			if (type == null) {
				throw new DaoException("Type \"" + good.getType() + "\" not found for good " + good.getId());
			}
			log.fine(good.getName() + " -> taxed: " + type.isTaxed());
			good.setTaxed(type.isTaxed());
		}
		log.fine("Parsed goods with types");
		return goods;
	}

	/*
	 * Reads a list of items from target reader and parses them using the parser passed as input
	 * 
	 * @param reader The reader to read items
	 * 
	 * @param parser The parser to parse items
	 * 
	 * @return The items list
	 * 
	 * @throws IOException
	 * 
	 * @throws ParserException
	 */
	private <T> List<T> getItems(BufferedReader reader, IParser<T> parser) throws IOException, ParserException {
		List<T> items = new ArrayList<>();
		String line;
		while ((line = reader.readLine()) != null) {
			items.add(parser.parse(line));
		}
		return items;
	}

	/**
	 * Closes the readers and cleans the cache
	 */
	@Override
	public void close() throws DaoException, IOException {
		readerTypes.close();
		readerGoods.close();
		if (goods != null) {
			goods.clear();
		}
	}

}
