package com.github.hexagonoframework.textparser;

import static com.github.hexagonoframework.core.exception.Error.of;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_CONVERTER_NAO_ENCONTRADO;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_REGISTRO_CAMPO_VALOR;

import java.util.Map.Entry;

import com.github.hexagonoframework.textparser.annotation.Registro;
import com.github.hexagonoframework.textparser.converter.IntegerTypeConverter;
import com.github.hexagonoframework.textparser.converter.LongTypeConverter;
import com.github.hexagonoframework.textparser.converter.StringTypeConverter;
import com.github.hexagonoframework.textparser.exception.TextParserException;

class ObjectParser<T> {

	private final RegistroInfo registroInfo;
	private T registro;

	ObjectParser(RegistroInfo registroInfo) {
		this.registroInfo = registroInfo;
	}

	String parse(T registro) {
		this.registro = registro;
		StringBuilder output = new StringBuilder();

		for (Entry<Integer, CampoInfo> entry : registroInfo.getCampos().entrySet()) {
			output.append(getValue(entry.getValue()));
		}

		return output.toString();
	}

	private String getValue(CampoInfo campoInfo) {
		Class<?> campoClass = campoInfo.getField().getType();
		Object value;
		try {
			value = campoInfo.getField().get(registro);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new TextParserException(of(PARSE_REGISTRO_CAMPO_VALOR, campoInfo.getField().getName(),
					registro.getClass().getName()), e);
		}
		
		if (String.class.equals(campoClass)) {
            return new StringTypeConverter().getAsString((String) value);
        }

        if (Integer.class.equals(campoClass) || campoClass.isAssignableFrom(Integer.TYPE)) {
            return new IntegerTypeConverter().getAsString((Integer) value);
        }

        if (Long.class.equals(campoClass) || campoClass.isAssignableFrom(Long.TYPE)) {
        	return new LongTypeConverter().getAsString((Long) value);
        }
        
        if (campoClass.isAnnotationPresent(Registro.class)) {
        	return TextParser.toText(value);
		}
        
        throw new TextParserException(of(PARSE_CONVERTER_NAO_ENCONTRADO, campoClass.getName()));
	}
}
