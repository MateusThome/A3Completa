package gui.TelaProfessor;

import gui.TelaTurmasProfessor;
import gui.TelaUsuario.TelaUsuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaProfessor extends JFrame {

    private JPanel MainPanelTelaProfessor;
    private JList<String> listTurmas;
    private JButton btnAdicionarTurmaTelaProfessor;
    private JButton btnExcluirTelaProfessor;
    private JButton btnEditarTelaProfessor;
    private JButton btnSairTelaProfessor;

    private DefaultListModel<String> modeloLista;

    public TelaProfessor() {
        setTitle("Área do Professor");
        setContentPane(MainPanelTelaProfessor);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 500); // tamanho inicial
        setMinimumSize(new Dimension(550, 450)); // tamanho mínimo
        setLocationRelativeTo(null);

        // Criar o modelo de lista e vincular
        modeloLista = new DefaultListModel<>();
        listTurmas.setModel(modeloLista);

        listTurmas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {  // detecta double click
                    int index = listTurmas.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        String turmaSelecionada = listTurmas.getModel().getElementAt(index);

                        // Fecha a tela atual (se quiser)
                        // frame.dispose();

                        // Abre a tela do professor para essa turma
                        TelaTurmasProfessor telaTurma = new TelaTurmasProfessor(turmaSelecionada, () -> {
                            // Callback ao fechar TelaProfessorDaTurma, se quiser fazer algo
                            System.out.println("Tela da turma fechada");
                        });
                    }
                }
            }
        });

        // Ações dos botões

        btnAdicionarTurmaTelaProfessor.addActionListener(e -> {
            String nomeTurma = JOptionPane.showInputDialog(this, "Digite o nome da nova turma:");
            if (nomeTurma != null && !nomeTurma.trim().isEmpty()) {
                modeloLista.addElement(nomeTurma.trim());
            }
        });

        btnEditarTelaProfessor.addActionListener(e -> {
            int index = listTurmas.getSelectedIndex();
            if (index != -1) {
                String nomeAtual = modeloLista.getElementAt(index);
                String novoNome = JOptionPane.showInputDialog(this, "Editar nome da turma:", nomeAtual);
                if (novoNome != null && !novoNome.trim().isEmpty()) {
                    modeloLista.setElementAt(novoNome.trim(), index);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma turma para editar.");
            }
        });

        btnExcluirTelaProfessor.addActionListener(e -> {
            int index = listTurmas.getSelectedIndex();
            if (index != -1) {
                int confirm = JOptionPane.showOptionDialog(this,
                        "Tem certeza que deseja excluir esta turma?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Sim", "Não"},  // Botões personalizados
                        "Não"); // Botão padrão
                if (confirm == JOptionPane.YES_OPTION) {
                    modeloLista.removeElementAt(index);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma turma para excluir.");
            }
        });

        btnSairTelaProfessor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fecha a tela atual
                dispose();

                // Cria e exibe a nova tela
                TelaUsuario telaUsuario = new TelaUsuario();
                telaUsuario.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaProfessor().setVisible(true));
    }
}
