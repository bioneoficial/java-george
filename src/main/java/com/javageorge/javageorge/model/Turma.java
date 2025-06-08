package com.javageorge.javageorge.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "turmas")
public class Turma implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoTurma;

    @Column(nullable = false)
    private Integer limite;

    @Column(nullable = false)
    private String periodo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Professor professor;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matricula> matriculas = new ArrayList<>();

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prova> provas = new ArrayList<>();

    // Constructors
    public Turma() {
    }

    public Turma(Integer limite, String periodo, Disciplina disciplina, Professor professor) {
        this.limite = limite;
        this.periodo = periodo;
        this.disciplina = disciplina;
        this.professor = professor;
    }

    // Getters and Setters
    public Integer getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(Integer codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public Integer getLimite() {
        return limite;
    }

    public void setLimite(Integer limite) {
        this.limite = limite;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public List<Prova> getProvas() {
        return provas;
    }

    public void setProvas(List<Prova> provas) {
        this.provas = provas;
    }

    // Helper methods
    public void addMatricula(Matricula matricula) {
        matriculas.add(matricula);
        matricula.setTurma(this);
    }

    public void removeMatricula(Matricula matricula) {
        matriculas.remove(matricula);
        matricula.setTurma(null);
    }

    public void addProva(Prova prova) {
        provas.add(prova);
        prova.setTurma(this);
    }

    public void removeProva(Prova prova) {
        provas.remove(prova);
        prova.setTurma(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Turma turma = (Turma) o;

        return codigoTurma != null ? codigoTurma.equals(turma.codigoTurma) : turma.codigoTurma == null;
    }

    @Override
    public int hashCode() {
        return codigoTurma != null ? codigoTurma.hashCode() : 0;
    }
}
