package com.github.hexagonoframework.textparser.converter;

public class LongTypeConverter implements TypeConverter<Long> {

	public Long getAsObject(String string) {
		return Long.parseLong(string);
	}

	public String getAsString(Long object) {
		return Long.toString(object);
	}

}
