package gui.TelaNovoUsuario;

import gui.TelaUsuario.TelaUsuario;

import javax.swing.*;
import java.awt.*;

public class TelaNovoUsuario extends JFrame {
    private JPanel MainPanelNovoUsuario;
    private JPanel jpEsquedaTelaMenuNovo;
    private JButton button1;
    private JTextField tfNome;
    private JTextField tfEmail;
    private JPasswordField pfSenha;
    private JComboBox cbTipoUsuario;
    private JButton btnNUsuario;
    private JButton btnNovoUsuario;
    private JButton btnCadUsuario;
    private JPanel jpTelaNovoUsuarioBemVindo;
    private JButton btnTelaNovoUsuario;

    public TelaNovoUsuario() {
        setTitle("Novo Usuário");
        setSize(480, 430); // Tamanho fixo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Impede redimensionamento
        setLocationRelativeTo(null); // Centraliza na tela
        jpEsquedaTelaMenuNovo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(29, 29, 29)));


        //personalizando btn
        btnTelaNovoUsuario.setBackground(Color.getHSBColor(159,77,21));
        btnNovoUsuario.setBackground(Color.getHSBColor(159,77,21));
        btnNovoUsuario.setOpaque(false);
        btnNovoUsuario.setBorderPainted(false);
        btnNovoUsuario.setFocusPainted(false);
        btnTelaNovoUsuario.setOpaque(false);
        btnTelaNovoUsuario.setBorderPainted(false);
        btnTelaNovoUsuario.setFocusPainted(false);// opcional, remove a borda padrão

        // Cria a fonte personalizada (SansSerif, negrito, tamanho 15)
        Font fonteNegrito15 = new Font("SansSerif", Font.BOLD, 15);

        // Aplica a fonte a todos os componentes da interface
        aplicarFonte(MainPanelNovoUsuario, fonteNegrito15);

        setContentPane(MainPanelNovoUsuario); // Mostra o painel da GUI
        setVisible(true);

        btnTelaNovoUsuario.addActionListener(e -> {
            TelaUsuario novaTela = new TelaUsuario();
            novaTela.setVisible(true);  // abre a nova tela
            this.dispose();             // fecha a tela atual
        });

    }

    private void aplicarFonte(Component componente, Font fonte) {
        componente.setFont(fonte); // Aplica a fonte no componente atual

        // Se o componente pode conter outros (como JPanel), entra nele
        if (componente instanceof Container) {
            for (Component filho : ((Container) componente).getComponents()) {
                aplicarFonte(filho, fonte); // Aplica recursivamente nos filhos
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaNovoUsuario());
    }
}
