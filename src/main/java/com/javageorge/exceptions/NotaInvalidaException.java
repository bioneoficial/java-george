package com.javageorge.exceptions;

public class NotaInvalidaException extends RuntimeException {
    public NotaInvalidaException(String message) {
        super(message);
    }

    public NotaInvalidaException() {
        super("Valor da nota deve estar entre 0 e 10.");
    }
}
