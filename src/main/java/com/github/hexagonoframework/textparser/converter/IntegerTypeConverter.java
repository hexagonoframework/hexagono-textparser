package com.github.hexagonoframework.textparser.converter;

import org.apache.commons.lang3.StringUtils;

import com.github.hexagonoframework.textparser.annotation.Campo;

public class IntegerTypeConverter extends AbstractTypeConverter<Integer> {

    public IntegerTypeConverter(Campo campo) {
        super(campo);
    }
    
	public Integer getAsObject(String string) {
		return Integer.parseInt(string);
	}

	public String getAsString(Integer object) {
	    String value = Integer.toString(object);
	    switch (getCampo().padAlign()) {
	    case RIGHT:
            return StringUtils.rightPad(value, getCampo().tamanho(), getCampo().padChar());

	    case LEFT:
        default:
            return StringUtils.leftPad(value, getCampo().tamanho(), getCampo().padChar());
	    }
	}

}
