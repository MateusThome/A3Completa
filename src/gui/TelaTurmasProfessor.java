package gui;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TelaTurmasProfessor extends JFrame {
    private JFrame frame;
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private JTextField tfNomeAluno;
    private JTextField tfEmailAluno;
    private JButton btnAdicionarAluno;
    private JButton btnExcluirNota;
    private JButton btnExcluirAluno;

    // Estrutura interna para guardar email (que não aparece na tabela)
    private Map<String, String> emailPorAluno = new HashMap<>();

    public TelaTurmasProfessor(String nomeTurma, Runnable aoFechar) {
        frame = new JFrame("Turma: " + nomeTurma);
        frame.setSize(700, 500);
        frame.setMinimumSize(new Dimension(700, 500));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Colunas: Nome, A1, A2, A3
        String[] colunas = {"Nome", "A1", "A2", "A3"};
        Object[][] dados = {
                {"Ana Souza", 8.5, 7.0, 9.0},
        };

        // Preenche o mapa email por aluno (simulação)
        emailPorAluno.put("Ana Souza", "ana@email.com");

        modeloTabela = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; // Permite editar todas as células
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return String.class;
                else return Double.class;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tabela);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Painel para adicionar aluno
        JPanel painelAdicionar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelAdicionar.add(new JLabel("Nome do aluno:"));
        tfNomeAluno = new JTextField(10);
        painelAdicionar.add(tfNomeAluno);
        painelAdicionar.add(new JLabel("Email do aluno:"));
        tfEmailAluno = new JTextField(15);
        painelAdicionar.add(tfEmailAluno);
        btnAdicionarAluno = new JButton("Adicionar Aluno");
        painelAdicionar.add(btnAdicionarAluno);

        frame.add(painelAdicionar, BorderLayout.NORTH);

        // Painel inferior com botões excluir nota e aluno
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnExcluirNota = new JButton("Excluir Nota Selecionada");
        btnExcluirAluno = new JButton("Excluir Aluno (todas notas zeradas)");
        btnExcluirAluno.setEnabled(false); // começa desabilitado
        painelBotoes.add(btnExcluirNota);
        painelBotoes.add(btnExcluirAluno);
        frame.add(painelBotoes, BorderLayout.SOUTH);

        // Ações

        btnAdicionarAluno.addActionListener(e -> {
            String nome = tfNomeAluno.getText().trim();
            String email = tfEmailAluno.getText().trim();
            if (nome.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Preencha nome e email!");
                return;
            }
            // Verifica se aluno já existe pelo nome (simplificado)
            for (int i = 0; i < modeloTabela.getRowCount(); i++) {
                if (modeloTabela.getValueAt(i, 0).toString().equalsIgnoreCase(nome)) {
                    JOptionPane.showMessageDialog(frame, "Aluno já está na turma!");
                    return;
                }
            }
            modeloTabela.addRow(new Object[]{nome, 0.0, 0.0, 0.0});
            emailPorAluno.put(nome, email);
            tfNomeAluno.setText("");
            tfEmailAluno.setText("");
        });

        btnExcluirNota.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            int col = tabela.getSelectedColumn();
            if (row == -1 || col == -1) {
                JOptionPane.showMessageDialog(frame, "Selecione uma nota para excluir!");
                return;
            }
            if (col == 0) {
                JOptionPane.showMessageDialog(frame, "Selecione uma nota (coluna A1, A2 ou A3)!");
                return;
            }
            // Zera a nota selecionada
            modeloTabela.setValueAt(0.0, row, col);

            // Checa se todas as notas estão zeradas para habilitar excluir aluno
            if (todasNotasZeradas(row)) {
                btnExcluirAluno.setEnabled(true);
            } else {
                btnExcluirAluno.setEnabled(false);
            }
        });

        btnExcluirAluno.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(frame, "Selecione um aluno para excluir!");
                return;
            }
            if (!todasNotasZeradas(row)) {
                JOptionPane.showMessageDialog(frame, "Só pode excluir aluno com todas as notas zeradas!");
                return;
            }
            String nome = modeloTabela.getValueAt(row, 0).toString();
            modeloTabela.removeRow(row);
            emailPorAluno.remove(nome);
            btnExcluirAluno.setEnabled(false);
        });

        // Atualiza botão excluir aluno quando troca seleção
        tabela.getSelectionModel().addListSelectionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row == -1) {
                btnExcluirAluno.setEnabled(false);
            } else {
                btnExcluirAluno.setEnabled(todasNotasZeradas(row));
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                aoFechar.run();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                aoFechar.run();
            }
        });

        frame.setVisible(true);
    }

    private boolean todasNotasZeradas(int row) {
        for (int col = 1; col <= 3; col++) {
            Object val = modeloTabela.getValueAt(row, col);
            if (val instanceof Number) {
                if (((Number) val).doubleValue() != 0.0) {
                    return false;
                }
            }
        }
        return true;
    }

    public JFrame getFrame() {
        return frame;
    }
}
