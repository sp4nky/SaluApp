package Dominio;


public class dModelo {

    private int idModelo;
    private String nombre;
    private dMarca marca;
    private dTipo tipo;

    public dTipo getTipo() {
        return tipo;
    }

    public void setTipo(dTipo tipo) {
        this.tipo = tipo;
    }

    public dMarca getMarca() {
        return marca;
    }

    public void setMarca(dMarca marca) {
        this.marca = marca;
    }

    public int getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(int idModelo) {
        this.idModelo = idModelo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public dModelo(int idModelo, String nombre, dMarca marca, dTipo tipo) {
        this.idModelo = idModelo;
        this.nombre = nombre;
        this.marca = marca;
        this.tipo = tipo;
    }

    public dModelo(int idModelo, String nombre) {
        this.idModelo = idModelo;
        this.nombre = nombre;
    }
}
