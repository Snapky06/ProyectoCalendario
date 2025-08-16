package project;

public class UsuarioAdmin extends LogicaUsuario {
    public UsuarioAdmin(String nombre, String user, String pass, int edad) {
        super("Administrador", nombre, user, pass, edad);
    }

    @Override
    public String getPerfil() {
        return "--- PERFIL DE ADMINISTRADOR ---\n" +
               "Nombre: " + nombre + "\nUsername: " + user +
               "\nEdad: " + edad + "\nTipo: " + tipo;
    }
}
