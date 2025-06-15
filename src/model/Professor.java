package model;

public class Professor extends Usuario {
    private String materia;

    public Professor(String nome, String cpf, String email, String senha, String materia) {
        super(nome, cpf, email, senha);
        this.materia = materia;
    }

    public String getMateria() {
        return materia;
    }
}