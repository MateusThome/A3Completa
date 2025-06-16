package gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays; // Necessário para Arrays.asList (apenas se for simular, mas mantive por segurança)
import java.util.List;

import model.Aluno;
import model.Turma;

public class TelaAlunosDaTurma extends JFrame {
    private JFrame frame;
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private Aluno alunoLogado; // Para saber qual aluno está vendo as notas
    private Turma turmaVisualizada; // Para saber qual turma está sendo vista

    // Construtor para a visão do ALUNO
    public TelaAlunosDaTurma(Aluno aluno, Turma turma, Runnable aoFechar) {
        this.alunoLogado = aluno;
        this.turmaVisualizada = turma;

        frame = new JFrame("Minhas Notas em: " + turma.getNome());
        frame.setSize(400, 250); // Tamanho menor, pois é só para um aluno
        frame.setMinimumSize(new Dimension(400, 250));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        String[] colunas = {"Avaliação", "Nota"}; // Apenas duas colunas para o aluno
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Aluno não pode editar notas
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setRowSelectionAllowed(false); // Não permite selecionar linhas
        tabela.setColumnSelectionAllowed(false); // Não permite selecionar colunas
        tabela.getTableHeader().setReorderingAllowed(false); // Não permite reordenar colunas

        JScrollPane scrollPane = new JScrollPane(tabela);
        frame.add(scrollPane, BorderLayout.CENTER);

        carregarNotasDoAlunoNaTurma(); // Carrega as notas específicas do aluno

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                aoFechar.run(); // Executa o callback quando a janela é fechada
            }

            @Override
            public void windowClosing(WindowEvent e) {
                aoFechar.run(); // Executa o callback também ao clicar no X da janela
            }
        });

        frame.setVisible(true);
    }

    private void carregarNotasDoAlunoNaTurma() {
        modeloTabela.setRowCount(0); // Limpa as linhas existentes
        List<Double> notas = turmaVisualizada.getNotasAluno(alunoLogado);

        // Garante que sempre há 3 notas para exibir (pode ser 0.0 se não definidas)
        while(notas.size() < 3) {
            notas.add(0.0);
        }

        modeloTabela.addRow(new Object[]{"A1", notas.get(0)});
        modeloTabela.addRow(new Object[]{"A2", notas.get(1)});
        modeloTabela.addRow(new Object[]{"A3", notas.get(2)});
    }

    public JFrame getFrame() {
        return frame;
    }
}