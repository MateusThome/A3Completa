package model;

import java.util.ArrayList;

public class Aluno extends Usuario {
    private ArrayList<Turma> turmas = new ArrayList<>();

    public Aluno(String nome, String cpf, String email, String senha) {
        super(nome, cpf, email, senha);
    }

    public ArrayList<Turma> getTurmas() {
        return turmas;
    }
}