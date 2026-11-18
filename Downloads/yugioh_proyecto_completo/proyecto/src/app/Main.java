package app;

import controlador.ControladorBatalla;
import vista.VistaBatalla;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            VistaBatalla vista = new VistaBatalla();
            new ControladorBatalla(vista);
            vista.setLocationRelativeTo(null);
            vista.setVisible(true);
        });
    }
}
