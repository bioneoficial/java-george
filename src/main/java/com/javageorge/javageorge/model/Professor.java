package com.javageorge.javageorge.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "professores")
public class Professor extends Pessoa {

    @Column(name = "codigo_professor")
    private Integer codigoProfessor;

    @Column(nullable = false)
    private String especialidade;

    @Column
    private String titulo;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<Turma> turmas = new ArrayList<>();

    // Constructors
    public Professor() {
    }

    public Professor(String nome, String cpf, String email, String endereco, 
                   String especialidade, String titulo) {
        super(nome, cpf, email, endereco);
        this.especialidade = especialidade;
        this.titulo = titulo;
    }

    // Getters and Setters
    public Integer getCodigoProfessor() {
        return codigoProfessor;
    }

    public void setCodigoProfessor(Integer codigoProfessor) {
        this.codigoProfessor = codigoProfessor;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }

    // Helper methods
    public void addTurma(Turma turma) {
        turmas.add(turma);
        turma.setProfessor(this);
    }

    public void removeTurma(Turma turma) {
        turmas.remove(turma);
        turma.setProfessor(null);
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
