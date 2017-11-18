package com.github.hexagonoframework.textparser.converter;

public class IntegerTypeConverter implements TypeConverter<Integer> {

	public Integer getAsObject(String string) {
		return Integer.parseInt(string);
	}

	public String getAsString(Integer object) {
		return Integer.toString(object);
	}

}
