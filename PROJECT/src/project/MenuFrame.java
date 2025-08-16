package project;

import javax.swing.*;

public class MenuFrame extends FrontEnd {
    private JButton btnEventos = new JButton("Administración de Eventos");
    private JButton btnUsuarios = new JButton("Administración de Usuarios");
    private JButton btnReportes = new JButton("Reportes");
    private JButton btnSalir = new JButton("Cerrar Sesión");
    private JLabel tit = new JLabel("Menú Principal");
    
    public MenuFrame(){
        FrameConFondo(this, cargarFondo("/proyectocal/imagenes/FONDO1.jpg"));
        titulo1(tit);
        JButton[] botones = {btnEventos, btnUsuarios, btnReportes, btnSalir};
        layoutBtn(botones);
        acciones();
        this.setVisible(true);
    }
    
    public void acciones(){
        btnUsuarios.addActionListener(e -> {
            if (Usuarios.esAdmin()) {
                new usuariosFrame().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Acceso denegado. Solo para administradores.");
            }
        });

        btnEventos.addActionListener(e -> {
            new EventosFrame().setVisible(true);
            this.dispose();
        });

        btnReportes.addActionListener(e -> {
            new ReportesFrame().setVisible(true);
            this.dispose();
        });

        btnSalir.addActionListener(e -> {
            Usuarios.usuarioLogeado = null;
            new Main().setVisible(true);
            this.dispose();
        });
    }
}