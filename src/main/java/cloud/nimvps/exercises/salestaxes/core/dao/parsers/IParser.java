package cloud.nimvps.exercises.salestaxes.core.dao.parsers;

/**
 * Parser interface. Used for parsers that parse strings to items of type T
 * 
 * @param <T>
 */
public interface IParser<T> {

	T parse(String str) throws ParserException;

}
