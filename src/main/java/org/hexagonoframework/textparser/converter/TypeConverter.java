package org.hexagonoframework.textparser.converter;

interface TypeConverter<T> {

	T getAsObject(String string);
	String getAsString(T object);
	
}
