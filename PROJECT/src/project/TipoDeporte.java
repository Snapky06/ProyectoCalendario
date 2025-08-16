package project;

import javax.swing.JOptionPane;

public enum TipoDeporte {
    FUTBOL(11),
    TENIS(0), 
    RUGBY(15),
    BASEBALL(9);

    private final int tamanoEquipo;

    TipoDeporte(int tamanoEquipo) {
        this.tamanoEquipo = tamanoEquipo;
    }

    public int getTamanoEquipo() {
        if (this == TENIS) {
            String[] options = {"Singles", "Doubles"};
            int choice = JOptionPane.showOptionDialog(null, "Seleccione el tipo de partido para Tenis:", "Tipo de Partido de Tenis",
                                                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            return (choice == 0) ? 1 : 2;
        }
        return tamanoEquipo;
    }
}
