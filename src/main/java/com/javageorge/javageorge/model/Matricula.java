package com.javageorge.javageorge.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matriculas")
public class Matricula implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoMatricula;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDate dataMatricula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nota> notas = new ArrayList<>();

    // Constructors
    public Matricula() {
    }

    public Matricula(String status, LocalDate dataMatricula, Aluno aluno, Turma turma) {
        this.status = status;
        this.dataMatricula = dataMatricula;
        this.aluno = aluno;
        this.turma = turma;
    }

    // Getters and Setters
    public Integer getCodigoMatricula() {
        return codigoMatricula;
    }

    public void setCodigoMatricula(Integer codigoMatricula) {
        this.codigoMatricula = codigoMatricula;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    // Helper methods
    public void addNota(Nota nota) {
        notas.add(nota);
        nota.setMatricula(this);
    }

    public void removeNota(Nota nota) {
        notas.remove(nota);
        nota.setMatricula(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matricula matricula = (Matricula) o;

        return codigoMatricula != null ? codigoMatricula.equals(matricula.codigoMatricula) : matricula.codigoMatricula == null;
    }

    @Override
    public int hashCode() {
        return codigoMatricula != null ? codigoMatricula.hashCode() : 0;
    }
}
