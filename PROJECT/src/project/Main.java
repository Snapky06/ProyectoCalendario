package project;

import javax.swing.*;

public class Main extends FrontEnd {
    private final JLabel titleLabel = new JLabel("JAVA TICKET");
    private final JButton btnLogin = new JButton("Login");
    private final JButton btnCerrar = new JButton("Cerrar");
    private final String imagen = "/proyectocal/imagenes/fondoNeg.png";

    public Main() {
        FrameConFondo(this, cargarFondo(imagen));
        transicionSuave.fadeIn(this);
        titulo1(titleLabel);
        JButton[] botones = {btnLogin, btnCerrar};
        layoutBtn(botones);
        acciones();
        this.setVisible(true);
    }

    public void acciones() {
        btnCerrar.addActionListener(e -> System.exit(0));
        btnLogin.addActionListener(e -> {
            if (Usuarios.validarUserVisual()) {
                    transicionSuave.fadeOut(this, () -> new MenuFrame());
            }
        });
    }

    public static void main(String[] args) {
        Usuarios.users.add(new UsuarioAdmin("Administrador del Sistema", "admin", "supersecreto", 30));
        SwingUtilities.invokeLater(Main::new);
    }
}