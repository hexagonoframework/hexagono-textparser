package com.github.hexagonoframework.textparser.exception;

public class ParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final transient com.github.hexagonoframework.textparser.exception.Error error;

    public ParserException(final com.github.hexagonoframework.textparser.exception.Error error) {
        super(error.getMessage());
        this.error = error;
    }

    public ParserException(final com.github.hexagonoframework.textparser.exception.Error error, Throwable throwable) {
        super(error.getMessage(), throwable);
        this.error = error;
    }

    public com.github.hexagonoframework.textparser.exception.Error getError() {
        return error;
    }
}