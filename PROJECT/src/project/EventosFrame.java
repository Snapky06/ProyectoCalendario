package project;

import javax.swing.*;
import java.util.ArrayList;

public class EventosFrame extends FrontEnd {

    private JButton btnCrear = new JButton("Crear Evento");
    private JButton btnEditar = new JButton("Editar Evento");
    private JButton btnEliminar = new JButton("Cancelar Evento");
    private JButton btnVer = new JButton("Ver Evento");
    private JButton btnRegresar = new JButton("Regresar al Menú");
    private JLabel tit = new JLabel("Gestión de Eventos");
    private final String imagen = "/proyectocal/imagenes/prueba5.jpg";

    public EventosFrame() {
        FrameConFondo(this, cargarFondo(imagen));
        titulo1(tit);
        
        ArrayList<JButton> botones = new ArrayList<>();

        if (!Usuarios.esLimitado()) {
            botones.add(btnCrear);
            botones.add(btnEditar);
        }

        boolean puedeCancelar = false;
        if (!Usuarios.esLimitado()) {
            puedeCancelar = true;
        } else { 
            if (Usuarios.usuarioLogeado != null && 
                Usuarios.usuarioLogeado.getEventosCreados() != null && 
                !Usuarios.usuarioLogeado.getEventosCreados().isEmpty()) {
                puedeCancelar = true;
            }
        }

        if (puedeCancelar) {
            botones.add(btnEliminar);
        }

        botones.add(btnVer);
        botones.add(btnRegresar);
        
        layoutBtn(botones.toArray(new JButton[0]));
        acciones();
        transicionSuave.fadeIn(this);
    }

    private void acciones() {
        btnCrear.addActionListener(e -> GestionEventos.crearEvento());
        btnEditar.addActionListener(e -> GestionEventos.editarEvento());
        btnEliminar.addActionListener(e -> GestionEventos.eliminarEvento());
        btnVer.addActionListener(e -> GestionEventos.verEvento());
        btnRegresar.addActionListener(e -> {
            transicionSuave.fadeOut(this, () -> new MenuFrame());
        });
    }
}