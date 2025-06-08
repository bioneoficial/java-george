package com.javageorge.entities;

import com.javageorge.exceptions.AlunoJaMatriculadoException;
import com.javageorge.exceptions.TurmaLotadaException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "alunos")
@Getter
@Setter
@NoArgsConstructor
public class Aluno extends Pessoa {

    @Column(unique = true, nullable = false)
    private String matriculaUnica;

    @Column(nullable = false)
    private LocalDate dataIngresso;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matricula> matriculas = new ArrayList<>();

    public Aluno(Integer codigoPessoa, String nome, String cpf, String email, 
                String endereco, String telefone, String matriculaUnica, LocalDate dataIngresso) {
        super(codigoPessoa, nome, cpf, email, endereco, telefone);
        this.matriculaUnica = matriculaUnica;
        this.dataIngresso = dataIngresso;
    }

    public List<Matricula> getMatriculas() {
        return Collections.unmodifiableList(matriculas);
    }

    public void addMatricula(Matricula matricula) {
        matriculas.add(matricula);
        matricula.setAluno(this);
    }

    public void removeMatricula(Matricula matricula) {
        matriculas.remove(matricula);
        matricula.setAluno(null);
    }

    /**
     * Realiza matrícula do aluno em uma turma
     * 
     * @param turma A turma onde o aluno será matriculado
     * @return A matrícula realizada
     * @throws TurmaLotadaException Se a turma já atingiu o limite de alunos
     * @throws AlunoJaMatriculadoException Se o aluno já está matriculado na turma
     */
    public Matricula realizarMatricula(Turma turma) {
        // Verifica se a turma está lotada
        if (turma.estaLotada()) {
            throw new TurmaLotadaException();
        }

        // Verifica se o aluno já está matriculado na turma
        for (Matricula m : matriculas) {
            if (m.getTurma().equals(turma) && "ATIVA".equals(m.getStatus())) {
                throw new AlunoJaMatriculadoException();
            }
        }

        // Cria a matrícula
        Matricula matricula = new Matricula();
        matricula.setAluno(this);
        matricula.setTurma(turma);
        matricula.setDataMatricula(LocalDate.now());
        matricula.setStatus("ATIVA");

        // Adiciona a matrícula ao aluno e à turma
        this.addMatricula(matricula);
        turma.addMatricula(matricula);

        return matricula;
    }
}
