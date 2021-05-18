package Dominio;


public class dTipo {

    private int idTipo;
    private String nombre;

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public dTipo(int idTipo, String nombre) {
        this.idTipo = idTipo;
        this.nombre = nombre;
    }
}
