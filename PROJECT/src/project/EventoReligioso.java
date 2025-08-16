package project;

import java.time.LocalDate;

public class EventoReligioso extends Evento {
    public static final int MAX_CAPACIDAD = 30000;
    public int personasConvertidas;

    public EventoReligioso(String c, String t, String d, LocalDate f, double m, String cr) {
        super(c, t, d, f, m + 2000, cr);
        this.personasConvertidas = 0;
    }

    @Override
    public String getDetalles() {
        return "Tipo: Religioso\nPersonas Convertidas: " + personasConvertidas +
               "\nCapacidad Máxima: " + MAX_CAPACIDAD + " personas";
    }

    @Override
    public double getMultaCancelacion() {
        return 0;
    }
}
