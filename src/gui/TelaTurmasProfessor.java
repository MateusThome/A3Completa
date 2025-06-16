package gui;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import model.Aluno;
import model.Turma;
import model.GerenciadorUsuarios;

public class TelaTurmasProfessor extends JFrame {
    private JFrame frame;
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private JTextField tfNomeAluno;
    private JTextField tfEmailAluno;
    private JButton btnAdicionarAluno;
    private JButton btnZerarNota;
    private JButton btnRemoverAluno;

    private Turma turmaAtual;

    public TelaTurmasProfessor(Turma turma, Runnable aoFechar) {
        this.turmaAtual = turma;

        frame = new JFrame("Gerenciar Turma: " + turma.getNome());
        frame.setSize(700, 500);
        frame.setMinimumSize(new Dimension(700, 500));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        String[] colunas = {"Nome", "Email", "A1", "A2", "A3"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Apenas as colunas de notas (A1, A2, A3) são editáveis
                return column >= 2 && column <= 4;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0 || columnIndex == 1) return String.class;
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
        btnAdicionarAluno = new JButton("Adicionar Aluno à Turma");
        painelAdicionar.add(btnAdicionarAluno);

        frame.add(painelAdicionar, BorderLayout.NORTH);

        // Painel inferior com botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnZerarNota = new JButton("Zerar Nota Selecionada");
        btnRemoverAluno = new JButton("Remover Aluno da Turma");
        painelBotoes.add(btnZerarNota);
        painelBotoes.add(btnRemoverAluno);
        frame.add(painelBotoes, BorderLayout.SOUTH);

        // Inicializar a tabela com os alunos e suas notas da turma atual
        carregarAlunosENotasDaTurma();

        // Listener para a edição de células (notas)
        modeloTabela.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int col = e.getColumn();
                if (col >= 2 && col <= 4) { // Se uma coluna de nota foi editada
                    try {
                        String emailAluno = modeloTabela.getValueAt(row, 1).toString();
                        String nomeAluno = modeloTabela.getValueAt(row, 0).toString(); // Para buscar o Aluno
                        Aluno alunoParaAtualizar = GerenciadorUsuarios.buscarAlunoPorNomeEEmail(nomeAluno, emailAluno);

                        if (alunoParaAtualizar != null) {
                            List<Double> notasAtuais = turmaAtual.getNotasAluno(alunoParaAtualizar);
                            // Garante que a lista de notas tem tamanho suficiente para A1, A2, A3
                            while(notasAtuais.size() < 3) notasAtuais.add(0.0);

                            Double novaNota = (Double) modeloTabela.getValueAt(row, col);
                            notasAtuais.set(col - 2, novaNota); // col-2 para mapear A1->0, A2->1, A3->2
                            turmaAtual.setNotasAluno(alunoParaAtualizar, notasAtuais);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Por favor, insira um número válido para a nota (ex: 8.5).", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                        // Você pode adicionar lógica para reverter o valor na tabela aqui se desejar
                        // Por simplicidade, apenas avisa.
                    } catch (ClassCastException ex) {
                        JOptionPane.showMessageDialog(frame, "Formato de nota inválido. Use números.", "Erro de Tipo", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        btnAdicionarAluno.addActionListener(e -> {
            String nome = tfNomeAluno.getText().trim();
            String email = tfEmailAluno.getText().trim();

            if (nome.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Preencha nome e email do aluno!");
                return;
            }

            // 1. Verificar se o aluno já está na tabela desta turma (pelo email)
            for (int i = 0; i < modeloTabela.getRowCount(); i++) {
                if (modeloTabela.getValueAt(i, 1).toString().equalsIgnoreCase(email)) {
                    JOptionPane.showMessageDialog(frame, "Aluno com este email já está nesta turma!");
                    return;
                }
            }

            // 2. Validar se o aluno existe no GerenciadorUsuarios (cadastro global)
            Aluno alunoEncontrado = GerenciadorUsuarios.buscarAlunoPorNomeEEmail(nome, email);

            if (alunoEncontrado != null) {
                // Aluno válido encontrado no cadastro global e não está na turma
                turmaAtual.adicionarAluno(alunoEncontrado); // Adiciona ao objeto Turma e inicializa notas (0.0,0.0,0.0)
                modeloTabela.addRow(new Object[]{alunoEncontrado.getNome(), alunoEncontrado.getEmail(), 0.0, 0.0, 0.0});
                tfNomeAluno.setText("");
                tfEmailAluno.setText("");
                JOptionPane.showMessageDialog(frame, "Aluno " + nome + " adicionado à turma.");
            } else {
                JOptionPane.showMessageDialog(frame, "Aluno não encontrado ou dados incorretos no cadastro de usuários (nome e email devem bater).", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnZerarNota.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            int col = tabela.getSelectedColumn();
            if (row == -1 || col == -1) {
                JOptionPane.showMessageDialog(frame, "Selecione uma nota para zerar!");
                return;
            }
            if (col < 2) { // Não pode zerar nome ou email
                JOptionPane.showMessageDialog(frame, "Selecione uma nota (coluna A1, A2 ou A3) para zerar!");
                return;
            }
            modeloTabela.setValueAt(0.0, row, col); // Zera visualmente

            // Atualiza no modelo da Turma
            String emailAluno = modeloTabela.getValueAt(row, 1).toString();
            String nomeAluno = modeloTabela.getValueAt(row, 0).toString();
            Aluno alunoParaAtualizar = GerenciadorUsuarios.buscarAlunoPorNomeEEmail(nomeAluno, emailAluno);
            if (alunoParaAtualizar != null) {
                List<Double> notasAtuais = turmaAtual.getNotasAluno(alunoParaAtualizar);
                // Garante que a lista tem 3 elementos
                while(notasAtuais.size() < 3) {
                    notasAtuais.add(0.0);
                }
                notasAtuais.set(col - 2, 0.0);
                turmaAtual.setNotasAluno(alunoParaAtualizar, notasAtuais);
            }
        });

        btnRemoverAluno.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(frame, "Selecione um aluno para remover!");
                return;
            }
            String emailAluno = modeloTabela.getValueAt(row, 1).toString();
            String nomeAluno = modeloTabela.getValueAt(row, 0).toString(); // Pegar nome para buscar
            int confirm = JOptionPane.showOptionDialog(frame,
                    "Tem certeza que deseja remover este aluno da turma?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{"Sim", "Não"},
                    "Não");
            if (confirm == JOptionPane.YES_OPTION) {
                // Buscar o objeto Aluno completo
                Aluno alunoParaRemover = GerenciadorUsuarios.buscarAlunoPorNomeEEmail(nomeAluno, emailAluno);
                if (alunoParaRemover != null) {
                    // Remover do objeto Turma (lista de alunos)
                    boolean removedFromTurma = turmaAtual.getAlunosNaTurma().remove(alunoParaRemover);
                    if (removedFromTurma) {
                        // Remover as notas do mapa de notas da turma também
                        turmaAtual.getMapDeNotasPorAluno().remove(alunoParaRemover); // CORREÇÃO AQUI
                        // Remover a turma da lista de turmas do aluno
                        alunoParaRemover.getTurmas().remove(turmaAtual); // Importante para a visão do aluno

                        modeloTabela.removeRow(row);
                        JOptionPane.showMessageDialog(frame, "Aluno removido da turma com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Erro: Aluno não encontrado na lista de alunos da turma (inconsistência interna).");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Erro: Aluno não encontrado no cadastro global (inconsistência interna).");
                }
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                aoFechar.run();
            }
        });

        frame.setVisible(true);
    }

    // Método para carregar os alunos da turma no modelo da tabela, incluindo as notas
    private void carregarAlunosENotasDaTurma() {
        modeloTabela.setRowCount(0); // Limpa as linhas existentes
        for (Aluno aluno : turmaAtual.getAlunosNaTurma()) {
            List<Double> notas = turmaAtual.getNotasAluno(aluno);
            // Garante que a lista de notas tem 3 elementos, preenchendo com 0.0 se for menor
            while (notas.size() < 3) {
                notas.add(0.0);
            }
            modeloTabela.addRow(new Object[]{aluno.getNome(), aluno.getEmail(), notas.get(0), notas.get(1), notas.get(2)});
        }
    }

    public JFrame getFrame() {
        return frame;
    }
}