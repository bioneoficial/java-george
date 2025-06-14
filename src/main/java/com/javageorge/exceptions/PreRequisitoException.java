package com.javageorge.exceptions;
public class PreRequisitoException extends RuntimeException {
    public PreRequisitoException(String message) {
        super(message);
    }

    public PreRequisitoException() {
        super("Operação inválida com pré-requisito. Pode estar criando um ciclo ou o pré-requisito já existe.");
    }
}
