package Dominio;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import Persistencia.MySQL;
import Persistencia.pAdjuntos;
import Persistencia.pCliente;
import Persistencia.pEmpresa;
import Persistencia.pFuncionario;
import Persistencia.pGrupo;
import Persistencia.pHistorial;
import Persistencia.pMarca;
import Persistencia.pModelo;
import Persistencia.pNotas;
import Persistencia.pRepuestos;
import Persistencia.pSolicitud;
import Persistencia.pStock;
import Persistencia.pTipo;

public class dControladora implements Serializable {

    private MySQL mysql;
    private pCliente pcliente;
    private pFuncionario pfuncionario;
    private pGrupo pgrupo;
    private pMarca pmarca;
    private pModelo pmodelo;
    private pSolicitud psolicitud;
    private pStock pstock;
    private pTipo ptipo;
    private pNotas pnotas;
    private pEmpresa pempresa;
    private pAdjuntos padjuntos;
    private pRepuestos prepuestos;
    private pHistorial phistorial;

    private static dControladora instance;

    public String usuarioBD;
    public String contrBD;
    public String ipBD;
    public String puertoBD;
    public String catalogoBD;


    public dControladora() {

        usuarioBD= "root";
        contrBD= "sasa";
        ipBD= "10.0.2.2";
        puertoBD= "3306";
        catalogoBD= "";
        mysql = new MySQL(usuarioBD,contrBD,ipBD,puertoBD);
        pfuncionario = new pFuncionario(mysql);
        pgrupo = new pGrupo(mysql);
        pmarca = new pMarca(mysql);
        pmodelo = new pModelo(mysql);
        psolicitud = new pSolicitud(mysql);
        pstock = new pStock(mysql);
        ptipo = new pTipo(mysql);
        prepuestos = new pRepuestos(mysql);
        phistorial= new pHistorial(mysql);

        pcliente = new pCliente(mysql);
        padjuntos = new pAdjuntos(mysql);
        pnotas = new pNotas(mysql);

    }

    public dControladora(String usuarioBD, String contrBD, String ipBD, String puertoBD) {
        this.catalogoBD= "";
        mysql = new MySQL(usuarioBD,contrBD,ipBD,puertoBD);
        this.usuarioBD=usuarioBD;
        this.contrBD=contrBD;
        this.puertoBD=puertoBD;
        this.ipBD=ipBD;
        pfuncionario = new pFuncionario(mysql);
        pgrupo = new pGrupo(mysql);
        pmarca = new pMarca(mysql);
        pmodelo = new pModelo(mysql);
        psolicitud = new pSolicitud(mysql);
        pstock = new pStock(mysql);
        ptipo = new pTipo(mysql);
        phistorial = new pHistorial(mysql);
        prepuestos = new pRepuestos(mysql);

        pcliente = new pCliente(mysql);
        padjuntos = new pAdjuntos(mysql);
        pnotas = new pNotas(mysql);
    }

    public static dControladora getInstance()
    {
        if(instance == null)
            instance = new dControladora();
        return instance;
    }



    public static dControladora getInstance(String usuarioBD, String contrBD, String ipBD, String puertoBD)
    {
        if(instance == null)
            instance = new dControladora(usuarioBD,contrBD,ipBD,puertoBD);
        return instance;
    }

    public MySQL getMysql() {
        return mysql;
    }

    public void abrirConexion(){
        mysql.conectarBDMySQL(usuarioBD, contrBD, ipBD, puertoBD, "");
    }

    public dFuncionario buscarFuncionario(String correo){
        return pfuncionario.buscar(correo);
    }

    public dFuncionario buscarFuncionarioParaLogin(String correo){
        return pfuncionario.buscarParaLogin(correo);
    }

    public boolean marcarSolicitudResuelta(dSolicitud solicitud) {
        try {
            psolicitud.marcarComoResuelta(solicitud);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public ArrayList<dSolicitud> listarSolicitudesFuncionario(dFuncionario funcionario) {
        return psolicitud.listarSolicitudesFuncionario(funcionario);
    }

    public dSolicitud buscarSolicitud(int idSolicitud) {
        return psolicitud.buscar(idSolicitud);
    }

    public ArrayList<dRepuestos> listarRepuestosSolicitud(dSolicitud solicitud) {
        return prepuestos.listarRepuestosSolicitud(solicitud);
    }

    public boolean agregarHistorial(dHistorial historial) {
        try {
            phistorial.alta(historial);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean asignarComentarioHablado(dSolicitud solicitud) {
        try {
            psolicitud.agregarComentarioHablado(solicitud);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean agregarNota(dNotas nota) {

            return pnotas.alta(nota);

    }

    public ArrayList<dFuncionario> listarFuncionariosSolicitud(dSolicitud solicitud) {
        return pfuncionario.listarFuncionariosSolicitud(solicitud);
    }

    public ArrayList<dSolicitud> listarSolicitudes() {
        return psolicitud.listarTodos();
    }

    public ArrayList<dHistorial> listarHistorialSolicitud(dSolicitud solicitud) {
        return phistorial.listarHistorialSolicitud(solicitud);
    }

    public void asignarSolicitudFuncionario(dSolicitud solicitud, dFuncionario funcionario) throws SQLException {
        psolicitud.asignarFuncionarioSolicitud(solicitud,funcionario);
    }

    public ArrayList<dSolicitud> listarTodosNoAsignadasFuncionario(dFuncionario funcionario){
        return psolicitud.listarTodosNoAsignadasFuncionario(funcionario);
    }

    public ArrayList<dStock> listarStock() {
        return pstock.listarTodos();
    }

    public ArrayList<dRepuestos> listarTodosRepuestos() {
        return prepuestos.listarTodos();

    }

    public void agregarRepuestoSolicitud(dRepuestos repuesto, dSolicitud solicitud, int cantidad) {
        psolicitud.asignarRepuestoSolicitud(solicitud,repuesto,cantidad);
    }
}
