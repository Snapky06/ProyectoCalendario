package project;

import javax.swing.*;

public class MenuFrame extends FrontEnd {

    private JButton btnEventos = new JButton("Administración de Eventos");
    private JButton btnUsuarios = new JButton("Administración de Usuarios");
    private JButton btnReportes = new JButton("Reportes");
    private JButton btnSalir = new JButton("Cerrar Sesión");
    private JLabel tit = new JLabel("Menú Principal");
    private final String imagen = "/proyectocal/imagenes/prueba2.jpg";

    public MenuFrame() {
        FrameConFondo(this, cargarFondo(imagen));
        transicionSuave.fadeIn(this);
        titulo1(tit);
        JButton[] botones = {btnEventos, btnUsuarios, btnReportes, btnSalir};
        layoutBtn(botones);
        acciones();
        this.setVisible(true);
    }

    public void acciones() {
        btnUsuarios.addActionListener(e -> {
            if (Usuarios.esAdmin()) {
                transicionSuave.fadeOut(this, () -> new usuariosFrame());
            } else {
                JOptionPane.showMessageDialog(this, "Acceso denegado. Solo para administradores.");
            }
        });

        btnEventos.addActionListener(e -> {
            transicionSuave.fadeOut(this, () -> new EventosFrame());
        });

        btnReportes.addActionListener(e -> {
            transicionSuave.fadeOut(this, () -> new ReportesFrame());
        });

        btnSalir.addActionListener(e -> {
            Usuarios.usuarioLogeado = null;
        transicionSuave.fadeOut(this, () -> new Main());
        });
    }
}
