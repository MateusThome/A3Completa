package gui.TelaAluno;

import gui.TelaAlunosDaTurma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class TelaAluno extends JFrame {

    private JPanel MainPanelTelaAluno;
    private JList<String> list1;
    private JButton btnSairTelaAluno;

    private final Map<String, JFrame> janelasTurmasAbertas = new HashMap<>();

    public TelaAluno() {
        setTitle("Área do Aluno");
        setContentPane(MainPanelTelaAluno);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(450, 300);
        setMinimumSize(new Dimension(450, 300)); // <-- Adicionado aqui
        setLocationRelativeTo(null);

        // Dados de exemplo nas turmas
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        modeloLista.addElement("Matemática");
        modeloLista.addElement("História");
        modeloLista.addElement("Física");
        modeloLista.addElement("Geografia");
        modeloLista.addElement("Química");
        list1.setModel(modeloLista);

        // Evento de duplo clique para abrir alunos da turma
        list1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String turmaSelecionada = list1.getSelectedValue();
                    if (turmaSelecionada != null) {
                        abrirJanelaTurma(turmaSelecionada);
                    }
                }
            }
        });

        // Botão sair
        btnSairTelaAluno.addActionListener(e -> dispose());
    }

    private void abrirJanelaTurma(String turma) {
        if (janelasTurmasAbertas.containsKey(turma)) {
            janelasTurmasAbertas.get(turma).toFront();
            return;
        }

        JFrame janela = new TelaAlunosDaTurma(turma, () -> janelasTurmasAbertas.remove(turma)).getFrame();
        janelasTurmasAbertas.put(turma, janela);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaAluno().setVisible(true));
    }
}
