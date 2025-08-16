package project;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FrontEnd extends JFrame {
    private static final Color PASTEL_AZUL = new Color(168, 205, 224);
    private static final Color TEXTO_OSCURO = new Color(46, 58, 70);

    public FrontEnd() {}

    public void FrameConFondo(JFrame frame, Image bg) {
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bg != null) g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        frame.setContentPane(bgPanel);
        frame.getContentPane().setLayout(new GridBagLayout());
    }

    public Image cargarFondo(String imagen) {
        try {
            return ImageIO.read(FrontEnd.class.getResource(imagen));
        } catch (IOException | IllegalArgumentException ex) {
            System.err.println("No se pudo cargar el fondo: " + ex.getMessage());
            return null;
        }
    }

    public void titulo1(JLabel titleLabel) {
        titleLabel.setFont(new Font("Arial", Font.BOLD, 60));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(PASTEL_AZUL);
        titleLabel.setForeground(TEXTO_OSCURO);
        titleLabel.setBorder(BorderFactory.createLineBorder(TEXTO_OSCURO, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(5, 5, 50, 5); gbc.fill = GridBagConstraints.NORTH;
        getContentPane().add(titleLabel, gbc);
    }

    public void disBoton(JButton[] btn) {
        Font buttonFont = new Font("Arial", Font.BOLD, 24); // Adjusted font size
        Dimension buttonSize = new Dimension(400, 70); // Adjusted button size
        for (JButton b : btn) {
            b.setFont(buttonFont); b.setPreferredSize(buttonSize); b.setOpaque(true);
            b.setContentAreaFilled(true); b.setBorderPainted(true); b.setBackground(PASTEL_AZUL);
            b.setForeground(TEXTO_OSCURO); b.setBorder(BorderFactory.createLineBorder(TEXTO_OSCURO, 10));
        }
    }

    public void layoutBtn(JButton[] btn) {
        JPanel panel = new JPanel(new GridLayout(btn.length, 1, 0, 20)); // Adjusted gap
        panel.setOpaque(false);
        disBoton(btn);
        for (JButton b : btn) panel.add(b);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.NORTH;
        getContentPane().add(panel, gbc);
    }
}
