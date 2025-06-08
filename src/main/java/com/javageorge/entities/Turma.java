package com.javageorge.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "turmas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Turma implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoTurma;

    @Column(nullable = false)
    private Integer limiteAlunos;

    @Column(nullable = false)
    private String periodoLetivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matricula> matriculas = new ArrayList<>();

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prova> provas = new ArrayList<>();

    public List<Matricula> getMatriculas() {
        return Collections.unmodifiableList(matriculas);
    }

    public List<Prova> getProvas() {
        return Collections.unmodifiableList(provas);
    }

    /**
     * Obtém o número de alunos matriculados na turma
     * 
     * @return O número de alunos com matrícula ativa
     */
    public int getNumeroAlunosMatriculados() {
        return (int) matriculas.stream()
                .filter(m -> "ATIVA".equals(m.getStatus()))
                .count();
    }

    /**
     * Verifica se a turma está lotada
     * 
     * @return true se o número de alunos matriculados for igual ou maior que o limite
     */
    public boolean estaLotada() {
        return getNumeroAlunosMatriculados() >= limiteAlunos;
    }

    /**
     * Obtém a lista de alunos matriculados na turma
     * 
     * @return Lista de alunos com matrícula ativa
     */
    public List<Aluno> getAlunosMatriculados() {
        return matriculas.stream()
                .filter(m -> "ATIVA".equals(m.getStatus()))
                .map(Matricula::getAluno)
                .collect(Collectors.toList());
    }

    /**
     * Adiciona uma matrícula à turma
     * 
     * @param m A matrícula a ser adicionada
     * @return true se a matrícula foi adicionada com sucesso
     */
    public boolean adicionarMatricula(Matricula m) {
        if (m == null) {
            return false;
        }

        if (estaLotada() && "ATIVA".equals(m.getStatus())) {
            return false;
        }

        matriculas.add(m);
        m.setTurma(this);
        return true;
    }

    /**
     * Remove uma matrícula da turma
     * 
     * @param m A matrícula a ser removida
     * @return true se a matrícula foi removida com sucesso
     */
    public boolean removerMatricula(Matricula m) {
        if (m == null) {
            return false;
        }

        boolean result = matriculas.remove(m);
        if (result) {
            m.setTurma(null);
        }
        return result;
    }

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
    @Table(name = "turmas")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Turma implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoTurma;

    @Column(nullable = false)
    private String periodo;

    @Column(nullable = false)
    private Integer limiteAlunos;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matricula> matriculas = new ArrayList<>();

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prova> provas = new ArrayList<>();

    public List<Matricula> getMatriculas() {
        return Collections.unmodifiableList(matriculas);
    }

    public List<Prova> getProvas() {
        return Collections.unmodifiableList(provas);
    }

    /**
     * Verifica se a turma está lotada
     * 
     * @return true se a turma estiver lotada, false caso contrário
     */
    public boolean estaLotada() {
        int alunosAtivos = 0;
        for (Matricula m : matriculas) {
            if ("ATIVA".equals(m.getStatus())) {
                alunosAtivos++;
            }
        }
        return alunosAtivos >= limiteAlunos;
    }

    /**
     * Adiciona uma matrícula à turma
     * 
     * @param matricula A matrícula a ser adicionada
     */
    public void addMatricula(Matricula matricula) {
        matriculas.add(matricula);
        matricula.setTurma(this);
    }

    /**
     * Remove uma matrícula da turma
     * 
     * @param matricula A matrícula a ser removida
     */
    public void removeMatricula(Matricula matricula) {
        matriculas.remove(matricula);
        matricula.setTurma(null);
    }

    /**
     * Adiciona uma prova à turma
     * 
     * @param prova A prova a ser adicionada
     */
    public void addProva(Prova prova) {
        provas.add(prova);
        prova.setTurma(this);
    }

    /**
     * Remove uma prova da turma
     * 
     * @param prova A prova a ser removida
     */
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

    /**
     * Remove uma prova da turma
     * 
     * @param prova A prova a ser removida
     */
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
