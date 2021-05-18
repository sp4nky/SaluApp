package Dominio;


public class dModelosRepuestos {

    private int id;
    private String nombre;
    private dMarcasRepuestos marca;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public dMarcasRepuestos getMarca() {
        return marca;
    }

    public void setMarca(dMarcasRepuestos marca) {
        this.marca = marca;
    }

    public dModelosRepuestos(int id, String nombre, dMarcasRepuestos marca) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
    }
}
