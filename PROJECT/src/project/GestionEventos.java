package project;

import com.toedter.calendar.JDateChooser;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

        TipoDeporte tipoDeporteSeleccionado = null;
        TipoMusica tipoMusicaSeleccionado = null;

        switch (tipoSeleccionado) {
            case "Deportivo":
                JOptionPane.showMessageDialog(null, "LA CANTIDAD MAXIMA de gente permitida es de 20 mil.");
                JComboBox<TipoDeporte> deportes = new JComboBox<>(TipoDeporte.values());
                int optDeporte = JOptionPane.showOptionDialog(null, deportes, "Seleccione un Deporte", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
                if (optDeporte != JOptionPane.OK_OPTION) return;
                tipoDeporteSeleccionado = (TipoDeporte) deportes.getSelectedItem();
                break;
            case "Musical":
                JOptionPane.showMessageDialog(null, "LA CANTIDAD MAXIMA permitida es de 25 mil (por el uso de la grama).\nSe le cobra un seguro por la grama de 30% sobre el valor acordado de renta.");
                JComboBox<TipoMusica> musicas = new JComboBox<>(TipoMusica.values());
                int optMusical = JOptionPane.showOptionDialog(null, musicas, "Seleccione un Género Musical", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
                if (optMusical != JOptionPane.OK_OPTION) return;
                tipoMusicaSeleccionado = (TipoMusica) musicas.getSelectedItem();
                break;
            case "Religioso":
                JOptionPane.showMessageDialog(null, "LA CANTIDAD MAXIMA permitida es de 30 mil.\nSe cobra 2000 lps fijos de seguro por el desgaste de la grama.");
                break;
        }

        JTextField codigo = new JTextField();
        JTextField titulo = new JTextField();
        JTextField descripcion = new JTextField();
        JDateChooser fechaChooser = new JDateChooser();
        
        fechaChooser.getJCalendar().getDayChooser().addDateEvaluator(new OcupadoDateEvaluator());
        fechaChooser.getJCalendar().getDayChooser().addDateEvaluator(new WarningDateEvaluator());
        fechaChooser.repaint();

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

            if (!Usuarios.esAdmin()) {
                if (fechaEvento.isBefore(LocalDate.now()) || fechaEvento.isEqual(LocalDate.now())) {
                    JOptionPane.showMessageDialog(null, "No se pueden crear eventos en fechas pasadas o el mismo día.");
                    return;
                }
            }
            
            for (Evento eventoExistente : eventos) {
                if (eventoExistente.getFecha().equals(fechaEvento) && !eventoExistente.isCancelado()) {
                    JOptionPane.showMessageDialog(null, "Esta fecha ya está ocupada por otro evento.");
                    return;
                }
            }

            double montoRenta = Double.parseDouble(monto.getText());

            if (tipoSeleccionado.equals("Religioso") && montoRenta > 30000) {
                JOptionPane.showMessageDialog(null, "El monto de renta para eventos religiosos no puede ser mayor a 30,000 Lps.");
                return;
            }

            if (!Usuarios.esAdmin() && !tipoSeleccionado.equals("Religioso")) {
                long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), fechaEvento);
                if (daysBetween < 7) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Está creando un evento con menos de una semana de anticipación. Se aplicará un recargo del 15%. ¿Desea continuar?", "Aviso de Recargo", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        montoRenta *= 1.15;
                    } else {
                        return;
                    }
                }
            }

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
                    Object[] msgDeportivo = {"Equipo 1:", equipo1, "Equipo 2:", equipo2};
                    int optDeportivo = JOptionPane.showConfirmDialog(null, msgDeportivo, "Detalles Deportivos", JOptionPane.OK_CANCEL_OPTION);
                    if (optDeportivo == JOptionPane.OK_OPTION) {
                        nuevoEvento = new EventoDeportivo(codigo.getText(), titulo.getText(), descripcion.getText(), fechaEvento, montoRenta, creador, equipo1.getText(), equipo2.getText(), tipoDeporteSeleccionado);
                    }
                    break;
                case "Musical":
                    nuevoEvento = new EventoMusical(codigo.getText(), titulo.getText(), descripcion.getText(), fechaEvento, montoRenta, creador, tipoMusicaSeleccionado);
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
            
            fechaChooser.getJCalendar().getDayChooser().addDateEvaluator(new OcupadoDateEvaluator());
            fechaChooser.getJCalendar().getDayChooser().addDateEvaluator(new WarningDateEvaluator());
            fechaChooser.repaint();

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
                int tamanoEquipo = ed.tipoDeporte.getTamanoEquipo();

                JPanel panelEquipo1 = new JPanel(new GridLayout(tamanoEquipo, 2, 5, 5));
                panelEquipo1.setBorder(BorderFactory.createTitledBorder("Jugadores de " + ed.equipo1));
                List<JTextField> camposJugadores1 = new ArrayList<>();
                for (int i = 0; i < tamanoEquipo; i++) {
                    String label = (tamanoEquipo == 1) ? "Jugador:" : "Jugador " + (i + 1) + ":";
                    panelEquipo1.add(new JLabel(label));
                    JTextField campo = new JTextField();
                    camposJugadores1.add(campo);
                    panelEquipo1.add(campo);
                }

                int result1 = JOptionPane.showConfirmDialog(null, panelEquipo1, "Añadir Jugadores a " + ed.equipo1, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result1 == JOptionPane.OK_OPTION) {
                    for (JTextField campo : camposJugadores1) {
                        if (!campo.getText().trim().isEmpty()) {
                            ed.jugadoresEq1.add(campo.getText().trim());
                        }
                    }
                }

                JPanel panelEquipo2 = new JPanel(new GridLayout(tamanoEquipo, 2, 5, 5));
                panelEquipo2.setBorder(BorderFactory.createTitledBorder("Jugadores de " + ed.equipo2));
                List<JTextField> camposJugadores2 = new ArrayList<>();
                for (int i = 0; i < tamanoEquipo; i++) {
                    String label = (tamanoEquipo == 1) ? "Jugador:" : "Jugador " + (i + 1) + ":";
                    panelEquipo2.add(new JLabel(label));
                    JTextField campo = new JTextField();
                    camposJugadores2.add(campo);
                    panelEquipo2.add(campo);
                }

                int result2 = JOptionPane.showConfirmDialog(null, panelEquipo2, "Añadir Jugadores a " + ed.equipo2, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result2 == JOptionPane.OK_OPTION) {
                    for (JTextField campo : camposJugadores2) {
                        if (!campo.getText().trim().isEmpty()) {
                            ed.jugadoresEq2.add(campo.getText().trim());
                        }
                    }
                }
            } else if (evento instanceof EventoMusical) {
                EventoMusical em = (EventoMusical) evento;
                try {
                    int staffAAnadir = Integer.parseInt(JOptionPane.showInputDialog("¿Cuántos miembros de staff desea añadir?"));
                    if (em.equipoStaff.size() + staffAAnadir > 40) {
                        JOptionPane.showMessageDialog(null, "No se pueden añadir más de 40 miembros de staff en total.");
                        return;
                    }

                    JPanel panelStaff = new JPanel(new GridLayout(staffAAnadir, 2, 5, 5));
                    panelStaff.setBorder(BorderFactory.createTitledBorder("Añadir Staff"));
                    List<JTextField> camposStaff = new ArrayList<>();
                    for (int i = 0; i < staffAAnadir; i++) {
                        panelStaff.add(new JLabel("Miembro " + (i + 1) + ":"));
                        JTextField campo = new JTextField();
                        camposStaff.add(campo);
                        panelStaff.add(campo);
                    }

                    int result = JOptionPane.showConfirmDialog(null, panelStaff, "Añadir Miembros de Staff", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        for (JTextField campo : camposStaff) {
                            if (!campo.getText().trim().isEmpty()) {
                                em.equipoStaff.add(campo.getText().trim());
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un número válido.");
                }
            } else if (evento instanceof EventoReligioso) {
                EventoReligioso er = (EventoReligioso) evento;
                try {
                    int convertidos = Integer.parseInt(JOptionPane.showInputDialog("Cantidad de personas convertidas:"));
                    if (convertidos > EventoReligioso.MAX_CAPACIDAD) {
                        JOptionPane.showMessageDialog(null, "El número de convertidos no puede exceder la capacidad máxima de " + EventoReligioso.MAX_CAPACIDAD + ".");
                        return;
                    }
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