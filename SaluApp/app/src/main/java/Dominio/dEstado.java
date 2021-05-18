package Dominio;

public class dEstado {

    private int id;
    private String nombre;
    private String tarea;

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

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public dEstado(int id, String nombre, String tarea) {
        this.id = id;
        this.nombre = nombre;
        this.tarea = tarea;
    }
}
