package com.github.hexagonoframework.textparser.converter;

import org.apache.commons.lang3.StringUtils;

import com.github.hexagonoframework.textparser.annotation.Campo;

public class StringTypeConverter extends AbstractTypeConverter<String> {

    public StringTypeConverter(Campo campo) {
        super(campo);
    }

    @Override
    public String getAsObject(String string) {
        return string.trim();
    }

    @Override
    public String getAsString(String object) {
        switch (getCampo().padAlign()) {
        case LEFT:
            return StringUtils.leftPad(object, getCampo().tamanho(), getCampo().padChar());

        case RIGHT:
        default:
            return StringUtils.rightPad(object, getCampo().tamanho(), getCampo().padChar());
        }
    }

}
