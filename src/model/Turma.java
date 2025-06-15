package model;

import java.util.ArrayList;

public class Turma {
    private String nome;
    private ArrayList<Aluno> alunos = new ArrayList<>();

    public Turma(String nome) {
        this.nome = nome;
    }

    public String getNome() { return nome; }
    public ArrayList<Aluno> getAlunos() { return alunos; }

    public void adicionarAluno(Aluno aluno) {
        alunos.add(aluno);
    }
}