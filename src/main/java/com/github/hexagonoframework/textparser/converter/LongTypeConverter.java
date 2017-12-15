package com.github.hexagonoframework.textparser.converter;

import org.apache.commons.lang3.StringUtils;

import com.github.hexagonoframework.textparser.annotation.Campo;

public class LongTypeConverter extends AbstractTypeConverter<Long> {

    public LongTypeConverter(Campo campo) {
        super(campo);
    }
    
	public Long getAsObject(String string) {
		return Long.parseLong(string);
	}

	public String getAsString(Long object) {
	    String value = Long.toString(object);
        switch (getCampo().padAlign()) {
        case RIGHT:
            return StringUtils.rightPad(value, getCampo().tamanho(), getCampo().padChar());

        case LEFT:
        default:
            return StringUtils.leftPad(value, getCampo().tamanho(), getCampo().padChar());
        }
	}

}
