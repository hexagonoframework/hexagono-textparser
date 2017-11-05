package org.hexagonoframework.textparser;

import org.hexagonoframework.textparser.annotation.Registro;
import org.hexagonoframework.textparser.converter.IntegerTypeConverter;
import org.hexagonoframework.textparser.converter.LongTypeConverter;
import org.hexagonoframework.textparser.converter.StringTypeConverter;
import org.hexagonoframework.textparser.exception.ParserException;

import java.util.Map.Entry;

import static org.hexagonoframework.textparser.exception.Error.of;
import static org.hexagonoframework.textparser.exception.ParserErrorType.ERR_PARSE_CONVERTER_NAO_ENCONTRADO;
import static org.hexagonoframework.textparser.exception.ParserErrorType.ERR_PARSE_REGISTRO_CAMPO_VALOR;

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
			throw new ParserException(of(ERR_PARSE_REGISTRO_CAMPO_VALOR, campoInfo.getField().getName(),
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
        	return RegistroParser.fromRegistro(value);
		}
        
        throw new ParserException(of(ERR_PARSE_CONVERTER_NAO_ENCONTRADO, campoClass.getName()));
	}
}
