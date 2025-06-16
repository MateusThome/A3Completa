package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Turma {
    private String nome;
    private List<Aluno> alunosNaTurma; // Lista de objetos Aluno
    private Map<Aluno, List<Double>> notasPorAluno; // Map para guardar as notas de cada aluno na turma

    public Turma(String nome) {
        this.nome = nome;
        this.alunosNaTurma = new ArrayList<>();
        this.notasPorAluno = new HashMap<>(); // Inicializa o mapa de notas
    }

    public String getNome() {
        return nome;
    }

    // Setter para o nome da turma (necessário para edição pelo professor)
    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Aluno> getAlunosNaTurma() {
        return alunosNaTurma;
    }

    public void adicionarAluno(Aluno aluno) {
        if (!alunosNaTurma.contains(aluno)) { // Evita duplicatas
            alunosNaTurma.add(aluno);
            // Ao adicionar um aluno à turma, inicializa suas notas como 0.0, 0.0, 0.0
            notasPorAluno.put(aluno, new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0)));
            aluno.adicionarTurma(this); // Adiciona esta turma ao objeto Aluno também
        }
    }

    // Novo método para obter as notas de um aluno específico na turma
    public List<Double> getNotasAluno(Aluno aluno) {
        // Retorna as notas do aluno ou uma lista vazia se não encontrar
        return notasPorAluno.getOrDefault(aluno, new ArrayList<>());
    }

    // Novo método para definir/atualizar as notas de um aluno específico na turma
    public void setNotasAluno(Aluno aluno, List<Double> notas) {
        if (alunosNaTurma.contains(aluno)) {
            // Cria uma nova lista para evitar referências diretas e garantir mutabilidade segura
            notasPorAluno.put(aluno, new ArrayList<>(notas));
        }
    }

    // NOVO: Getter para o mapa de notas completo (necessário para remover a entrada de um aluno)
    public Map<Aluno, List<Double>> getMapDeNotasPorAluno() {
        return notasPorAluno;
    }
}