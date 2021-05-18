package Dominio;

import java.io.File;

public class dAdjuntos {

    private int id;
    private dSolicitud solicitud;
    private File imagen;
    private String descripcion;

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

    public File getImagen() {
        return imagen;
    }

    public void setImagen(File imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public dAdjuntos(int id, dSolicitud solicitud, File imagen, String descripcion) {
        this.id = id;
        this.solicitud = solicitud;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }
}
