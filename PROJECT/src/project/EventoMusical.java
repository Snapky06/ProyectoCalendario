package project;

import java.time.LocalDate;
import java.util.ArrayList;

public class EventoMusical extends Evento {
    public static final int MAX_CAPACIDAD = 25000;
    public TipoMusica tipoMusica;
    public ArrayList<String> equipoStaff;

    public EventoMusical(String c, String t, String d, LocalDate f, double m, String cr, TipoMusica tipo) {
        super(c, t, d, f, m + (m * 0.30), cr);
        this.tipoMusica = tipo;
        this.equipoStaff = new ArrayList<>();
    }

    @Override
    public String getDetalles() {
        return "Tipo: Musical\nMúsica: " + tipoMusica + "\nCapacidad Máxima: " + MAX_CAPACIDAD + " personas\n" +
               "Staff: " + equipoStaff.toString();
    }

    @Override
    public double getMultaCancelacion() {
        return montoRenta * 0.50;
    }
}
