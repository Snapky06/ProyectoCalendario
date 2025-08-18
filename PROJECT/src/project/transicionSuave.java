package project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Supplier;

public class transicionSuave {

    public static void fadeIn(JFrame frame) {
        frame.setOpacity(0f);
        frame.setVisible(true);

        Timer timer = new Timer(15, new ActionListener() {
            private float opacity = 0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += 0.05f;
                if (opacity >= 1.0f) {
                    opacity = 1.0f;
                    ((Timer) e.getSource()).stop();
                }
                frame.setOpacity(opacity);
            }
        });
        timer.start();
    }

    public static void fadeOut(JFrame ventanaActual, Supplier<JFrame> CreadorDeProximaVentana) {
        Timer timer = new Timer(15, new ActionListener() {
            private float opacity = 1f;

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.05f;
                if (opacity <= 0.0f) {
                    opacity = 0.0f;
                    ((Timer) e.getSource()).stop();
                    ventanaActual.dispose();
                    

                    CreadorDeProximaVentana.get();
                }
                ventanaActual.setOpacity(opacity);
            }
        });
        timer.start();
    }
}