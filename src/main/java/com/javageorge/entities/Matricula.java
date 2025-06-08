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
@Table(name = "matriculas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public List<Nota> getNotas() {
        return Collections.unmodifiableList(notas);
    }

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
