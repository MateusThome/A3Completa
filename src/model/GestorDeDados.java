package model;

import java.util.ArrayList;

public class GestorDeDados {
    public static ArrayList<Usuario> usuarios = new ArrayList<>();
    public static ArrayList<Turma> turmas = new ArrayList<>();

    static {
        usuarios.add(new Aluno("Aluno Exemplo", "11111111111", "aluno1@email.com", "123"));
        usuarios.add(new Professor("Professor Exemplo", "22222222222", "professor1@email.com", "123", "Matematica"));
    }

    public static Usuario validarLogin(String email, String senha) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email) && u.getSenha().equals(senha)) {
                return u;
            }
        }
        return null;
    }
}