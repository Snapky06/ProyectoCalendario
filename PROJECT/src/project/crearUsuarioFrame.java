package project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class crearUsuarioFrame {
    public crearUsuarioFrame() {
        JTextField tfNombre = new JTextField();
        JTextField tfUser = new JTextField();
        JPasswordField tfPass = new JPasswordField();
        JPasswordField tfConfirmPass = new JPasswordField();
        JTextField tfEdad = new JTextField();
        String[] tipos = {"Administrador", "Contenidos", "Limitado"};
        JComboBox<String> cbTipo = new JComboBox<>(tipos);
        JCheckBox showPass = new JCheckBox("Mostrar Contraseña");

        Object[] message = {
            "Nombre Completo:", tfNombre,
            "Username:", tfUser,
            "Password:", tfPass,
            "Confirmar Password:", tfConfirmPass,
            "Edad:", tfEdad,
            "Tipo de Usuario:", cbTipo,
            showPass
        };

        showPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPass.isSelected()) {
                    tfPass.setEchoChar((char) 0);
                    tfConfirmPass.setEchoChar((char) 0);
                } else {
                    tfPass.setEchoChar('•');
                    tfConfirmPass.setEchoChar('•');
                }
            }
        });

        int option = JOptionPane.showConfirmDialog(null, message, "Crear Nuevo Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String nombre = tfNombre.getText();
                String user = tfUser.getText();
                String pass = new String(tfPass.getPassword());
                String confirmPass = new String(tfConfirmPass.getPassword());
                int edad = Integer.parseInt(tfEdad.getText());
                String tipo = (String) cbTipo.getSelectedItem();

                if (nombre.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                    return;
                }

                if (!nombre.matches("^[a-zA-Z ]+$")) {
                    JOptionPane.showMessageDialog(null, "El nombre completo solo puede contener letras y espacios.");
                    return;
                }

                if (!pass.equals(confirmPass)) {
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.");
                    return;
                }
                
                Usuarios.addUser(tipo, nombre, user, pass, edad);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "La edad debe ser un número válido.");
            }
        }
    }
}