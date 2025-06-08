package com.javageorge.exceptions;

public class TurmaLotadaException extends RuntimeException {
    public TurmaLotadaException(String message) {
        super(message);
    }

    public TurmaLotadaException() {
        super("A turma atingiu o limite máximo de alunos.");
    }
}
