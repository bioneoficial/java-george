package com.javageorge.entities;
package com.javageorge.entities;

import com.javageorge.exceptions.PreRequisitoException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "disciplinas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Disciplina implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoDisciplina;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer cargaHoraria;

    @Column(nullable = false)
    private String ementa;

    @ManyToMany
    @JoinTable(
        name = "disciplina_prerequisito",
        joinColumns = @JoinColumn(name = "disciplina_id"),
        inverseJoinColumns = @JoinColumn(name = "prerequisito_id")
    )
    private Set<Disciplina> preRequisitos = new HashSet<>();

    @OneToMany(mappedBy = "disciplina")
    private List<Turma> turmas = new ArrayList<>();

    public Set<Disciplina> getPreRequisitos() {
        return Collections.unmodifiableSet(preRequisitos);
    }

    public List<Turma> getTurmas() {
        return Collections.unmodifiableList(turmas);
    }

    /**
     * Adiciona um pré-requisito à disciplina
     * 
     * @param disciplina A disciplina que será pré-requisito
     * @throws PreRequisitoException Se tentar adicionar a própria disciplina como pré-requisito
     */
    public void addPreRequisito(Disciplina disciplina) {
        if (this.equals(disciplina)) {
            throw new PreRequisitoException("Uma disciplina não pode ser pré-requisito dela mesma");
        }
        preRequisitos.add(disciplina);
    }

    /**
     * Remove um pré-requisito da disciplina
     * 
     * @param disciplina A disciplina a ser removida dos pré-requisitos
     */
    public void removePreRequisito(Disciplina disciplina) {
        preRequisitos.remove(disciplina);
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
import com.javageorge.exceptions.PreRequisitoException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "disciplinas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Disciplina implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoDisciplina;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer cargaHoraria;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "disciplina_prerequisitos",
        joinColumns = @JoinColumn(name = "disciplina_id"),
        inverseJoinColumns = @JoinColumn(name = "prerequisito_id")
    )
    private List<Disciplina> preRequisitos = new ArrayList<>();

    @OneToMany(mappedBy = "disciplina", fetch = FetchType.LAZY)
    private List<Turma> turmas = new ArrayList<>();

    public List<Disciplina> getPreRequisitos() {
        return Collections.unmodifiableList(preRequisitos);
    }

    /**
     * Adiciona um pré-requisito à disciplina
     * 
     * @param d A disciplina a ser adicionada como pré-requisito
     * @throws PreRequisitoException Se a disciplina já for um pré-requisito ou se houver ciclo
     * @throws IllegalArgumentException Se a disciplina for nula ou igual a esta
     */
    public void adicionarPreRequisito(Disciplina d) {
        if (d == null) {
            throw new IllegalArgumentException("A disciplina não pode ser nula");
        }

        if (this.equals(d)) {
            throw new IllegalArgumentException("Uma disciplina não pode ser pré-requisito dela mesma");
        }

        if (preRequisitos.contains(d)) {
            throw new PreRequisitoException("Esta disciplina já é um pré-requisito");
        }

        // Verifica se há ciclo (se esta disciplina é pré-requisito da que estamos adicionando)
        if (verificarCicloPreRequisito(d, this)) {
            throw new PreRequisitoException("Esta operação criaria um ciclo de pré-requisitos");
        }

        preRequisitos.add(d);
    }

    /**
     * Remove um pré-requisito da disciplina
     * 
     * @param d A disciplina a ser removida dos pré-requisitos
     * @throws IllegalArgumentException Se a disciplina for nula
     */
    public void removerPreRequisito(Disciplina d) {
        if (d == null) {
            throw new IllegalArgumentException("A disciplina não pode ser nula");
        }

        preRequisitos.remove(d);
    }

    /**
     * Verifica se existe um ciclo de pré-requisitos
     * 
     * @param atual A disciplina atual sendo verificada
     * @param alvo A disciplina alvo sendo procurada nos pré-requisitos
     * @return true se houver ciclo, false caso contrário
     */
    private boolean verificarCicloPreRequisito(Disciplina atual, Disciplina alvo) {
        if (atual.getPreRequisitos().contains(alvo)) {
            return true;
        }

        for (Disciplina prereq : atual.getPreRequisitos()) {
            if (verificarCicloPreRequisito(prereq, alvo)) {
                return true;
            }
        }

        return false;
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
