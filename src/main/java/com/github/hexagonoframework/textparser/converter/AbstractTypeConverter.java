package com.github.hexagonoframework.textparser.converter;

import com.github.hexagonoframework.textparser.annotation.Campo;

public abstract class AbstractTypeConverter<T> implements TypeConverter<T> {

    private final Campo campo;
    
    public AbstractTypeConverter(Campo campo) {
        this.campo = campo;
    }
    
    public Campo getCampo() {
        return campo;
    }
}
