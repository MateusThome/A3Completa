package gui.TelaAluno;

import gui.TelaAlunosDaTurma; // Esta tela será ajustada para o aluno ver suas notas
import gui.TelaUsuario.TelaUsuario;
import model.Aluno; // Importar Aluno
import model.Turma; // Importar Turma

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays; // Necessário para Arrays.asList no main de teste
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class TelaAluno extends JFrame {

    private JPanel MainPanelTelaAluno;
    private JList<String> listTurmasAluno; // Renomeado para clareza
    private JButton btnSairTelaAluno;

    private final Map<String, JFrame> janelasTurmasAbertas = new HashMap<>();
    private Aluno alunoLogado; // Para guardar o aluno que logou

    // Construtor que recebe o objeto Aluno logado
    public TelaAluno(Aluno aluno) {
        this.alunoLogado = aluno; // Armazena o aluno logado

        setTitle("Área do Aluno: " + aluno.getNome());
        setContentPane(MainPanelTelaAluno);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(450, 300);
        setMinimumSize(new Dimension(450, 300));
        setLocationRelativeTo(null);

        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        listTurmasAluno.setModel(modeloLista); // Usa o novo nome

        // Carregar as turmas do aluno logado
        carregarTurmasDoAluno();

        // Evento de duplo clique para abrir a tela de notas da turma para o aluno
        listTurmasAluno.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = listTurmasAluno.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        String nomeTurmaSelecionada = modeloLista.getElementAt(index);
                        abrirJanelaNotasDaTurmaParaAluno(nomeTurmaSelecionada);
                    }
                }
            }
        });

        // Botão sair
        btnSairTelaAluno.addActionListener(e -> {
            dispose();
            for (JFrame janela : janelasTurmasAbertas.values()) {
                janela.dispose(); // Fecha todas as janelas de turma abertas pelo aluno
            }
            new TelaUsuario().setVisible(true); // Retorna para a tela de login
        });
    }

    // Método para carregar as turmas do aluno logado na JList
    private void carregarTurmasDoAluno() {
        DefaultListModel<String> modelo = (DefaultListModel<String>) listTurmasAluno.getModel();
        modelo.clear();
        for (Turma turma : alunoLogado.getTurmas()) {
            modelo.addElement(turma.getNome());
        }
    }

    private void abrirJanelaNotasDaTurmaParaAluno(String nomeTurma) {
        if (janelasTurmasAbertas.containsKey(nomeTurma)) {
            janelasTurmasAbertas.get(nomeTurma).toFront();
            return;
        }

        Turma turmaSelecionada = null;
        for (Turma t : alunoLogado.getTurmas()) { // Buscar a turma entre as turmas do aluno
            if (t.getNome().equalsIgnoreCase(nomeTurma)) {
                turmaSelecionada = t;
                break;
            }
        }

        if (turmaSelecionada != null) {
            // Passa o objeto Turma e o Aluno logado para a nova tela (TelaAlunosDaTurma como visão do aluno)
            JFrame janela = new TelaAlunosDaTurma(alunoLogado, turmaSelecionada, () -> janelasTurmasAbertas.remove(nomeTurma)).getFrame();
            janelasTurmasAbertas.put(nomeTurma, janela);
        } else {
            JOptionPane.showMessageDialog(this, "Erro: Turma não encontrada para o aluno.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método main para teste (opcional, pode ser removido ao integrar)
    public static void main(String[] args) {
        // Exemplo de como você chamaria TelaAluno após o login
        // Isso simula um aluno já logado para fins de teste
        Aluno alunoTeste = new Aluno("Mateus Teste", "111.111.111-11", "mateus.teste@gmail.com", "senhaTeste");
        Turma turmaExemplo = new Turma("Engenharia de Software");
        turmaExemplo.adicionarAluno(alunoTeste); // Adiciona o aluno à turma (isso adiciona a turma ao aluno também)
        turmaExemplo.setNotasAluno(alunoTeste, Arrays.asList(8.5, 7.0, 9.0)); // Define as notas para o aluno nesta turma

        SwingUtilities.invokeLater(() -> new TelaAluno(alunoTeste).setVisible(true));
    }
}