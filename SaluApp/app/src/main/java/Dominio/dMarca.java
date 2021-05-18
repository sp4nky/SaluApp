package Dominio;


public class dMarca {

    private int idMarca;
    private String nombre;

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public dMarca(int idMarca, String nombre) {
        this.idMarca = idMarca;
        this.nombre = nombre;
    }
}
