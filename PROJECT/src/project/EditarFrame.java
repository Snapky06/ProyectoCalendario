package project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarFrame {
    private boolean selfDemoted = false;

    public EditarFrame(int userIndex) {
        LogicaUsuario user = Usuarios.users.get(userIndex);
        String originalType = user.getTipo();

        JTextField tfNombre = new JTextField(user.getNombre());
        JTextField tfUser = new JTextField(user.getUser());
        tfUser.setEditable(false);
        JPasswordField tfPass = new JPasswordField(user.getPass());
        JTextField tfEdad = new JTextField(String.valueOf(user.getEdad()));
        String[] tipos = {"Administrador", "Contenidos", "Limitado"};
        JComboBox<String> cbTipo = new JComboBox<>(tipos);
        cbTipo.setSelectedItem(user.getTipo());
        JCheckBox showPass = new JCheckBox("Mostrar Contraseña");

        Object[] message = {
            "Nombre Completo:", tfNombre,
            "Username (no editable):", tfUser,
            "Password:", tfPass,
            "Edad:", tfEdad,
            "Tipo de Usuario:", cbTipo,
            showPass
        };

        showPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPass.isSelected()) {
                    tfPass.setEchoChar((char) 0);
                } else {
                    tfPass.setEchoChar('•');
                }
            }
        });

        int option = JOptionPane.showConfirmDialog(null, message, "Editar Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String nombre = tfNombre.getText();
                String pass = new String(tfPass.getPassword());
                int edad = Integer.parseInt(tfEdad.getText());
                String tipo = (String) cbTipo.getSelectedItem();

                if (!nombre.matches("^[a-zA-Z ]+$")) {
                    JOptionPane.showMessageDialog(null, "El nombre completo solo puede contener letras y espacios.");
                    return;
                }
                
                Usuarios.editarUsuario(userIndex, tipo, nombre, user.getUser(), pass, edad);

                if (user.getUser().equals(Usuarios.usuarioLogeado.getUser()) && 
                    originalType.equals("Administrador") && !tipo.equals("Administrador")) {
                    this.selfDemoted = true;
                    JOptionPane.showMessageDialog(null, "Ha cambiado su propio rol. Será redirigido al Menú Principal.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "La edad debe ser un número válido.");
            }
        }
    }

    public boolean isSelfDemoted() {
        return selfDemoted;
    }
}