package project;

import com.toedter.calendar.JDateChooser;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public final class Reportes {
    private static double sumarMontosRecursivo(ArrayList<Evento> lista, int index) {
        if (index >= lista.size()) return 0;
        double monto = lista.get(index).isCancelado() ? lista.get(index).getMultaPagada() : lista.get(index).getMontoRenta();
        return monto + sumarMontosRecursivo(lista, index + 1);
    }
    
    private static int contarEventosPorTipo(ArrayList<Evento> lista, Class<?> tipo, int index) {
        if (index >= lista.size()) return 0;
        int count = tipo.isInstance(lista.get(index)) ? 1 : 0;
        return count + contarEventosPorTipo(lista, tipo, index + 1);
    }
    
    private static double sumarMontosPorTipo(ArrayList<Evento> lista, Class<?> tipo, int index) {
        if (index >= lista.size()) return 0;
        double monto = 0;
        if (tipo.isInstance(lista.get(index))) {
            monto = lista.get(index).isCancelado() ? lista.get(index).getMultaPagada() : lista.get(index).getMontoRenta();
        }
        return monto + sumarMontosPorTipo(lista, tipo, index + 1);
    }

    public static void listarEventos(String tipoReporte) {
        ArrayList<Evento> filtrados = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        StringBuilder reporte = new StringBuilder();
        switch (tipoReporte) {
            case "Futuros":
                reporte.append("--- Eventos Futuros ---\n");
                for (Evento e : GestionEventos.eventos) if (!e.getFecha().isBefore(hoy) && !e.isCancelado()) filtrados.add(e);
                break;
            case "Realizados":
                reporte.append("--- Eventos Realizados ---\n");
                for (Evento e : GestionEventos.eventos) if (e.getFecha().isBefore(hoy) && !e.isCancelado()) filtrados.add(e);
                break;
            case "Cancelados":
                reporte.append("--- Eventos Cancelados ---\n");
                for (Evento e : GestionEventos.eventos) if (e.isCancelado()) filtrados.add(e);
                break;
        }
        if (filtrados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay eventos para este reporte.");
            return;
        }
        filtrados.sort((e1, e2) -> e2.getFecha().compareTo(e1.getFecha()));
        for (Evento e : filtrados) {
            reporte.append("COD: ").append(e.getCodigo()).append(" - TIPO: ").append(e.getClass().getSimpleName())
                   .append(" - TITULO: ").append(e.getTitulo()).append(" - FECHA: ").append(e.getFecha())
                   .append(" - MONTO: ").append(e.isCancelado() ? e.getMultaPagada() : e.getMontoRenta()).append("\n");
        }
        reporte.append("\n--- Estadísticas ---\n");
        reporte.append("Deportivos: ").append(contarEventosPorTipo(filtrados, EventoDeportivo.class, 0)).append("\n");
        reporte.append("Musicales: ").append(contarEventosPorTipo(filtrados, EventoMusical.class, 0)).append("\n");
        reporte.append("Religiosos: ").append(contarEventosPorTipo(filtrados, EventoReligioso.class, 0)).append("\n\n");
        reporte.append("Monto Total (Deportivos): ").append(sumarMontosPorTipo(filtrados, EventoDeportivo.class, 0)).append("\n");
        reporte.append("Monto Total (Musicales): ").append(sumarMontosPorTipo(filtrados, EventoMusical.class, 0)).append("\n");
        reporte.append("Monto Total (Religiosos): ").append(sumarMontosPorTipo(filtrados, EventoReligioso.class, 0)).append("\n");
        JOptionPane.showMessageDialog(null, new JTextArea(reporte.toString()));
    }

    public static void ingresoPorFecha() {

        JDateChooser fechaIniChooser = new JDateChooser();
        int optionIni = JOptionPane.showConfirmDialog(null, 
                new Object[]{"Seleccione la Fecha Inicial:", fechaIniChooser}, 
                "Ingreso por Rango de Fechas (Paso 1 de 2)", 
                JOptionPane.OK_CANCEL_OPTION);

        if (optionIni != JOptionPane.OK_OPTION) return;
        
        Date fechaSeleccionadaIni = fechaIniChooser.getDate();
        if (fechaSeleccionadaIni == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fecha inicial.");
            return;
        }

        JDateChooser fechaFinChooser = new JDateChooser();
        fechaFinChooser.setMinSelectableDate(fechaSeleccionadaIni); 
        fechaFinChooser.setDate(fechaSeleccionadaIni); 

        int optionFin = JOptionPane.showConfirmDialog(null, 
                new Object[]{"Seleccione la Fecha Final:", fechaFinChooser}, 
                "Ingreso por Rango de Fechas (Paso 2 de 2)", 
                JOptionPane.OK_CANCEL_OPTION);

        if (optionFin != JOptionPane.OK_OPTION) return;

        Date fechaSeleccionadaFin = fechaFinChooser.getDate();
        if (fechaSeleccionadaFin == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fecha final.");
            return;
        }

        try {
            LocalDate inicio = fechaSeleccionadaIni.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fin = fechaSeleccionadaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            ArrayList<Evento> filtrados = new ArrayList<>();
            for (Evento e : GestionEventos.eventos) {
                if (!e.getFecha().isBefore(inicio) && !e.getFecha().isAfter(fin)) {
                    filtrados.add(e);
                }
            }
            double totalGenerado = sumarMontosRecursivo(filtrados, 0);
            StringBuilder reporte = new StringBuilder("--- Ingresos entre " + inicio + " y " + fin + " ---\n");
            reporte.append("Total Generado (incluye rentas y multas): Lps. ").append(totalGenerado).append("\n\n");
            reporte.append("--- Detalle de Eventos en Rango ---\n");
            reporte.append("Deportivos: ").append(contarEventosPorTipo(filtrados, EventoDeportivo.class, 0)).append("\n");
            reporte.append("Musicales: ").append(contarEventosPorTipo(filtrados, EventoMusical.class, 0)).append("\n");
            reporte.append("Religiosos: ").append(contarEventosPorTipo(filtrados, EventoReligioso.class, 0)).append("\n");
            JOptionPane.showMessageDialog(null, new JTextArea(reporte.toString()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error al procesar las fechas.");
        }
    }

    public static void verPerfilUsuario() {
        String perfil = Usuarios.usuarioLogeado.getPerfil();
        if (Usuarios.usuarioLogeado.getEventosCreados() != null) {
            perfil += "\n\n--- Eventos Creados ---\n";
            for (String codigoEvento : Usuarios.usuarioLogeado.getEventosCreados()) {
                Evento evento = GestionEventos.buscarEventoRecursivo(codigoEvento, 0);
                if (evento != null) {
                    perfil += "ID: " + evento.getCodigo() + " - TIPO: " + evento.getClass().getSimpleName() + 
                              " - TITULO: " + evento.getTitulo() + " - MONTO: " + evento.getMontoRenta() + "\n";
                }
            }
        }
        JOptionPane.showMessageDialog(null, new JTextArea(perfil));
    }
}