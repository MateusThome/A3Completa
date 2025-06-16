package model;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Usuario {
    private String materia;
    private List<Turma> turmasCriadas; // Lista de turmas que este professor gerencia

    public Professor(String nome, String cpf, String email, String senha, String materia) {
        super(nome, cpf, email, senha);
        this.materia = materia;
        this.turmasCriadas = new ArrayList<>();
    }

    public String getMateria() {
        return materia;
    }

    public List<Turma> getTurmasCriadas() {
        return turmasCriadas;
    }

    public void adicionarTurma(Turma turma) {
        if (!turmasCriadas.contains(turma)) { // Evita turmas duplicadas
            turmasCriadas.add(turma);
        }
    }
}