package model;

import model.Aluno;
import model.Professor;
import model.Turma;
import model.Usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GerenciadorUsuarios {
    private static final List<Usuario> usuariosCadastrados = new ArrayList<>();

    // Bloco estático para inicializar usuários e dados
    static {
        // Criar Aluno "mateus"
        Aluno alunoMateus = new Aluno("mateus", "999.999.999-99", "mateus@gmail.com", "932");
        usuariosCadastrados.add(alunoMateus);

        // Criar Aluno "guilherme" (para a turma)
        Aluno alunoGuilherme = new Aluno("guilherme", "888.888.888-88", "guilherme@email.com", "senhaGuilherme");
        usuariosCadastrados.add(alunoGuilherme);

        // Criar Aluno "murilo" (para a turma)
        Aluno alunoMurilo = new Aluno("murilo", "777.777.777-77", "murilo@email.com", "senhaMurilo");
        usuariosCadastrados.add(alunoMurilo);

        // Criar Professor "leonardo"
        Professor professorLeonardo = new Professor("leonardo", "666.666.666-66", "leonardo@gmail.com", "123", "Programação");
        usuariosCadastrados.add(professorLeonardo);

        // Criar a turma "PSCN"
        Turma turmaPSCN = new Turma("PSCN");

        // Adicionar alunos à turma. O método adicionarAluno já inicializa as notas como 0.0,0.0,0.0.
        // Em seguida, definimos as notas iniciais específicas.
        turmaPSCN.adicionarAluno(alunoMateus);
        turmaPSCN.setNotasAluno(alunoMateus, Arrays.asList(10.0, 10.0, 10.0));

        turmaPSCN.adicionarAluno(alunoGuilherme);
        turmaPSCN.setNotasAluno(alunoGuilherme, Arrays.asList(10.0, 10.0, 10.0));

        turmaPSCN.adicionarAluno(alunoMurilo);
        turmaPSCN.setNotasAluno(alunoMurilo, Arrays.asList(10.0, 10.0, 10.0));

        // Adicionar a turma ao professor Leonardo
        professorLeonardo.adicionarTurma(turmaPSCN);
    }

    /**
     * Cadastra um novo usuário (Aluno ou Professor).
     * @param usuario O objeto Usuario a ser cadastrado.
     * @return true se o cadastro foi bem-sucedido, false se o email já existe.
     */
    public static boolean cadastrarUsuario(Usuario usuario) {
        for (Usuario u : usuariosCadastrados) {
            if (u.getEmail().equalsIgnoreCase(usuario.getEmail())) {
                return false; // Email já cadastrado
            }
        }
        usuariosCadastrados.add(usuario);
        return true;
    }

    /**
     * Valida as credenciais de login de um usuário e verifica seu tipo.
     * @param email O email do usuário.
     * @param senha A senha do usuário.
     * @param tipoLogin O tipo esperado do usuário ("Aluno" ou "Professor") vindo da tela de login.
     * @return O objeto Usuario se as credenciais e o tipo forem válidos, null caso contrário.
     */
    public static Usuario validarUsuario(String email, String senha, String tipoLogin) {
        for (Usuario u : usuariosCadastrados) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getSenha().equals(senha)) {
                if (tipoLogin.equals("Aluno") && u instanceof Aluno) {
                    return u;
                } else if (tipoLogin.equals("Professor") && u instanceof Professor) {
                    return u;
                }
            }
        }
        return null;
    }

    /**
     * Retorna a lista de todos os usuários cadastrados.
     * @return Uma lista imutável de usuários.
     */
    public static List<Usuario> getUsuariosCadastrados() {
        return new ArrayList<>(usuariosCadastrados); // Retorna uma cópia para evitar modificações externas diretas
    }

    /**
     * Busca um aluno pelo nome e email.
     * @param nome O nome do aluno.
     * @param email O email do aluno.
     * @return O objeto Aluno se encontrado, null caso contrário.
     */
    public static Aluno buscarAlunoPorNomeEEmail(String nome, String email) {
        for (Usuario u : usuariosCadastrados) {
            if (u instanceof Aluno && u.getNome().equalsIgnoreCase(nome) && u.getEmail().equalsIgnoreCase(email)) {
                return (Aluno) u;
            }
        }
        return null;
    }

    /**
     * Busca um professor pelo email.
     * @param email O email do professor.
     * @return O objeto Professor se encontrado, null caso contrário.
     */
    public static Professor buscarProfessorPorEmail(String email) {
        for (Usuario u : usuariosCadastrados) {
            if (u instanceof Professor && u.getEmail().equalsIgnoreCase(email)) {
                return (Professor) u;
            }
        }
        return null;
    }
}