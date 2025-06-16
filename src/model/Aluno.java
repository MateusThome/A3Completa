package model;

import java.util.ArrayList;
import java.util.List; // Importar List

public class Aluno extends Usuario {
    private List<Turma> turmas = new ArrayList<>(); // Usar List em vez de ArrayList para tipo

    public Aluno(String nome, String cpf, String email, String senha) {
        super(nome, cpf, email, senha);
    }

    public List<Turma> getTurmas() { // Retornar List
        return turmas;
    }

    // Método para adicionar uma turma ao aluno (se ele for matriculado)
    public void adicionarTurma(Turma turma) {
        if (!this.turmas.contains(turma)) { // Evita duplicatas
            this.turmas.add(turma);
        }
    }
}