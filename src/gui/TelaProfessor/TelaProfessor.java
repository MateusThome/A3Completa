package gui.TelaProfessor;

import gui.TelaTurmasProfessor;
import gui.TelaUsuario.TelaUsuario;
import model.Professor;
import model.Turma;
import model.GerenciadorUsuarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class TelaProfessor extends JFrame {

    private JPanel MainPanelTelaProfessor;
    private JList<String> listTurmas;
    private JButton btnAdicionarTurmaTelaProfessor;
    private JButton btnExcluirTelaProfessor;
    private JButton btnEditarTelaProfessor;
    private JButton btnSairTelaProfessor;

    private DefaultListModel<String> modeloLista;
    private Professor professorLogado; // Para guardar o professor que logou
    private Map<String, JFrame> janelasTurmasAbertas = new HashMap<>(); // Para controlar janelas de turma

    // Construtor que recebe o objeto Professor logado
    public TelaProfessor(Professor professor) {
        this.professorLogado = professor; // Armazena o professor logado

        setTitle("Área do Professor: " + professor.getNome()); // Exibe o nome do professor
        setContentPane(MainPanelTelaProfessor);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 500);
        setMinimumSize(new Dimension(550, 450));
        setLocationRelativeTo(null);

        modeloLista = new DefaultListModel<>();
        listTurmas.setModel(modeloLista);

        // Carregar as turmas do professor logado ao iniciar a tela
        carregarTurmasDoProfessor();

        listTurmas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = listTurmas.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        String nomeTurmaSelecionada = modeloLista.getElementAt(index);

                        // Abre a tela da turma do professor
                        // Passa o nome da turma e um callback para quando a janela fechar
                        abrirJanelaTurma(nomeTurmaSelecionada);
                    }
                }
            }
        });

        btnAdicionarTurmaTelaProfessor.addActionListener(e -> {
            String nomeTurma = JOptionPane.showInputDialog(this, "Digite o nome da nova turma:");
            if (nomeTurma != null && !nomeTurma.trim().isEmpty()) {
                nomeTurma = nomeTurma.trim();
                // Verificar se a turma já existe para este professor
                boolean turmaExiste = false;
                for (Turma t : professorLogado.getTurmasCriadas()) {
                    if (t.getNome().equalsIgnoreCase(nomeTurma)) {
                        turmaExiste = true;
                        break;
                    }
                }

                if (!turmaExiste) {
                    Turma novaTurma = new Turma(nomeTurma);
                    professorLogado.adicionarTurma(novaTurma); // Adiciona a turma ao objeto Professor
                    modeloLista.addElement(nomeTurma); // Adiciona à JList
                    JOptionPane.showMessageDialog(this, "Turma '" + nomeTurma + "' adicionada.");
                } else {
                    JOptionPane.showMessageDialog(this, "Turma com este nome já existe para você.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnEditarTelaProfessor.addActionListener(e -> {
            int index = listTurmas.getSelectedIndex();
            if (index != -1) {
                String nomeAtual = modeloLista.getElementAt(index);
                String novoNome = JOptionPane.showInputDialog(this, "Editar nome da turma:", nomeAtual);
                if (novoNome != null && !novoNome.trim().isEmpty()) {
                    novoNome = novoNome.trim();
                    // Encontrar a turma real no objeto professor e atualizar o nome
                    Turma turmaParaEditar = null;
                    for (Turma t : professorLogado.getTurmasCriadas()) {
                        if (t.getNome().equalsIgnoreCase(nomeAtual)) {
                            turmaParaEditar = t;
                            break;
                        }
                    }
                    if (turmaParaEditar != null) {
                        turmaParaEditar.setNome(novoNome); // Usa o setter da Turma
                        modeloLista.setElementAt(novoNome, index);
                        JOptionPane.showMessageDialog(this, "Turma renomeada para '" + novoNome + "'.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro: Turma não encontrada para edição (interno).", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma turma para editar.");
            }
        });

        btnExcluirTelaProfessor.addActionListener(e -> {
            int index = listTurmas.getSelectedIndex();
            if (index != -1) {
                int confirm = JOptionPane.showOptionDialog(this,
                        "Tem certeza que deseja excluir esta turma? Isso removerá todos os alunos dela.",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Sim", "Não"},
                        "Não");
                if (confirm == JOptionPane.YES_OPTION) {
                    String nomeTurmaParaRemover = modeloLista.getElementAt(index);
                    // Remover a turma também do objeto Professor
                    // É importante remover do objeto professor para que não apareça novamente
                    professorLogado.getTurmasCriadas().removeIf(t -> t.getNome().equalsIgnoreCase(nomeTurmaParaRemover));
                    modeloLista.removeElementAt(index);
                    JOptionPane.showMessageDialog(this, "Turma '" + nomeTurmaParaRemover + "' excluída.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma turma para excluir.");
            }
        });

        btnSairTelaProfessor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a tela atual
                // Fechar todas as janelas de turmas abertas por este professor
                for (JFrame janela : janelasTurmasAbertas.values()) {
                    janela.dispose();
                }
                new TelaUsuario().setVisible(true); // Retorna para a tela de login
            }
        });
    }

    // Método para carregar as turmas do professor logado na JList
    private void carregarTurmasDoProfessor() {
        modeloLista.clear(); // Limpa qualquer dado anterior
        for (Turma turma : professorLogado.getTurmasCriadas()) {
            modeloLista.addElement(turma.getNome());
        }
    }

    private void abrirJanelaTurma(String nomeTurma) {
        if (janelasTurmasAbertas.containsKey(nomeTurma)) {
            janelasTurmasAbertas.get(nomeTurma).toFront();
            return;
        }

        // Buscar a turma real do professor
        Turma turmaSelecionada = null;
        for (Turma t : professorLogado.getTurmasCriadas()) {
            if (t.getNome().equalsIgnoreCase(nomeTurma)) {
                turmaSelecionada = t;
                break;
            }
        }

        if (turmaSelecionada != null) {
            JFrame janela = new TelaTurmasProfessor(turmaSelecionada, () -> janelasTurmasAbertas.remove(nomeTurma)).getFrame();
            janelasTurmasAbertas.put(nomeTurma, janela);
        } else {
            JOptionPane.showMessageDialog(this, "Turma não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}