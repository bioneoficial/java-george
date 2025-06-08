package com.javageorge.javageorge.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "disciplinas")
public class Disciplina implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoDisciplina;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer cargaHoraria;

    @ManyToMany
    @JoinTable(
        name = "disciplina_prerequisitos",
        joinColumns = @JoinColumn(name = "disciplina_id"),
        inverseJoinColumns = @JoinColumn(name = "prerequisito_id")
    )
    private Set<Disciplina> prerequisitos = new HashSet<>();

    @ManyToMany(mappedBy = "prerequisitos")
    private Set<Disciplina> disciplinasQueExigemEsta = new HashSet<>();

    @OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL)
    private List<Turma> turmas = new ArrayList<>();

    // Constructors
    public Disciplina() {
    }

    public Disciplina(String nome, Integer cargaHoraria) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
    }

    // Getters and Setters
    public Integer getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(Integer codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Set<Disciplina> getPrerequisitos() {
        return prerequisitos;
    }

    public void setPrerequisitos(Set<Disciplina> prerequisitos) {
        this.prerequisitos = prerequisitos;
    }

    public Set<Disciplina> getDisciplinasQueExigemEsta() {
        return disciplinasQueExigemEsta;
    }

    public void setDisciplinasQueExigemEsta(Set<Disciplina> disciplinasQueExigemEsta) {
        this.disciplinasQueExigemEsta = disciplinasQueExigemEsta;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }

    // Helper methods
    public void addPrerequisito(Disciplina disciplina) {
        prerequisitos.add(disciplina);
        disciplina.getDisciplinasQueExigemEsta().add(this);
    }

    public void removePrerequisito(Disciplina disciplina) {
        prerequisitos.remove(disciplina);
        disciplina.getDisciplinasQueExigemEsta().remove(this);
    }

    public void addTurma(Turma turma) {
        turmas.add(turma);
        turma.setDisciplina(this);
    }

    public void removeTurma(Turma turma) {
        turmas.remove(turma);
        turma.setDisciplina(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Disciplina that = (Disciplina) o;

        return codigoDisciplina != null ? codigoDisciplina.equals(that.codigoDisciplina) : that.codigoDisciplina == null;
    }

    @Override
    public int hashCode() {
        return codigoDisciplina != null ? codigoDisciplina.hashCode() : 0;
    }
}
