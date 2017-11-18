package com.github.hexagonoframework.textparser.exception;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Objects;

public class Error {

    private ErrorType type;
    private Object[] parametros;

    private Error(ErrorType type, Object... parametros) {
        this.type = type;
        this.parametros = parametros;
    }

    public static Error of(ErrorType type, Object... parametros) {
        return new Error(type, parametros);
    }

    public ErrorType getType() {
        return type;
    }

    public String getMessage() {
        return MessageFormat.format(type.getPattern(), parametros);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Error error = (Error) o;
        return Objects.equals(type, error.type) &&
                Arrays.equals(parametros, error.parametros);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, parametros);
    }

    @Override
    public String toString() {
        return "Error{" +
                "type=" + type +
                ", parametros=" + Arrays.toString(parametros) +
                ", message=" + getMessage() +
                '}';
    }
}
