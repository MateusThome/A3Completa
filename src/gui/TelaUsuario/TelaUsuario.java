package gui.TelaUsuario;

import gui.TelaNovoUsuario.TelaNovoUsuario;

import javax.swing.*;
import java.awt.*;

public class TelaUsuario extends JFrame {
    private JButton btEntrarUsuario;
    private JTextField tfEmail;
    private JPanel MainPanelUsuario;
    private JPanel jpBtnEntrar;
    private JPanel jpEsquedaTelaMenu;
    private JPasswordField psSenhaUsuario;
    private JComboBox comboBox1;
    private JButton btnNovoUsuario;
    private JButton btnUsuario;
    private JPanel jpBemVindo;

    public TelaUsuario() {
        setTitle("Usuário");
        setSize(480, 430); // Tamanho fixo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Impede redimensionamento
        setLocationRelativeTo(null); // Centraliza na tela
        jpEsquedaTelaMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(29, 29, 29)));


        //personalizando btn
        btnUsuario.setBackground(Color.getHSBColor(159,77,21));
        btnNovoUsuario.setOpaque(false);
        btnNovoUsuario.setBorderPainted(false);
        btnNovoUsuario.setFocusPainted(false);
        btnUsuario.setOpaque(false);
        btnUsuario.setBorderPainted(false);
        btnUsuario.setFocusPainted(false);// opcional, remove a borda padrão

        // Cria a fonte personalizada (SansSerif, negrito, tamanho 15)
        Font fonteNegrito15 = new Font("SansSerif", Font.BOLD, 15);

        // Aplica a fonte a todos os componentes da interface
        aplicarFonte(MainPanelUsuario, fonteNegrito15);

        setContentPane(MainPanelUsuario); // Mostra o painel da GUI
        setVisible(true);

        btnNovoUsuario.addActionListener(e -> {
            TelaNovoUsuario novaTela = new TelaNovoUsuario();
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
        SwingUtilities.invokeLater(() -> new TelaUsuario());
    }
}
