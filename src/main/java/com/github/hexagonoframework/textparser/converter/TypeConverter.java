package com.github.hexagonoframework.textparser.converter;

public interface TypeConverter<T> {

	T getAsObject(String string);
	String getAsString(T object);
	
}
