package org.hexagonoframework.textparser;

import static org.hexagonoframework.textparser.exception.Error.of;
import static org.hexagonoframework.textparser.exception.ParserErrorType.ERR_PARSE_CLASSE_NULA;
import static org.hexagonoframework.textparser.exception.ParserErrorType.ERR_PARSE_REGISTRO_NULO;

import org.hexagonoframework.textparser.exception.ParserException;

public class RegistroParser {

	private RegistroParser() {
		super();
	}
	
	static {
		new RegistroParser();
	}
	
    public static <T> T toRegistro(String dados, Class<T> registroClass) {
    	assertClassIsNotNull(registroClass);
    	registrarEmCache(registroClass);
    	return new StringParser<T>(ParserCache.get(registroClass)).parse(dados);
    }
    
    public static <T> String fromRegistro(T registro) {
    	assertRegistroIsNotNull(registro);
    	registrarEmCache(registro.getClass());
    	return new ObjectParser<T>(ParserCache.get(registro.getClass())).parse(registro);
	}
    
    private static void registrarEmCache(Class<?> registroClass) {
    	if (!ParserCache.containsKey(registroClass)) {
            ParserConfig config = new ParserConfig(registroClass);
            ParserCache.put(registroClass, new RegistroInfo(registroClass, config.getCampos()));
        }
    }
    
    private static void assertClassIsNotNull(Class<?> clazz) {
        if (null == clazz) throw new ParserException(of(ERR_PARSE_CLASSE_NULA));
    }
    
    private static <T> void assertRegistroIsNotNull(T objeto) {
        if (null == objeto) throw new ParserException(of(ERR_PARSE_REGISTRO_NULO));
    }
}
