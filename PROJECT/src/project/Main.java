package project;

import javax.swing.*;
import java.awt.*;

public class Main extends FrontEnd {

    public static PantallaDeCarga pantallaDeCargaInstancia;

    private final JLabel titleLabel = new JLabel("JAVA TICKET");
    private final JButton btnLogin = new JButton("Login");
    private final JButton btnCerrar = new JButton("Cerrar");
    private final String imagen = "/proyectocal/imagenes/prueba1.jpg";

    public Main() {
        FrameConFondo(this, cargarFondo(imagen));
        titulo1(titleLabel);
        JButton[] botones = {btnLogin, btnCerrar};
        layoutBtn(botones);
        acciones();
        transicionSuave.fadeIn(this);
    }

    public void acciones() {

        btnCerrar.addActionListener(e -> System.exit(0));

        btnLogin.addActionListener(e -> {
            if (Usuarios.validarUserVisual()) {

                pantallaDeCargaInstancia = new PantallaDeCarga();
                pantallaDeCargaInstancia.setVisible(true);
                transicionSuave.fadeOut(this, () -> new MenuFrame());

            }
        });
    }

    public static void main(String[] args) {
        Usuarios.users.add(new UsuarioAdmin("Ing. Erick Amaya", "admin", "supersecreto", 34));

        SwingUtilities.invokeLater(() -> {

            new Main();
        });
    }
}
