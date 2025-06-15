
package main;
import gui.TelaUsuario.TelaUsuario;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new TelaUsuario().setVisible(true);
        });
    }
}