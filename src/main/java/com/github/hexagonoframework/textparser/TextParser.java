package com.github.hexagonoframework.textparser;

import static com.github.hexagonoframework.core.exception.Error.of;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_CLASSE_NULA;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_REGISTRO_NULO;

import com.github.hexagonoframework.textparser.exception.TextParserException;

public class TextParser {

    private TextParser() {
        super();
    }

    static {
        new TextParser();
    }

    public static <T> T fromText(String dados, Class<T> registroClass) {
        assertRegistroClassIsNotNull(registroClass);
        registrarEmCache(registroClass);
        return new StringParser<T>(ParserCache.get(registroClass)).parse(dados);
    }

    public static <T> String toText(T registro) {
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

    private static void assertRegistroClassIsNotNull(Class<?> registroClass) {
        if (null == registroClass)
            throw new TextParserException(of(PARSE_CLASSE_NULA));
    }

    private static <T> void assertRegistroIsNotNull(T objeto) {
        if (null == objeto)
            throw new TextParserException(of(PARSE_REGISTRO_NULO));
    }
}
