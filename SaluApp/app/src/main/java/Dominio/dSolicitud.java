package Dominio;


import java.io.File;
import java.util.Date;

public class dSolicitud {

    private int idSolicitud;
    private dCliente cliente;
    private String cuenta;
    private Date fecha;//fehca prometida
    private String departamento;
    private String ciudad;
    private String direccion;
    private String esquina;
    private String diagnostico;//falla

    private dGrupo grupo;
    private Date fechaIngreso;
    private Date fechaFinalizado;
    private String tipo;
    private dFuncionario funcionario;
    private String informe;
    private String usuario;
    private String accesorios;
    private dEstado estado;
    private File comentarioHablado;
    private String comentarioHabladoNombre;
    private int IncidenciasEquiposCantidad;


    public File getComentarioHablado() {
        return comentarioHablado;
    }

    public void setComentarioHablado(File comentarioHablado) {
        this.comentarioHablado = comentarioHablado;
    }

    public String getComentarioHabladoNombre() {
        return comentarioHabladoNombre;
    }

    public void setComentarioHabladoNombre(String comentarioHabladoNombre) {
        this.comentarioHabladoNombre = comentarioHabladoNombre;
    }

    public int getIncidenciasEquiposCantidad() {
        return IncidenciasEquiposCantidad;
    }

    public void setIncidenciasEquiposCantidad(int incidenciasEquiposCantidad) {
        IncidenciasEquiposCantidad = incidenciasEquiposCantidad;
    }

    public dEstado getEstado() {
        return estado;
    }

    public void setEstado(dEstado estado) {
        this.estado = estado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public dGrupo getGrupo() {
        return grupo;
    }

    public void setGrupo(dGrupo grupo) {
        this.grupo = grupo;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaFinalizado() {
        return fechaFinalizado;
    }

    public void setFechaFinalizado(Date fechaFinalizado) {
        this.fechaFinalizado = fechaFinalizado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public dFuncionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(dFuncionario funcionario) {
        this.funcionario = funcionario;
    }

    public String getInforme() {
        return informe;
    }

    public void setInforme(String informe) {
        this.informe = informe;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(String accesorios) {
        this.accesorios = accesorios;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public dCliente getCliente() {
        return cliente;
    }

    public void setCliente(dCliente cliente) {
        this.cliente = cliente;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEsquina() {
        return esquina;
    }

    public void setEsquina(String esquina) {
        this.esquina = esquina;
    }

    public String getciudad() {
        return ciudad;
    }

    public void setciudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public dSolicitud(int idSolicitud, dCliente cliente, Date fecha, String ciudad, String direccion, dGrupo grupo, Date fechaIngreso, Date fechaFinalizado, dFuncionario funcionario, String informe, String usuario, String accesorios, File comentarioHablado, String comentarioHabladoNombre, int incidenciasEquiposCantidad, dEstado estado, String tipo) {
        this.idSolicitud = idSolicitud;
        this.cliente = cliente;
        this.fecha = fecha;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.grupo = grupo;
        this.fechaIngreso = fechaIngreso;
        this.fechaFinalizado = fechaFinalizado;
        this.funcionario = funcionario;
        this.informe = informe;
        this.usuario = usuario;
        this.accesorios = accesorios;
        this.comentarioHablado = comentarioHablado;
        this.comentarioHabladoNombre = comentarioHabladoNombre;
        IncidenciasEquiposCantidad = incidenciasEquiposCantidad;
        this.estado = estado;
        this.tipo = tipo;
    }

    public dSolicitud(int idSolicitud, dCliente cliente, String cuenta, Date fecha, String departamento, String ciudad, String direccion, String esquina, String diagnostico) {
        this.idSolicitud = idSolicitud;
        this.cliente = cliente;
        this.cuenta = cuenta;
        this.fecha = fecha;
        this.departamento = departamento;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.esquina = esquina;
        this.diagnostico = diagnostico;
    }
}
