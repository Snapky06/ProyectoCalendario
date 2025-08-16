package project;

import com.toedter.calendar.JDateChooser;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public final class GestionEventos {
    public static ArrayList<Evento> eventos = new ArrayList<>();

    public static Evento buscarEventoRecursivo(String codigo, int index) {
        if (index >= eventos.size()) return null;
        if (eventos.get(index).getCodigo().equals(codigo)) return eventos.get(index);
        return buscarEventoRecursivo(codigo, index + 1);
    }

    public static void crearEvento() {
        if (Usuarios.esLimitado()) {
            JOptionPane.showMessageDialog(null, "Los usuarios limitados no pueden crear eventos.");
            return;
        }
        String[] tipos = {"Deportivo", "Musical", "Religioso"};
        String tipoSeleccionado = (String) JOptionPane.showInputDialog(null, "Seleccione el tipo de evento:", "Crear Evento", JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);
        if (tipoSeleccionado == null) return;
        JTextField codigo = new JTextField();
        JTextField titulo = new JTextField();
        JTextField descripcion = new JTextField();
        JDateChooser fechaChooser = new JDateChooser();
        JTextField monto = new JTextField();
        Object[] message = {"Código:", codigo, "Título:", titulo, "Descripción:", descripcion, "Fecha:", fechaChooser, "Monto Renta:", monto};
        int option = JOptionPane.showConfirmDialog(null, message, "Datos del Evento", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) return;
        try {
            Date fechaSeleccionada = fechaChooser.getDate();
            if (fechaSeleccionada == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fecha.");
                return;
            }
            LocalDate fechaEvento = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            double montoRenta = Double.parseDouble(monto.getText());
            String creador = Usuarios.usuarioLogeado.getUser();
            if (buscarEventoRecursivo(codigo.getText(), 0) != null) {
                JOptionPane.showMessageDialog(null, "El código del evento ya existe.");
                return;
            }
            Evento nuevoEvento = null;
            switch (tipoSeleccionado) {
                case "Deportivo":
                    JTextField equipo1 = new JTextField();
                    JTextField equipo2 = new JTextField();
                    JComboBox<TipoDeporte> deportes = new JComboBox<>(TipoDeporte.values());
                    Object[] msgDeportivo = {"Equipo 1:", equipo1, "Equipo 2:", equipo2, "Deporte:", deportes};
                    int optDeportivo = JOptionPane.showConfirmDialog(null, msgDeportivo, "Detalles Deportivos", JOptionPane.OK_CANCEL_OPTION);
                    if (optDeportivo == JOptionPane.OK_OPTION) {
                        nuevoEvento = new EventoDeportivo(codigo.getText(), titulo.getText(), descripcion.getText(), fechaEvento, montoRenta, creador, equipo1.getText(), equipo2.getText(), (TipoDeporte) deportes.getSelectedItem());
                    }
                    break;
                case "Musical":
                    JComboBox<TipoMusica> musicas = new JComboBox<>(TipoMusica.values());
                    Object[] msgMusical = {"Tipo de Música:", musicas};
                    int optMusical = JOptionPane.showConfirmDialog(null, msgMusical, "Detalles Musicales", JOptionPane.OK_CANCEL_OPTION);
                    if (optMusical == JOptionPane.OK_OPTION) {
                        nuevoEvento = new EventoMusical(codigo.getText(), titulo.getText(), descripcion.getText(), fechaEvento, montoRenta, creador, (TipoMusica) musicas.getSelectedItem());
                    }
                    break;
                case "Religioso":
                    nuevoEvento = new EventoReligioso(codigo.getText(), titulo.getText(), descripcion.getText(), fechaEvento, montoRenta, creador);
                    break;
            }
            if (nuevoEvento != null) {
                eventos.add(nuevoEvento);
                Usuarios.usuarioLogeado.agregarEventoCreado(codigo.getText());
                JOptionPane.showMessageDialog(null, "Evento creado exitosamente.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error en el formato del monto.");
        }
    }

    public static void editarEvento() {
        if (Usuarios.esLimitado()) {
            JOptionPane.showMessageDialog(null, "Los usuarios limitados no pueden editar eventos.");
            return;
        }
        String codigo = JOptionPane.showInputDialog("Ingrese el código del evento a editar:");
        if (codigo == null || codigo.trim().isEmpty()) return;
        
        Evento evento = buscarEventoRecursivo(codigo, 0);
        if (evento == null) {
            JOptionPane.showMessageDialog(null, "El evento no existe.");
            return;
        }
        
        String[] opciones = {"Editar Datos Generales", "Añadir Personas/Jugadores"};
        if (evento instanceof EventoReligioso) {
            opciones = new String[]{"Editar Datos Generales", "Registrar Personas Convertidas"};
        }
        int seleccion = JOptionPane.showOptionDialog(null, "Seleccione qué desea editar", "Editar Evento", 
                                                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion == 0) {
            JTextField titulo = new JTextField(evento.getTitulo());
            JTextField descripcion = new JTextField(evento.getDescripcion());
            JDateChooser fechaChooser = new JDateChooser(Date.from(evento.getFecha().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            Object[] message = {"Título:", titulo, "Descripción:", descripcion, "Fecha:", fechaChooser};
            int option = JOptionPane.showConfirmDialog(null, message, "Editar Datos Generales", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                evento.titulo = titulo.getText();
                evento.descripcion = descripcion.getText();
                Date fechaSeleccionada = fechaChooser.getDate();
                if (fechaSeleccionada != null) {
                    evento.fecha = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                }
                JOptionPane.showMessageDialog(null, "Datos actualizados.");
            }
        } else if (seleccion == 1) {
            if (evento instanceof EventoDeportivo) {
                EventoDeportivo ed = (EventoDeportivo) evento;
                String jugador = JOptionPane.showInputDialog("Nombre del jugador a añadir para el equipo 1 (" + ed.equipo1 + "):");
                if(jugador != null && !jugador.isEmpty()) ed.jugadoresEq1.add(jugador);
                jugador = JOptionPane.showInputDialog("Nombre del jugador a añadir para el equipo 2 (" + ed.equipo2 + "):");
                if(jugador != null && !jugador.isEmpty()) ed.jugadoresEq2.add(jugador);
            } else if (evento instanceof EventoMusical) {
                EventoMusical em = (EventoMusical) evento;
                String staff = JOptionPane.showInputDialog("Nombre del miembro del staff a añadir:");
                if(staff != null && !staff.isEmpty()) em.equipoStaff.add(staff);
            } else if (evento instanceof EventoReligioso) {
                EventoReligioso er = (EventoReligioso) evento;
                try {
                    int convertidos = Integer.parseInt(JOptionPane.showInputDialog("Cantidad de personas convertidas:"));
                    er.personasConvertidas = convertidos;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Debe ser un número válido.");
                }
            }
            JOptionPane.showMessageDialog(null, "Información específica actualizada.");
        }
    }

    public static void eliminarEvento() {
        String codigo = JOptionPane.showInputDialog("Ingrese el código del evento a cancelar:");
        if (codigo == null || codigo.trim().isEmpty()) return;
        Evento evento = buscarEventoRecursivo(codigo.trim(), 0);
        if (evento == null) {
            JOptionPane.showMessageDialog(null, "El evento no existe.");
            return;
        }
        if (evento.isCancelado()) {
            JOptionPane.showMessageDialog(null, "Este evento ya ha sido cancelado.");
            return;
        }
        if (!evento.getCreador().equals(Usuarios.usuarioLogeado.getUser())) {
            JOptionPane.showMessageDialog(null, "Solo el usuario que creó el evento puede cancelarlo.");
            return;
        }
        if (evento.getFecha().isBefore(LocalDate.now())) {
            JOptionPane.showMessageDialog(null, "No se puede cancelar un evento que ya se realizó.");
            return;
        }
        long diasAnticipacion = ChronoUnit.DAYS.between(LocalDate.now(), evento.getFecha());
        double multa = 0;
        if (diasAnticipacion <= 1) {
            multa = evento.getMultaCancelacion();
            evento.setMultaPagada(multa);
            JOptionPane.showMessageDialog(null, "Se ha cobrado una multa de Lps. " + multa + " por cancelación tardía.");
        }
        evento.setCancelado(true);
        JOptionPane.showMessageDialog(null, "El evento ha sido cancelado exitosamente.");
    }
    
    public static void verEvento() {
        String codigo = JOptionPane.showInputDialog("Ingrese el código del evento a ver:");
        if (codigo == null || codigo.trim().isEmpty()) return;
        Evento evento = buscarEventoRecursivo(codigo.trim(), 0);
        if (evento == null) {
            JOptionPane.showMessageDialog(null, "El evento no existe.");
            return;
        }
        String info = "--- DETALLES DEL EVENTO ---\n" +
                      "Código: " + evento.getCodigo() + "\n" +
                      "Título: " + evento.getTitulo() + "\n" +
                      "Descripción: " + evento.getDescripcion() + "\n" +
                      "Fecha: " + evento.getFecha() + "\n" +
                      "Monto Renta: " + evento.getMontoRenta() + "\n" +
                      "Creador: " + evento.getCreador() + "\n" +
                      evento.getDetalles() + "\n";
        if (evento.isCancelado()) {
            info += "\nESTADO: CANCELADO\nMulta Pagada: Lps. " + evento.getMultaPagada();
        }
        JOptionPane.showMessageDialog(null, new JTextArea(info));
    }
}
