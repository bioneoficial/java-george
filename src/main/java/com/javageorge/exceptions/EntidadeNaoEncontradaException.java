package com.javageorge.exceptions;

public class EntidadeNaoEncontradaException extends RuntimeException {
    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }

    public EntidadeNaoEncontradaException(String entidade, Integer id) {
        super(entidade + " não encontrado(a) com id: " + id);
    }
}
