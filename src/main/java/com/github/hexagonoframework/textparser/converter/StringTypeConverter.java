package com.github.hexagonoframework.textparser.converter;

public class StringTypeConverter implements TypeConverter<String> {

	public String getAsObject(String string) {
		return string.trim();
	}

	public String getAsString(String object) {
		return object;
	}

}
