package com.javageorge.javageorge.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alunos")
public class Aluno extends Pessoa {

    @Column(name = "codigo_aluno")
    private Integer codigoAluno;

    @Column(unique = true, nullable = false)
    private String matricula;

    @Column(nullable = false)
    private LocalDate dataIngresso;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matricula> matriculas = new ArrayList<>();

    // Constructors
    public Aluno() {
    }

    public Aluno(String nome, String cpf, String email, String endereco, 
                String matricula, LocalDate dataIngresso) {
        super(nome, cpf, email, endereco);
        this.matricula = matricula;
        this.dataIngresso = dataIngresso;
    }

    // Getters and Setters
    public Integer getCodigoAluno() {
        return codigoAluno;
    }

    public void setCodigoAluno(Integer codigoAluno) {
        this.codigoAluno = codigoAluno;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public LocalDate getDataIngresso() {
        return dataIngresso;
    }

    public void setDataIngresso(LocalDate dataIngresso) {
        this.dataIngresso = dataIngresso;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    // Helper methods
    public void addMatricula(Matricula matricula) {
        matriculas.add(matricula);
        matricula.setAluno(this);
    }

    public void removeMatricula(Matricula matricula) {
        matriculas.remove(matricula);
        matricula.setAluno(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        return true; // A igualdade já é verificada pela superclasse usando codigoPessoa
    }

    @Override
    public int hashCode() {
        return super.hashCode(); // O hashCode já é calculado pela superclasse usando codigoPessoa
    }
}
