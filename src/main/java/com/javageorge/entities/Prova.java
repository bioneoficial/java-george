package com.javageorge.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "provas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public List<Nota> getNotas() {
        return Collections.unmodifiableList(notas);
    }

    /**
     * Adiciona uma nota à prova
     * 
     * @param nota A nota a ser adicionada
     */
    public void addNota(Nota nota) {
        notas.add(nota);
        nota.setProva(this);
    }

    /**
     * Remove uma nota da prova
     * 
     * @param nota A nota a ser removida
     */
    public void removeNota(Nota nota) {
        notas.remove(nota);
        nota.setProva(null);
    }

    /**
     * Calcula a média das notas para esta prova
     * 
     * @return A média das notas ou 0 se não houver notas
     */
    public Double calcularMedia() {
        if (notas.isEmpty()) {
            return 0.0;
        }

        double soma = notas.stream()
                .mapToDouble(Nota::getValor)
                .sum();

        return soma / notas.size();
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
