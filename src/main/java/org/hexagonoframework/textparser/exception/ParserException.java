package org.hexagonoframework.textparser.exception;

public class ParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final transient Error error;

    public ParserException(final Error error) {
        super(error.getMessage());
        this.error = error;
    }

    public ParserException(final Error error, Throwable throwable) {
        super(error.getMessage(), throwable);
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}