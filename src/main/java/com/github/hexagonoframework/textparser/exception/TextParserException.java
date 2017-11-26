package com.github.hexagonoframework.textparser.exception;

import com.github.hexagonoframework.core.exception.Error;

public class TextParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final transient Error error;

    public TextParserException(final Error error) {
        super(error.getMessage());
        this.error = error;
    }

    public TextParserException(final Error error, Throwable throwable) {
        super(error.getMessage(), throwable);
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}