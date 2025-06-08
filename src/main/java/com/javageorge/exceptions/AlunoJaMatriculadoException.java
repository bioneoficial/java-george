package com.javageorge.exceptions;
public class AlunoJaMatriculadoException extends RuntimeException {
    public AlunoJaMatriculadoException(String message) {
        super(message);
    }

    public AlunoJaMatriculadoException() {
        super("O aluno já está matriculado nesta turma.");
    }
}
