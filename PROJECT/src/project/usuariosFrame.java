package project;

import javax.swing.*;

public class usuariosFrame extends FrontEnd {
    private JButton btnCrear = new JButton("Crear Usuario");
    private JButton btnEditar = new JButton("Editar Usuario");
    private JButton btnBorrar = new JButton("Borrar Usuario");
    private JButton btnRegresar = new JButton("Regresar al Menú");
    private JLabel tit = new JLabel("Gestión de Usuarios");

    public usuariosFrame() {
        FrameConFondo(this, cargarFondo("/proyectocal/imagenes/FONDO1.jpg"));
        titulo1(tit);
        JButton[] botones = {btnCrear, btnEditar, btnBorrar, btnRegresar};
        layoutBtn(botones);
        acciones();
        this.setVisible(true);
    }

    public void acciones() {
        btnCrear.addActionListener(e -> new crearUsuarioFrame());
        
        btnEditar.addActionListener(e -> {
            String userToEdit = JOptionPane.showInputDialog(this, "Ingrese el username del usuario a editar:");
            if (userToEdit != null && !userToEdit.trim().isEmpty()) {
                int index = Usuarios.indiceUsuarios(userToEdit.trim());
                if (index != -1) {
                    new EditarFrame(index);
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario no encontrado.");
                }
            }
        });

        btnBorrar.addActionListener(e -> {
            String userToDelete = JOptionPane.showInputDialog(this, "Ingrese el username del usuario a borrar:");
            if (userToDelete != null && !userToDelete.trim().isEmpty()) {
                Usuarios.eliminarUsuario(userToDelete.trim());
            }
        });

        btnRegresar.addActionListener(e -> {
            new MenuFrame().setVisible(true);
            this.dispose();
        });
    }
}