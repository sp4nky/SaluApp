package Dominio;


import java.io.File;

public class dRepuestos {

    private int id;
    private String NroParte;
    private String CodigoBarras;
    private dModelosRepuestos modelo;
    private File foto;
    private dFamilias familia;
    private int cantidad;
    private String nombre;
    private String fotoExt;
    private String fotoNombre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNroParte() {
        return NroParte;
    }

    public void setNroParte(String nroParte) {
        NroParte = nroParte;
    }

    public String getCodigoBarras() {
        return CodigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        CodigoBarras = codigoBarras;
    }

    public dModelosRepuestos getModelo() {
        return modelo;
    }

    public void setModelo(dModelosRepuestos modelo) {
        this.modelo = modelo;
    }

    public File getFoto() {
        return foto;
    }

    public void setFoto(File foto) {
        this.foto = foto;
    }

    public dFamilias getFamilia() {
        return familia;
    }

    public void setFamilia(dFamilias familia) {
        this.familia = familia;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFotoExt() {
        return fotoExt;
    }

    public void setFotoExt(String fotoExt) {
        this.fotoExt = fotoExt;
    }

    public String getFotoNombre() {
        return fotoNombre;
    }

    public void setFotoNombre(String fotoNombre) {
        this.fotoNombre = fotoNombre;
    }

    public dRepuestos(int id, String nroParte, String codigoBarras, dModelosRepuestos modelo, File foto, dFamilias familia, int cantidad, String nombre, String fotoExt, String fotoNombre) {
        this.id = id;
        NroParte = nroParte;
        CodigoBarras = codigoBarras;
        this.modelo = modelo;
        this.foto = foto;
        this.familia = familia;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.fotoExt = fotoExt;
        this.fotoNombre = fotoNombre;
    }
}
