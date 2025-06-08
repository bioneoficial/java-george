package com.javageorge.exceptions;

public class TurmaLotadaException extends RuntimeException {
    public TurmaLotadaException(String message) {
        super(message);
    }

    public TurmaLotadaException() {
        super("A turma já atingiu o limite de alunos.");
    }
}
