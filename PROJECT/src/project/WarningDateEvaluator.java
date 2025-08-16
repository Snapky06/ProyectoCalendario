package project;

import com.toedter.calendar.IDateEvaluator;
import java.awt.Color;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class WarningDateEvaluator implements IDateEvaluator {

    @Override
    public boolean isSpecial(Date date) {
        if (Usuarios.esAdmin()) {
            return false;
        }
        LocalDate calendarDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), calendarDate);
        return daysBetween >= 0 && daysBetween < 7;
    }

    @Override
    public Color getSpecialForegroundColor() {
        return null;
    }

    @Override
    public Color getSpecialBackroundColor() {
        return Color.YELLOW;
    }

    @Override
    public String getSpecialTooltip() {
        return "Se le cobrará extra por reservar con poca anticipación.";
    }

    @Override
    public boolean isInvalid(Date date) {
        return false;
    }

    @Override
    public Color getInvalidForegroundColor() {
        return null;
    }

    @Override
    public Color getInvalidBackroundColor() {
        return null;
    }

    @Override
    public String getInvalidTooltip() {
        return null;
    }
}
