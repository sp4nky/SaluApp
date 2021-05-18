package Dominio;


import java.io.File;

public class dStock {
//Stock sera tomado como equipos
    private int idStock;
    private String nombre;
    private int cantidad;
    private dModelo modelo;
    private dTipo tipo;
   // private dMarca marca;

    private File foto;
    private String fotoNombre;
    private String nroSerie;
    private String codigoBarras;
    private String fotoExt;

    public String getFotoExt() {
        return fotoExt;
    }

    public void setFotoExt(String fotoExt) {
        this.fotoExt = fotoExt;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public File getFoto() {
        return foto;
    }

    public void setFoto(File foto) {
        this.foto = foto;
    }

    public String getFotoNombre() {
        return fotoNombre;
    }

    public void setFotoNombre(String fotoNombre) {
        this.fotoNombre = fotoNombre;
    }

    public String getNroSerie() {
        return nroSerie;
    }

    public void setNroSerie(String nroSerie) {
        this.nroSerie = nroSerie;
    }

    public int getIdStock() {
        return idStock;
    }

    public void setIdStock(int idStock) {
        this.idStock = idStock;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public dModelo getModelo() {
        return modelo;
    }

    public void setModelo(dModelo modelo) {
        this.modelo = modelo;
    }

    public dTipo getTipo() {
        return tipo;
    }

    public void setTipo(dTipo tipo) {
        this.tipo = tipo;
    }

    public dStock(int idStock, dModelo modelo, String nroSerie, File foto, String fotoNombre, String fotoExt) {
        this.idStock = idStock;
        this.modelo = modelo;
        this.nroSerie = nroSerie;
        this.foto = foto;
        this.fotoNombre = fotoNombre;
        this.fotoExt= fotoExt;
    }

}
