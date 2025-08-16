package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FrontEnd extends JFrame {

    // Paleta de colores refinada con un hover más oscuro y texto off-white
    private static final Color BACKGROUND_COMPONENTS = new Color(45, 45, 58);
    private static final Color BUTTON_HOVER_BACKGROUND = new Color(30, 32, 42); // Azul muy oscuro para el fondo del hover
    private static final Color TEXT_COLOR = new Color(236, 236, 234); // Blanco hueso (off-white)
    private static final Color BORDER_COLOR = new Color(68, 85, 132);
    private static final Color BORDER_HOVER_COLOR = new Color(40, 50, 70); // Borde oscuro para el hover

    public FrontEnd() {}

    public void FrameConFondo(JFrame frame, Image bg) {
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bg != null) {
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(new Color(28, 28, 36));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        frame.setContentPane(bgPanel);
        frame.getContentPane().setLayout(new GridBagLayout());
    }

    public Image cargarFondo(String imagen) {
        try {
            java.net.URL imageUrl = getClass().getResource(imagen);
            if (imageUrl == null) {
                JOptionPane.showMessageDialog(null, "No se pudo encontrar la imagen de fondo en la ruta:\n" + imagen, "Error de Recurso", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            return ImageIO.read(imageUrl);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error al cargar la imagen de fondo.", "Error de Carga", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return null;
        }
    }

    public void titulo1(JLabel titleLabel) {
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 85)); // Fuente elegante
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setBackground(BACKGROUND_COMPONENTS);
        titleLabel.setOpaque(true);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 3),
            BorderFactory.createEmptyBorder(20, 45, 20, 45)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(60, 20, 70, 20);
        gbc.anchor = GridBagConstraints.NORTH;
        getContentPane().add(titleLabel, gbc);
    }

    public void disBoton(JButton[] btn) {
        Font buttonFont = new Font("Georgia", Font.BOLD, 28); // Fuente elegante y tamaño ajustado
        Dimension buttonSize = new Dimension(500, 80);

        for (JButton b : btn) {
            b.setFont(buttonFont);
            b.setPreferredSize(buttonSize);
            b.setForeground(TEXT_COLOR);
            b.setBackground(BACKGROUND_COMPONENTS);
            b.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 3));
            b.setFocusPainted(false);
            b.setContentAreaFilled(true);

            b.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    b.setBackground(BUTTON_HOVER_BACKGROUND);
                    b.setBorder(BorderFactory.createLineBorder(BORDER_HOVER_COLOR, 4));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    b.setBackground(BACKGROUND_COMPONENTS);
                    b.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 3));
                }
            });
        }
    }

    public void layoutBtn(JButton[] btn) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        disBoton(btn);

        for (int i = 0; i < btn.length; i++) {
            JPanel buttonContainer = new JPanel(new GridBagLayout());
            buttonContainer.setOpaque(false);
            buttonContainer.add(btn[i]);
            panel.add(buttonContainer);

            if (i < btn.length - 1) {
                panel.add(Box.createRigidArea(new Dimension(0, 20)));
            }
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        getContentPane().add(panel, gbc);
    }
}