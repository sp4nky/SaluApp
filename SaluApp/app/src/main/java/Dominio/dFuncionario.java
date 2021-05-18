package Dominio;


import java.io.File;
import java.util.Date;

public class dFuncionario {
    private int idFuncionario;
    private String nombre;
    private String apellido;
    private dGrupo grupo;
    private String correo;
    private String contr;
    private String telefono;

    private String direccion;
    private String localidad;
    private String estado;
    private String celular;
    private String CI;
    private Date fchNacimiento;
    private File foto;
    private String cargo;
    private Date fchIngreso;

    public Date getFchIngreso() {
        return fchIngreso;
    }

    public void setFchIngreso(Date fchIngreso) {
        this.fchIngreso = fchIngreso;
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

    public Date getFchNacimiento() {
        return fchNacimiento;
    }

    public void setFchNacimiento(Date fchNacimiento) {
        this.fchNacimiento = fchNacimiento;
    }

    public File getFoto() {
        return foto;
    }

    public void setFoto(File foto) {
        this.foto = foto;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public dGrupo getGrupo() {
        return grupo;
    }

    public void setGrupo(dGrupo grupo) {
        this.grupo = grupo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContr() {
        return contr;
    }

    public void setContr(String contr) {
        this.contr = contr;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public dFuncionario(int idFuncionario, String nombre, dGrupo grupo, String correo, String contr, String telefono, String direccion, String localidad, String estado, String celular, String CI, Date fchNacimiento, File foto, String cargo, Date fchIngreso) {
        this.idFuncionario = idFuncionario;
        this.nombre = nombre;
        this.grupo = grupo;
        this.correo = correo;
        this.contr = contr;
        this.telefono = telefono;
        this.direccion = direccion;
        this.localidad = localidad;
        this.estado = estado;
        this.celular = celular;
        this.CI = CI;
        this.fchNacimiento = fchNacimiento;
        this.foto = foto;
        this.cargo = cargo;
        this.fchIngreso = fchIngreso;
    }

    public dFuncionario(int idFuncionario, String nombre, String apellido, dGrupo grupo, String correo, String contr, String telefono) {
        this.idFuncionario = idFuncionario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.grupo = grupo;
        this.correo = correo;
        this.contr = contr;
        this.telefono = telefono;
    }
}
