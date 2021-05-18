package Dominio;

import java.io.File;

public class dEmpresa {

    private int id;
    private String nombre;
    private String direccion;
    private String localidad;
    private String estado;
    private String telefono;
    private String correo;
    private String web;
    private String rut;
    private File logo;
    private String fax;
    private String celular;
    private String codigoPostal;
    private String logoNombre;

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public File getLogo() {
        return logo;
    }

    public void setLogo(File logo) {
        this.logo = logo;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getLogoNombre() {
        return logoNombre;
    }

    public void setLogoNombre(String logoNombre) {
        this.logoNombre = logoNombre;
    }

    public dEmpresa(int id, String nombre, String direccion, String localidad, String estado, String telefono, String correo, String web, String rut, File logo, String fax, String celular, String codigoPostal, String logoNombre) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.localidad = localidad;
        this.estado = estado;
        this.telefono = telefono;
        this.correo = correo;
        this.web = web;
        this.rut = rut;
        this.logo = logo;
        this.fax = fax;
        this.celular = celular;
        this.codigoPostal = codigoPostal;
        this.logoNombre = logoNombre;
    }
}
