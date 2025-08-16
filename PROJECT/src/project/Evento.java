package project;

import java.time.LocalDate;

public abstract class Evento {
    protected String codigo, titulo, descripcion, creador;
    protected LocalDate fecha;
    protected double montoRenta;
    protected boolean cancelado;
    protected double multaPagada;

    public Evento(String c, String t, String d, LocalDate f, double m, String cr) {
        this.codigo = c;
        this.titulo = t;
        this.descripcion = d;
        this.fecha = f;
        this.montoRenta = m;
        this.creador = cr;
        this.cancelado = false;
        this.multaPagada = 0;
    }

    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public LocalDate getFecha() { return fecha; }
    public double getMontoRenta() { return montoRenta; }
    public boolean isCancelado() { return cancelado; }
    public void setCancelado(boolean cancelado) { this.cancelado = cancelado; }
    public String getCreador() { return creador; }
    public double getMultaPagada() { return multaPagada; }
    public void setMultaPagada(double multa) { this.multaPagada = multa; }

    public abstract String getDetalles();
    public abstract double getMultaCancelacion();
}
