package Dominio;


import java.sql.SQLException;

import Persistencia.pCliente;

public class dCliente {
    private int idCliente;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;

    private String localidad;
    private String estado;
    private String direccion;
    private String celular;
    private String CI;
    private String Contacto;

    private pCliente pcliente;

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCI() {
        return CI;
    }

    public void setCI(String CI) {
        this.CI = CI;
    }

    public String getContacto() {
        return Contacto;
    }

    public void setContacto(String contacto) {
        Contacto = contacto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public dCliente(int idCliente, String nombre, String direccion, String localidad, String estado, String telefono, String celular, String correo, String CI, String contacto) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.localidad = localidad;
        this.estado = estado;
        this.telefono = telefono;
        this.celular = celular;
        this.correo = correo;
        this.CI = CI;
        this.Contacto = contacto;
    }

    public dCliente(int idCliente, String nombre, String apellido, String telefono, String correo) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
    }

}
