package project;

import javax.swing.*;

public class crearUsuarioFrame {
    public crearUsuarioFrame() {
        JTextField tfNombre = new JTextField();
        JTextField tfUser = new JTextField();
        JPasswordField tfPass = new JPasswordField();
        JTextField tfEdad = new JTextField();
        String[] tipos = {"Administrador", "Contenidos", "Limitado"};
        JComboBox<String> cbTipo = new JComboBox<>(tipos);

        Object[] message = {
            "Nombre Completo:", tfNombre,
            "Username:", tfUser,
            "Password:", tfPass,
            "Edad:", tfEdad,
            "Tipo de Usuario:", cbTipo
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Crear Nuevo Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String nombre = tfNombre.getText();
                String user = tfUser.getText();
                String pass = new String(tfPass.getPassword());
                int edad = Integer.parseInt(tfEdad.getText());
                String tipo = (String) cbTipo.getSelectedItem();

                if (nombre.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                    return;
                }
                
                Usuarios.addUser(tipo, nombre, user, pass, edad);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "La edad debe ser un número válido.");
            }
        }
    }
}
