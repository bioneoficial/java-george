package com.javageorge.javageorge.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "provas")
public class Prova implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoProva;

    @Column(nullable = false)
    private LocalDate dataProva;

    @Column(nullable = false)
    private Double peso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @OneToMany(mappedBy = "prova", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nota> notas = new ArrayList<>();

    // Constructors
    public Prova() {
    }

    public Prova(LocalDate dataProva, Double peso, Turma turma) {
        this.dataProva = dataProva;
        this.peso = peso;
        this.turma = turma;
    }

    // Getters and Setters
    public Integer getCodigoProva() {
        return codigoProva;
    }

    public void setCodigoProva(Integer codigoProva) {
        this.codigoProva = codigoProva;
    }

    public LocalDate getDataProva() {
        return dataProva;
    }

    public void setDataProva(LocalDate dataProva) {
        this.dataProva = dataProva;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
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
        nota.setProva(this);
    }

    public void removeNota(Nota nota) {
        notas.remove(nota);
        nota.setProva(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prova prova = (Prova) o;

        return codigoProva != null ? codigoProva.equals(prova.codigoProva) : prova.codigoProva == null;
    }

    @Override
    public int hashCode() {
        return codigoProva != null ? codigoProva.hashCode() : 0;
    }
}
