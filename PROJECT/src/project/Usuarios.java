package project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public final class Usuarios {
    public static ArrayList<LogicaUsuario> users = new ArrayList<>();
    public static LogicaUsuario usuarioLogeado = null;

    public static boolean validarUserVisual() {
        JTextField userField = new JTextField(14);
        JPasswordField passField = new JPasswordField(14);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; panel.add(userField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; panel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; panel.add(passField, gbc);
        int result = JOptionPane.showConfirmDialog(null, panel, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return false;
        String user = userField.getText();
        String pass = new String(passField.getPassword());
        if (user.trim().isEmpty() || pass.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Usuario o Contraseña vacíos");
            return false;
        }
        for (LogicaUsuario e : users) {
            if (e.getUser().equals(user)) {
                if (e.getPass().equals(pass)) {
                    JOptionPane.showMessageDialog(null, "Bienvenido " + e.getNombre());
                    usuarioLogeado = e;
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "La contraseña es errónea");
                    return false;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Usuario no encontrado");
        return false;
    }

    public static int indiceUsuarios(String user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUser().equals(user)) return i;
        }
        return -1;
    }

    public static void editarUsuario(int index, String tipo, String nombre, String user, String pass, int edad) {
        if (index != -1) {
            LogicaUsuario updatedUser = null;
            switch (tipo) {
                case "Administrador": updatedUser = new UsuarioAdmin(nombre, user, pass, edad); break;
                case "Contenidos": updatedUser = new UsuarioContenidos(nombre, user, pass, edad); break;
                case "Limitado": updatedUser = new UsuarioLimitado(nombre, user, pass, edad); break;
            }
            if (updatedUser != null) {
                users.set(index, updatedUser);
                JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente.");
            }
        }
    }

    public static void eliminarUsuario(String user) {
        int i = indiceUsuarios(user);
        if (i != -1) {
            if (users.get(i).getUser().equals(usuarioLogeado.getUser())) {
                JOptionPane.showMessageDialog(null, "No puedes eliminar al usuario actualmente logeado.");
                return;
            }
            users.remove(i);
            JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
        }
    }

    public static void addUser(String tipo, String nombre, String user, String pass, int edad) {
        if (indiceUsuarios(user) != -1) {
            JOptionPane.showMessageDialog(null, "Este usuario ya está en existencia.");
            return;
        }
        if (pass.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$")) {
            LogicaUsuario newUser = null;
            switch (tipo) {
                case "Administrador": newUser = new UsuarioAdmin(nombre, user, pass, edad); break;
                case "Contenidos": newUser = new UsuarioContenidos(nombre, user, pass, edad); break;
                case "Limitado": newUser = new UsuarioLimitado(nombre, user, pass, edad); break;
            }
            if (newUser != null) {
                users.add(newUser);
                JOptionPane.showMessageDialog(null, "Se añadió correctamente el nuevo usuario.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "La contraseña debe contener mínimo 8 caracteres, una letra, un número y un caracter especial.");
        }
    }
    
    public static boolean esAdmin() {
        return usuarioLogeado != null && "Administrador".equals(usuarioLogeado.getTipo());
    }
    
    public static boolean esLimitado() {
        return usuarioLogeado != null && "Limitado".equals(usuarioLogeado.getTipo());
    }
}