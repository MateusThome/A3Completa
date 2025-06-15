package gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaAlunosDaTurma extends JFrame {
    private JFrame frame;
    public JPanel panel1;
    private JList<String> list1;

    public TelaAlunosDaTurma(String nomeTurma, Runnable aoFechar) {
        frame = new JFrame("Turma: " + nomeTurma);
        frame.setSize(600, 400);  // Tamanho inicial
        frame.setMinimumSize(new Dimension(600, 400)); // Tamanho mínimo
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Simulação de dados da turma específica
        String[] colunas = {"Nome", "A1", "A2", "A3"};
        Object[][] dados = {
                {"Ana Souza", 8.5, 7.0, 9.0},
                {"Bruno Lima", 6.0, 7.5, 6.5},
                {"Carla Menezes", 9.5, 8.0, 9.2}
        };

        JTable tabela = new JTable(new DefaultTableModel(dados, colunas));
        JScrollPane scrollPane = new JScrollPane(tabela);

        frame.add(scrollPane, BorderLayout.CENTER);

        // Quando a janela for fechada, executa o callback
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                aoFechar.run();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                aoFechar.run(); // necessário para garantir remoção
            }
        });

        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }
}
