package project;

import javax.swing.*;

public class EventosFrame extends FrontEnd {
    private JButton btnCrear = new JButton("Crear Evento");
    private JButton btnEditar = new JButton("Editar Evento");
    private JButton btnEliminar = new JButton("Cancelar Evento");
    private JButton btnVer = new JButton("Ver Evento");
    private JButton btnRegresar = new JButton("Regresar al Menú");
    private JLabel tit = new JLabel("Gestión de Eventos");

    public EventosFrame() {
        FrameConFondo(this, cargarFondo("/proyectocal/imagenes/FONDO1.jpg"));
        titulo1(tit);
        if (Usuarios.esLimitado()) {
            btnCrear.setEnabled(false);
            btnEditar.setEnabled(false);
        }
        JButton[] botones = {btnCrear, btnEditar, btnEliminar, btnVer, btnRegresar};
        layoutBtn(botones);
        acciones();
        this.setVisible(true);
    }

    private void acciones() {
        btnCrear.addActionListener(e -> GestionEventos.crearEvento());
        btnEditar.addActionListener(e -> GestionEventos.editarEvento());
        btnEliminar.addActionListener(e -> GestionEventos.eliminarEvento());
        btnVer.addActionListener(e -> GestionEventos.verEvento());
        btnRegresar.addActionListener(e -> {
            new MenuFrame().setVisible(true);
            this.dispose();
        });
    }
}
