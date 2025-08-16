package project;

public class UsuarioLimitado extends LogicaUsuario {
    public UsuarioLimitado(String nombre, String user, String pass, int edad) {
        super("Limitado", nombre, user, pass, edad);
    }

    @Override
    public String getPerfil() {
        return "--- PERFIL DE USUARIO LIMITADO ---\n" +
               "Nombre: " + nombre + "\nUsername: " + user +
               "\nEdad: " + edad + "\nTipo: " + tipo;
    }
}