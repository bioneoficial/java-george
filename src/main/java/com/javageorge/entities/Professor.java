package com.javageorge.entities;
import com.javageorge.exceptions.NotaInvalidaException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "professores")
@Getter
@Setter
@NoArgsConstructor
public class Professor extends Pessoa {
    private static final long serialVersionUID = 1L;

    @Column(unique = true, nullable = false)
    private String siape;

    @Column(nullable = false)
    private String departamento;

    private String especialidade;

    private String tituloAcademico;

    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
    private List<Turma> turmas = new ArrayList<>();

    public Professor(Integer codigoPessoa, String nome, String cpf, String email, 
                   String endereco, String telefone, String siape, String departamento) {
        super(codigoPessoa, nome, cpf, email, endereco, telefone);
        this.siape = siape;
        this.departamento = departamento;
    }

    public Professor(Integer codigoPessoa, String nome, String cpf, String email, 
                    String endereco, String telefone, String siape, String departamento,
                    String especialidade, String tituloAcademico) {
        super(codigoPessoa, nome, cpf, email, endereco, telefone);
        this.siape = siape;
        this.departamento = departamento;
        this.especialidade = especialidade;
        this.tituloAcademico = tituloAcademico;
    }

    public List<Turma> getTurmas() {
        return Collections.unmodifiableList(turmas);
    }

    /**
     * Cadastra uma nova prova para uma turma
     * 
     * @param turma A turma para qual a prova será cadastrada
     * @param data A data da prova
     * @param peso O peso da prova (entre 0 e 1)
     * @return A prova cadastrada
     * @throws IllegalArgumentException Se a turma for nula ou a data estiver no passado
     * @throws NotaInvalidaException Se o peso for inválido (não estiver entre 0 e 1)
     */
    public Prova cadastrarProva(Turma turma, LocalDate data, double peso) {
        // Validações
        if (turma == null) {
            throw new IllegalArgumentException("A turma não pode ser nula");
        }

        if (data == null || data.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data da prova deve ser hoje ou no futuro");
        }

        if (peso <= 0 || peso > 1) {
            throw new NotaInvalidaException("O peso da prova deve ser maior que 0 e no máximo 1");
        }

        // Verifica se o professor ministra a turma
        if (!turma.getProfessor().equals(this)) {
            throw new IllegalArgumentException("O professor não ministra esta turma");
        }

        // Cria a prova
        Prova prova = new Prova();
        prova.setDataProva(data);
        prova.setPeso(peso);
        prova.setTurma(turma);

        // Adiciona a prova à turma
        turma.addProva(prova);

        return prova;
    }

    /**
     * Lança uma nota para um aluno em uma prova
     * 
     * @param matricula A matrícula do aluno
     * @param prova A prova para a qual será lançada a nota
     * @param valor O valor da nota (entre 0 e 10)
     * @return A nota cadastrada
     * @throws IllegalArgumentException Se a matrícula ou prova forem nulas
     * @throws NotaInvalidaException Se o valor da nota não estiver entre 0 e 10
     */
    public Nota lancarNota(Matricula matricula, Prova prova, double valor) {
        // Validações
        if (matricula == null) {
            throw new IllegalArgumentException("A matrícula não pode ser nula");
        }

        if (prova == null) {
            throw new IllegalArgumentException("A prova não pode ser nula");
        }

        if (valor < 0 || valor > 10) {
            throw new NotaInvalidaException("O valor da nota deve estar entre 0 e 10");
        }

        // Verifica se a prova pertence à turma da matrícula
        if (!prova.getTurma().equals(matricula.getTurma())) {
            throw new IllegalArgumentException("A prova não pertence à turma da matrícula");
        }

        // Verifica se o professor ministra a turma da prova
        if (!prova.getTurma().getProfessor().equals(this)) {
            throw new IllegalArgumentException("O professor não ministra a turma desta prova");
        }

        // Cria a nota
        Nota nota = new Nota();
        nota.setMatricula(matricula);
        nota.setProva(prova);
        nota.setValor(valor);

        // Adiciona a nota à prova
        prova.addNota(nota);

        // Adiciona a nota à matrícula
        matricula.addNota(nota);

        return nota;
    }
}
