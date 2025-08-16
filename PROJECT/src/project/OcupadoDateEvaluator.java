package project;

import com.toedter.calendar.IDateEvaluator;
import java.awt.Color;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class OcupadoDateEvaluator implements IDateEvaluator {

    @Override
    public boolean isSpecial(Date date) {
        LocalDate calendarDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        for (Evento evento : GestionEventos.eventos) {
            if (evento.getFecha().equals(calendarDate) && !evento.isCancelado()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Color getSpecialForegroundColor() {
        return Color.RED;
    }

    @Override
    public Color getSpecialBackroundColor() {
        return null;
    }

    @Override
    public String getSpecialTooltip() {
        return "Esta fecha ya está ocupada por otro evento.";
    }

    @Override
    public boolean isInvalid(Date date) {

        if (Usuarios.esAdmin()) {
            return false;
        }
        LocalDate calendarDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return calendarDate.isBefore(LocalDate.now()) || calendarDate.isEqual(LocalDate.now());

    }

    @Override
    public Color getInvalidForegroundColor() {
        return Color.GRAY;
    }

    @Override
    public Color getInvalidBackroundColor() {
        return null;
    }

    @Override
    public String getInvalidTooltip() {
        return "No se pueden crear eventos en fechas pasadas o el mismo día.";
    }
}