package com.javageorge.javageorge.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "notas")
public class Nota implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoNota;

    @Column(nullable = false)
    private Double valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula_id", nullable = false)
    private Matricula matricula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prova_id", nullable = false)
    private Prova prova;

    // Constructors
    public Nota() {
    }

    public Nota(Double valor, Matricula matricula, Prova prova) {
        this.valor = valor;
        this.matricula = matricula;
        this.prova = prova;
    }

    // Getters and Setters
    public Integer getCodigoNota() {
        return codigoNota;
    }

    public void setCodigoNota(Integer codigoNota) {
        this.codigoNota = codigoNota;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Prova getProva() {
        return prova;
    }

    public void setProva(Prova prova) {
        this.prova = prova;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nota nota = (Nota) o;

        return codigoNota != null ? codigoNota.equals(nota.codigoNota) : nota.codigoNota == null;
    }

    @Override
    public int hashCode() {
        return codigoNota != null ? codigoNota.hashCode() : 0;
    }
}
