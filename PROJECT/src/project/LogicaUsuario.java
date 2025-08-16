package project;

import java.util.ArrayList;

public abstract class LogicaUsuario {
    protected String tipo;
    protected String nombre;
    protected String user;
    protected String pass;
    protected int edad;
    protected ArrayList<String> eventosCreados;

    public LogicaUsuario(String tipo, String nombre, String user, String pass, int edad) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.user = user;
        this.pass = pass;
        this.edad = edad;
        if (!"Limitado".equals(this.tipo)) {
            this.eventosCreados = new ArrayList<>();
        }
    }

    public String getUser() { return user; }
    public String getPass() { return pass; }
    public String getTipo() { return tipo; }
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public ArrayList<String> getEventosCreados() { return eventosCreados; }

    public abstract String getPerfil();
    
    public final void agregarEventoCreado(String codigoEvento) {
        if (eventosCreados != null) {
            eventosCreados.add(codigoEvento);
        }
    }
}
