package project;

import javax.swing.*;

public class ReportesFrame extends FrontEnd {

    private JButton btnFuturos = new JButton("Eventos Futuros");
    private JButton btnRealizados = new JButton("Eventos Realizados");
    private JButton btnCancelados = new JButton("Eventos Cancelados");
    private JButton btnIngresoFecha = new JButton("Ingreso por Fecha");
    private JButton btnPerfil = new JButton("Ver Mi Perfil");
    private JButton btnRegresar = new JButton("Regresar al Menú");
    private JLabel tit = new JLabel("Reportes");
    private final String imagen = "/proyectocal/imagenes/prueba4.jpg";

    public ReportesFrame() {
        FrameConFondo(this, cargarFondo(imagen));
        transicionSuave.fadeIn(this);
        titulo1(tit);
        JButton[] botones = {btnFuturos, btnRealizados, btnCancelados, btnIngresoFecha, btnPerfil, btnRegresar};
        layoutBtn(botones);
        acciones();
        this.setVisible(true);
    }

    private void acciones() {
        btnFuturos.addActionListener(e -> Reportes.listarEventos("Futuros"));
        btnRealizados.addActionListener(e -> Reportes.listarEventos("Realizados"));
        btnCancelados.addActionListener(e -> Reportes.listarEventos("Cancelados"));
        btnIngresoFecha.addActionListener(e -> Reportes.ingresoPorFecha());
        btnPerfil.addActionListener(e -> Reportes.verPerfilUsuario());
        btnRegresar.addActionListener(e -> {
            transicionSuave.fadeOut(this, () -> new MenuFrame());
        });
    }
}
