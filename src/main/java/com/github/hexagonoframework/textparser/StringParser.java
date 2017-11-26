package com.github.hexagonoframework.textparser;

import static com.github.hexagonoframework.core.exception.Error.of;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_CLASSE_ATRIBUTO_INVALIDO;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_CLASSE_CONSTRUTOR_INVALIDO;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_CONVERTER_NAO_ENCONTRADO;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_DADOS_VAZIO;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_TAMANHO_DADOS_DIFERENTE_REGISTRO;

import java.util.Map.Entry;

import com.github.hexagonoframework.textparser.annotation.Registro;
import com.github.hexagonoframework.textparser.converter.IntegerTypeConverter;
import com.github.hexagonoframework.textparser.converter.LongTypeConverter;
import com.github.hexagonoframework.textparser.converter.StringTypeConverter;
import com.github.hexagonoframework.textparser.exception.TextParserException;

class StringParser<T> {

	private String dados;
    private RegistroInfo registroInfo;

    StringParser(RegistroInfo registroInfo) {
        this.registroInfo = registroInfo;
    }
	
    @SuppressWarnings("unchecked")
	T parse(String dados) {
        this.dados = dados;
        assertDadosIsNotEmpty();
        assertTamanhoDadosIgualTamanhoRegistro();

        T object;
		try {
			object = (T) registroInfo.getRegistroClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new TextParserException(of(PARSE_CLASSE_CONSTRUTOR_INVALIDO, registroInfo.getRegistroClass().getName()), e);
		}
        
		Class<T> newClass = (Class<T>) object.getClass();
        for (Entry<Integer, CampoInfo> entry : registroInfo.getCampos().entrySet()) {
            CampoInfo campo = entry.getValue();
            String substring = dados.substring(campo.getPosicaoInicial(), campo.getPosicaoInicial() + campo.getTamanho());
            Object value = convertValue(campo.getField().getType(), substring);
            
            try {
				newClass.getField(campo.getField().getName()).set(object, value);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				throw new TextParserException(of(PARSE_CLASSE_ATRIBUTO_INVALIDO, campo.getField().getName(),
						registroInfo.getRegistroClass().getName()), e);
			}
        }

        return object;
    }

    private void assertDadosIsNotEmpty() {
        if (null == dados || "".equals(dados)) throw new TextParserException(of(PARSE_DADOS_VAZIO));
    }

    private void assertTamanhoDadosIgualTamanhoRegistro() {
        if (dados.length() != registroInfo.getTamanhoTotal()) {
            throw new TextParserException(of(PARSE_TAMANHO_DADOS_DIFERENTE_REGISTRO, dados.length(), registroInfo.getTamanhoTotal()));
        }
    }

	@SuppressWarnings("unchecked")
	private T convertValue(Class<?> clazz, String substring) {
        if (String.class.equals(clazz)) {
            return (T) new StringTypeConverter().getAsObject(substring);
        }

        if (Integer.class.equals(clazz) || clazz.isAssignableFrom(Integer.TYPE)) {
            return (T) new IntegerTypeConverter().getAsObject(substring);
        }

        if (Long.class.equals(clazz) || clazz.isAssignableFrom(Long.TYPE)) {
        	return (T) new LongTypeConverter().getAsObject(substring);
        }
        
        if (clazz.isAnnotationPresent(Registro.class)) {
        	return (T) TextParser.fromText(substring, clazz);
        }

        throw new TextParserException(of(PARSE_CONVERTER_NAO_ENCONTRADO, clazz.getName()));
    }
}
