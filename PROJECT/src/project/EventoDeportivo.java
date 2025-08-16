package project;

import java.time.LocalDate;
import java.util.ArrayList;

public class EventoDeportivo extends Evento {
    public static final int MAX_CAPACIDAD = 20000;
    public String equipo1, equipo2;
    public TipoDeporte tipoDeporte;
    public ArrayList<String> jugadoresEq1, jugadoresEq2;

    public EventoDeportivo(String c, String t, String d, LocalDate f, double m, String cr, String eq1, String eq2, TipoDeporte tipo) {
        super(c, t, d, f, m, cr);
        this.equipo1 = eq1;
        this.equipo2 = eq2;
        this.tipoDeporte = tipo;
        this.jugadoresEq1 = new ArrayList<>();
        this.jugadoresEq2 = new ArrayList<>();
    }

    @Override
    public String getDetalles() {
        return "Tipo: Deportivo\nDeporte: " + tipoDeporte + "\nEquipos: " + equipo1 + " vs " + equipo2 +
               "\nCapacidad Máxima: " + MAX_CAPACIDAD + " personas\n" +
               "Jugadores Equipo 1: " + jugadoresEq1.toString() + "\n" +
               "Jugadores Equipo 2: " + jugadoresEq2.toString();
    }
    
    @Override
    public double getMultaCancelacion() {
        return montoRenta * 0.50;
    }
}
