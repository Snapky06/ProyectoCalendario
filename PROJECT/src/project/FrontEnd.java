package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FrontEnd extends JFrame {
    // Paleta de colores sólidos para una interfaz limpia y sin errores visuales
    private static final Color BACKGROUND_COMPONENTS = new Color(45, 55, 75); // Azul oscuro sólido
    private static final Color BUTTON_HOVER_BACKGROUND = new Color(70, 80, 100); // Tono más claro para el hover
    private static final Color TEXT_COLOR = new Color(230, 230, 230);
    private static final Color BORDER_COLOR = new Color(0, 191, 255);
    private static final Color BORDER_HOVER_COLOR = new Color(90, 210, 255);

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
                    g.setColor(new Color(15, 15, 25));
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
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 80));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setBackground(BACKGROUND_COMPONENTS); // Fondo sólido
        titleLabel.setOpaque(true);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 40, 20, 40)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(40, 20, 50, 20);
        gbc.anchor = GridBagConstraints.NORTH;
        getContentPane().add(titleLabel, gbc);
    }

    public void disBoton(JButton[] btn) {
        Font buttonFont = new Font("Segoe UI", Font.PLAIN, 28);
        Dimension buttonSize = new Dimension(450, 80);

        for (JButton b : btn) {
            b.setFont(buttonFont);
            b.setPreferredSize(buttonSize);
            b.setForeground(TEXT_COLOR);
            b.setBackground(BACKGROUND_COMPONENTS); // Fondo sólido
            b.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
            b.setFocusPainted(false);
            b.setContentAreaFilled(true); // Asegura que el fondo del botón sea visible y opaco

            b.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    b.setBackground(BUTTON_HOVER_BACKGROUND);
                    b.setBorder(BorderFactory.createLineBorder(BORDER_HOVER_COLOR, 2));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    b.setBackground(BACKGROUND_COMPONENTS);
                    b.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
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
                panel.add(Box.createRigidArea(new Dimension(0, 30)));
            }
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        getContentPane().add(panel, gbc);
    }
}