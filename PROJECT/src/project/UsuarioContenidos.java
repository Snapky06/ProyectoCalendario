package project;

public class UsuarioContenidos extends LogicaUsuario {
    public UsuarioContenidos(String nombre, String user, String pass, int edad) {
        super("Contenidos", nombre, user, pass, edad);
    }
    
    @Override
    public String getPerfil() {
        return "--- PERFIL DE CONTENIDOS ---\n" +
               "Nombre: " + nombre + "\nUsername: " + user +
               "\nEdad: " + edad + "\nTipo: " + tipo;
    }
}