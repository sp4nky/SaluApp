package Dominio;

import java.util.Date;

public class dNotas {

    private int id;
    private dSolicitud solicitud;
    private String nombreFuncionario;
    private Date fecha;
    private String texto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public dSolicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(dSolicitud solicitud) {
        this.solicitud = solicitud;
    }

    public String getNombreFuncionario() {
        return nombreFuncionario;
    }

    public void setNombreFuncionario(String nombreFuncionario) {
        this.nombreFuncionario = nombreFuncionario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public dNotas(int id, dSolicitud solicitud, String nombreFuncionario, Date fecha, String texto) {
        this.id = id;
        this.solicitud = solicitud;
        this.nombreFuncionario = nombreFuncionario;
        this.fecha = fecha;
        this.texto = texto;
    }
}
