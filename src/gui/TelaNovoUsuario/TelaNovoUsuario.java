package gui.TelaNovoUsuario;

import gui.TelaUsuario.TelaUsuario;
import model.Aluno;
import model.Professor;
import model.GerenciadorUsuarios;

import javax.swing.*;
import java.awt.*;

public class TelaNovoUsuario extends JFrame {
    private JPanel MainPanelNovoUsuario;
    private JPanel jpEsquedaTelaMenuNovo;
    private JButton button1;
    private JTextField tfNome;
    private JTextField tfEmail;
    private JPasswordField pfSenha;
    private JComboBox<String> cbTipoUsuario; // Adicionado <String>
    private JButton btnNUsuario;
    private JButton btnNovoUsuario;
    private JButton btnCadUsuario;
    private JPanel jpTelaNovoUsuarioBemVindo;
    private JButton btnTelaNovoUsuario;

    public TelaNovoUsuario() {
        setTitle("Novo Usuário");
        setSize(480, 430);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        jpEsquedaTelaMenuNovo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(29, 29, 29)));

        //personalizando btn
        btnTelaNovoUsuario.setBackground(Color.getHSBColor(159,77,21));
        btnNovoUsuario.setOpaque(false);
        btnNovoUsuario.setBorderPainted(false);
        btnNovoUsuario.setFocusPainted(false);
        btnTelaNovoUsuario.setOpaque(false);
        btnTelaNovoUsuario.setBorderPainted(false);
        btnTelaNovoUsuario.setFocusPainted(false);

        // Adicionar tipos de usuário ao ComboBox
        cbTipoUsuario.addItem("Aluno");
        cbTipoUsuario.addItem("Professor");

        Font fonteNegrito15 = new Font("SansSerif", Font.BOLD, 15);
        aplicarFonte(MainPanelNovoUsuario, fonteNegrito15);

        setContentPane(MainPanelNovoUsuario);
        setVisible(true);

        btnTelaNovoUsuario.addActionListener(e -> {
            TelaUsuario novaTela = new TelaUsuario();
            novaTela.setVisible(true);
            this.dispose();
        });

        btnCadUsuario.addActionListener(e -> {
            String nome = tfNome.getText();
            String email = tfEmail.getText();
            String senha = new String(pfSenha.getPassword());

            // Exemplo de CPF fixo. Considere adicionar um campo para isso na GUI.
            // Para um CPF único, você pode gerar um UUID ou ter uma lógica mais robusta.
            String cpf = "000.000.000-00"; // Simplificado para o exemplo

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos!", "Erro no Cadastro", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String tipoSelecionado = (String) cbTipoUsuario.getSelectedItem();
            boolean cadastrado = false;

            if (tipoSelecionado.equals("Aluno")) {
                Aluno novoAluno = new Aluno(nome, cpf, email, senha);
                cadastrado = GerenciadorUsuarios.cadastrarUsuario(novoAluno);
            } else if (tipoSelecionado.equals("Professor")) {
                String materia = JOptionPane.showInputDialog(this, "Digite a matéria do professor:");
                if (materia == null || materia.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "A matéria do professor não pode ser vazia.", "Erro no Cadastro", JOptionPane.WARNING_MESSAGE);
                    return; // Aborta o cadastro se a matéria for vazia
                }
                Professor novoProfessor = new Professor(nome, cpf, email, senha, materia);
                cadastrado = GerenciadorUsuarios.cadastrarUsuario(novoProfessor);
            }

            if (cadastrado) {
                JOptionPane.showMessageDialog(this, "Usuário Cadastrado com sucesso!", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
                tfNome.setText("");
                tfEmail.setText("");
                pfSenha.setText("");
                cbTipoUsuario.setSelectedItem("Aluno"); // Reseta para Aluno por padrão
            } else {
                JOptionPane.showMessageDialog(this, "Email já cadastrado! Tente outro email.", "Erro no Cadastro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void aplicarFonte(Component componente, Font fonte) {
        componente.setFont(fonte);
        if (componente instanceof Container) {
            for (Component filho : ((Container) componente).getComponents()) {
                aplicarFonte(filho, fonte);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaNovoUsuario());
    }
}