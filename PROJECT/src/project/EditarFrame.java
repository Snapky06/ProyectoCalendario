package project;

import javax.swing.*;

public class EditarFrame {
    public EditarFrame(int userIndex) {
        LogicaUsuario user = Usuarios.users.get(userIndex);
        JTextField tfNombre = new JTextField(user.getNombre());
        JTextField tfUser = new JTextField(user.getUser());
        tfUser.setEditable(false);
        JPasswordField tfPass = new JPasswordField(user.getPass());
        JTextField tfEdad = new JTextField(String.valueOf(user.getEdad()));
        String[] tipos = {"Administrador", "Contenidos", "Limitado"};
        JComboBox<String> cbTipo = new JComboBox<>(tipos);
        cbTipo.setSelectedItem(user.getTipo());
        Object[] message = {
            "Nombre Completo:", tfNombre,
            "Username (no editable):", tfUser,
            "Password:", tfPass,
            "Edad:", tfEdad,
            "Tipo de Usuario:", cbTipo
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Editar Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String nombre = tfNombre.getText();
                String pass = new String(tfPass.getPassword());
                int edad = Integer.parseInt(tfEdad.getText());
                String tipo = (String) cbTipo.getSelectedItem();
                Usuarios.editarUsuario(userIndex, tipo, nombre, user.getUser(), pass, edad);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "La edad debe ser un número válido.");
            }
        }
    }
}
