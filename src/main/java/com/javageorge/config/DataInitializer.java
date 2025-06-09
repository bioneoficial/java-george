package com.javageorge.config;

import com.javageorge.entities.*;
import com.javageorge.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Bean
    @Transactional
    public CommandLineRunner initData() {
        return args -> {
            System.out.println("Inicializando dados de exemplo...");

            // Criar professores
            List<Professor> professores = criarProfessores();

            // Criar disciplinas
            List<Disciplina> disciplinas = criarDisciplinas();

            // Adicionar pré-requisitos às disciplinas
            adicionarPreRequisitos(disciplinas);

            // Criar alunos
            List<Aluno> alunos = criarAlunos();

            // Criar turmas
            List<Turma> turmas = criarTurmas(disciplinas, professores);

            // Matricular alunos
            matricularAlunos(alunos, turmas);

            // Cadastrar provas
            List<Prova> provas = cadastrarProvas(turmas, professores);

            // Lançar notas
            lancarNotas(turmas, provas, professores);

            System.out.println("Dados de exemplo inicializados com sucesso!");
        };
    }

    private List<Professor> criarProfessores() {
        List<Professor> professores = new ArrayList<>();

        Professor p1 = new Professor(null, "João Silva", "12345678901", "joao.silva@javageorge.com", 
                "Rua A, 123", "(11) 98765-4321", "SIAPE001", "Computação", "Programação", "Doutor");

        Professor p2 = new Professor(null, "Maria Oliveira", "23456789012", "maria.oliveira@javageorge.com", 
                "Rua B, 456", "(11) 87654-3210", "SIAPE002", "Matemática", "Álgebra", "Mestre");

        Professor p3 = new Professor(null, "Carlos Santos", "34567890123", "carlos.santos@javageorge.com", 
                "Rua C, 789", "(11) 76543-2109", "SIAPE003", "Física", "Mecânica", "Doutor");

        professores.add(professorRepository.save(p1));
        professores.add(professorRepository.save(p2));
        professores.add(professorRepository.save(p3));

        return professores;
    }

    private List<Disciplina> criarDisciplinas() {
        List<Disciplina> disciplinas = new ArrayList<>();

        Disciplina d1 = new Disciplina();
        d1.setCodigo("PROG1");
        d1.setNome("Programação I");
        d1.setCargaHoraria(60);
        d1.setEmenta("Introdução à programação");

        Disciplina d2 = new Disciplina();
        d2.setCodigo("PROG2");
        d2.setNome("Programação II");
        d2.setCargaHoraria(60);
        d2.setEmenta("Programação orientada a objetos");

        Disciplina d3 = new Disciplina();
        d3.setCodigo("ESTR");
        d3.setNome("Estrutura de Dados");
        d3.setCargaHoraria(90);
        d3.setEmenta("Estruturas de dados avançadas");

        Disciplina d4 = new Disciplina();
        d4.setCodigo("ALG");
        d4.setNome("Álgebra Linear");
        d4.setCargaHoraria(60);
        d4.setEmenta("Conceitos básicos de álgebra linear");

        Disciplina d5 = new Disciplina();
        d5.setCodigo("CALC1");
        d5.setNome("Cálculo I");
        d5.setCargaHoraria(90);
        d5.setEmenta("Introdução ao cálculo diferencial");

        disciplinas.add(disciplinaRepository.save(d1));
        disciplinas.add(disciplinaRepository.save(d2));
        disciplinas.add(disciplinaRepository.save(d3));
        disciplinas.add(disciplinaRepository.save(d4));
        disciplinas.add(disciplinaRepository.save(d5));

        return disciplinas;
    }

    private void adicionarPreRequisitos(List<Disciplina> disciplinas) {
        // Programação II tem como pré-requisito Programação I
        disciplinas.get(1).addPreRequisito(disciplinas.get(0));

        // Estrutura de Dados tem como pré-requisito Programação II
        disciplinas.get(2).addPreRequisito(disciplinas.get(1));

        disciplinaRepository.saveAll(disciplinas);
    }

    private List<Aluno> criarAlunos() {
        List<Aluno> alunos = new ArrayList<>();

        Aluno a1 = new Aluno(null, "Ana Souza", "45678901234", "ana.souza@aluno.javageorge.com", 
                "Rua D, 101", "(11) 65432-1098", "MAT001", LocalDate.of(2022, 3, 1));

        Aluno a2 = new Aluno(null, "Bruno Costa", "56789012345", "bruno.costa@aluno.javageorge.com", 
                "Rua E, 202", "(11) 54321-0987", "MAT002", LocalDate.of(2022, 3, 1));

        Aluno a3 = new Aluno(null, "Carla Lima", "67890123456", "carla.lima@aluno.javageorge.com", 
                "Rua F, 303", "(11) 43210-9876", "MAT003", LocalDate.of(2022, 3, 1));

        Aluno a4 = new Aluno(null, "Daniel Rocha", "78901234567", "daniel.rocha@aluno.javageorge.com", 
                "Rua G, 404", "(11) 32109-8765", "MAT004", LocalDate.of(2022, 8, 1));

        Aluno a5 = new Aluno(null, "Elena Santos", "89012345678", "elena.santos@aluno.javageorge.com", 
                "Rua H, 505", "(11) 21098-7654", "MAT005", LocalDate.of(2022, 8, 1));

        alunos.add(alunoRepository.save(a1));
        alunos.add(alunoRepository.save(a2));
        alunos.add(alunoRepository.save(a3));
        alunos.add(alunoRepository.save(a4));
        alunos.add(alunoRepository.save(a5));

        return alunos;
    }

    private List<Turma> criarTurmas(List<Disciplina> disciplinas, List<Professor> professores) {
        List<Turma> turmas = new ArrayList<>();

        // Turma de Programação I
        Turma t1 = new Turma();
        t1.setDisciplina(disciplinas.get(0));
        t1.setProfessor(professores.get(0));
        t1.setPeriodo("2023.1");
        t1.setLimiteAlunos(30);
        t1.setDataInicio(LocalDate.of(2023, 2, 1));
        t1.setDataFim(LocalDate.of(2023, 6, 30));

        // Turma de Programação II
        Turma t2 = new Turma();
        t2.setDisciplina(disciplinas.get(1));
        t2.setProfessor(professores.get(0));
        t2.setPeriodo("2023.1");
        t2.setLimiteAlunos(25);
        t2.setDataInicio(LocalDate.of(2023, 2, 1));
        t2.setDataFim(LocalDate.of(2023, 6, 30));

        // Turma de Álgebra Linear
        Turma t3 = new Turma();
        t3.setDisciplina(disciplinas.get(3));
        t3.setProfessor(professores.get(1));
        t3.setPeriodo("2023.1");
        t3.setLimiteAlunos(40);
        t3.setDataInicio(LocalDate.of(2023, 2, 1));
        t3.setDataFim(LocalDate.of(2023, 6, 30));

        // Turma de Cálculo I
        Turma t4 = new Turma();
        t4.setDisciplina(disciplinas.get(4));
        t4.setProfessor(professores.get(1));
        t4.setPeriodo("2023.1");
        t4.setLimiteAlunos(35);
        t4.setDataInicio(LocalDate.of(2023, 2, 1));
        t4.setDataFim(LocalDate.of(2023, 6, 30));

        // Persistir turmas
        turmas.add(t1);
        turmas.add(t2);
        turmas.add(t3);
        turmas.add(t4);

        // Persistir as turmas usando o repositório de turmas
        turmas = turmaRepository.saveAll(turmas);

        // Atualizar os professores com as turmas persistidas
        for (Turma turma : turmas) {
            Professor professor = turma.getProfessor();
            // Já existe associação bidirecional, não é necessário atualizar manualmente
        }

        return turmas;
    }

    private void matricularAlunos(List<Aluno> alunos, List<Turma> turmas) {
        // Todos os alunos matriculados em Programação I
        for (Aluno aluno : alunos) {
            try {
                aluno.realizarMatricula(turmas.get(0));
            } catch (Exception e) {
                System.err.println("Erro ao matricular aluno: " + e.getMessage());
            }
        }

        // Primeiros 3 alunos matriculados em Álgebra Linear
        for (int i = 0; i < 3; i++) {
            try {
                alunos.get(i).realizarMatricula(turmas.get(2));
            } catch (Exception e) {
                System.err.println("Erro ao matricular aluno: " + e.getMessage());
            }
        }

        // Últimos 3 alunos matriculados em Cálculo I
        for (int i = alunos.size() - 3; i < alunos.size(); i++) {
            try {
                alunos.get(i).realizarMatricula(turmas.get(3));
            } catch (Exception e) {
                System.err.println("Erro ao matricular aluno: " + e.getMessage());
            }
        }

        // Persistir alunos
        alunoRepository.saveAll(alunos);
    }

    private List<Prova> cadastrarProvas(List<Turma> turmas, List<Professor> professores) {
        List<Prova> provas = new ArrayList<>();

        // Cadastrar provas para a turma de Programação I
        try {
            Prova p1 = professores.get(0).cadastrarProva(turmas.get(0), LocalDate.now().plusDays(30), 0.3);
            Prova p2 = professores.get(0).cadastrarProva(turmas.get(0), LocalDate.now().plusDays(60), 0.3);
            Prova p3 = professores.get(0).cadastrarProva(turmas.get(0), LocalDate.now().plusDays(90), 0.4);

            provas.add(p1);
            provas.add(p2);
            provas.add(p3);
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar provas: " + e.getMessage());
        }

        // Cadastrar provas para a turma de Álgebra Linear
        try {
            Prova p4 = professores.get(1).cadastrarProva(turmas.get(2), LocalDate.now().plusDays(35), 0.5);
            Prova p5 = professores.get(1).cadastrarProva(turmas.get(2), LocalDate.now().plusDays(85), 0.5);

            provas.add(p4);
            provas.add(p5);
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar provas: " + e.getMessage());
        }

        return provas;
    }

    private void lancarNotas(List<Turma> turmas, List<Prova> provas, List<Professor> professores) {
        // Como as provas são para o futuro, não vamos lançar notas agora
        // Mas poderíamos fazer algo como:
        /*
        if (!provas.isEmpty() && provas.get(0).getTurma().getMatriculas().size() > 0) {
            try {
                Matricula matricula = provas.get(0).getTurma().getMatriculas().get(0);
                professores.get(0).lancarNota(matricula, provas.get(0), 8.5);
            } catch (Exception e) {
                System.err.println("Erro ao lançar nota: " + e.getMessage());
            }
        }
        */
    }
}
