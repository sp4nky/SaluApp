package Dominio;


import java.util.Date;

public class dHistorial {

    private int id;
    private Date fecha;
    private dSolicitud solicitud;
    private String usuario;
    private String descripcion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public dSolicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(dSolicitud solicitud) {
        this.solicitud = solicitud;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public dHistorial(int id, Date fecha, dSolicitud solicitud, String usuario, String descripcion) {
        this.id = id;
        this.fecha = fecha;
        this.solicitud = solicitud;
        this.usuario = usuario;
        this.descripcion = descripcion;
    }
}
