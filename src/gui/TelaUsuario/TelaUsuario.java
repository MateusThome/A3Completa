package gui.TelaUsuario;

import gui.TelaAluno.TelaAluno; // Importação correta
import gui.TelaNovoUsuario.TelaNovoUsuario;
import gui.TelaProfessor.TelaProfessor; // Importação correta
import model.Aluno; // Importação correta
import model.Professor; // Importação correta
import model.Usuario; // Importação correta
import model.GerenciadorUsuarios;

import javax.swing.*;
import java.awt.*;

public class TelaUsuario extends JFrame {
    private JButton btEntrarUsuario;
    private JTextField tfEmail;
    private JPanel MainPanelUsuario;
    private JPanel jpBtnEntrar;
    private JPanel jpEsquedaTelaMenu;
    private JPasswordField psSenhaUsuario;
    private JComboBox<String> comboBox1; // Adicionado <String> para o ComboBox
    private JButton btnNovoUsuario;
    private JButton btnUsuario;
    private JPanel jpBemVindo;

    public TelaUsuario() {
        setTitle("Usuário");
        setSize(480, 430);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        jpEsquedaTelaMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(29, 29, 29)));

        //personalizando btn
        btnUsuario.setBackground(Color.getHSBColor(159,77,21));
        btnNovoUsuario.setOpaque(false);
        btnNovoUsuario.setBorderPainted(false);
        btnNovoUsuario.setFocusPainted(false);
        btnUsuario.setOpaque(false);
        btnUsuario.setBorderPainted(false);
        btnUsuario.setFocusPainted(false);

        // Adicionar tipos de usuário ao ComboBox
        comboBox1.addItem("Aluno");
        comboBox1.addItem("Professor");

        Font fonteNegrito15 = new Font("SansSerif", Font.BOLD, 15);
        aplicarFonte(MainPanelUsuario, fonteNegrito15);

        setContentPane(MainPanelUsuario);
        setVisible(true);

        btnNovoUsuario.addActionListener(e -> {
            TelaNovoUsuario novaTela = new TelaNovoUsuario();
            novaTela.setVisible(true);
            this.dispose();
        });

        btEntrarUsuario.addActionListener(e -> {
            String email = tfEmail.getText();
            String senha = new String(psSenhaUsuario.getPassword());
            String tipoSelecionado = (String) comboBox1.getSelectedItem();

            Usuario usuarioLogado = GerenciadorUsuarios.validarUsuario(email, senha, tipoSelecionado);

            if (usuarioLogado != null) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                // Direcionar para a tela apropriada usando instanceof
                if (usuarioLogado instanceof Aluno) {
                    new TelaAluno((Aluno) usuarioLogado).setVisible(true); // CORREÇÃO AQUI
                } else if (usuarioLogado instanceof Professor) {
                    new TelaProfessor((Professor) usuarioLogado).setVisible(true); // Passa o objeto Professor
                }
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuário não cadastrado ou credenciais inválidas!", "Erro de Login", JOptionPane.ERROR_MESSAGE);
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
        SwingUtilities.invokeLater(() -> new TelaUsuario());
    }
}