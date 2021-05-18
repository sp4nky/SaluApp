package Persistencia;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import Dominio.dCliente;
import Dominio.dEstado;
import Dominio.dFuncionario;
import Dominio.dGrupo;
import Dominio.dRepuestos;
import Dominio.dSolicitud;
import Dominio.dStock;

public class pSolicitud{

    private MySQL mysql;
    private Connection conn;
    private pCliente pcliente;
    private dCliente cliente;
    private dFuncionario funcionario;
    private dEstado estado;
    private pEstado pestado;
    private pFuncionario pfuncionario;
    private dGrupo grupo;
    private pGrupo pgrupo;
    private pStock pstock;
    private dStock stock;

    public pSolicitud(MySQL mysql) {
        this.mysql=mysql;
        conn=mysql.getConexionMySQL();
        pcliente= new pCliente(mysql);
        pestado = new pEstado(mysql);
        pgrupo = new pGrupo(mysql);
        pfuncionario =  new pFuncionario(mysql);
        pstock= new pStock(mysql);
    }
/*
    public void alta(dSolicitud s) throws SQLException
    {
        java.sql.Date fechasql= new java.sql.Date(s.getFecha().getTime());
        ResultSet rs= mysql.ejecutarSentencia("INSERT INTO salu.incidencias(idcliente, cuenta, fecha, departamento, ciudad, direccion, esquina, diagnostico) " +
                "VALUES (" + s.getCliente().getIdCliente() + ", '" + s.getCuenta() + "', " + fechasql + ", '" + s.getDepartamento() + "', '" + s.getciudad() + "', '" + s.getDireccion() + "', '" + s.getEsquina() + "', '" + s.getDiagnostico() + "')");
        rs.close();
    }
*/
    public dSolicitud buscar(int id)
    {
        dSolicitud s= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.incidencias.* FROM salu.incidencias WHERE IncidenciasId='" + id + "'");
            if(rs.next()) {
                int idSolicitud = rs.getInt("IncidenciasId");
                int idcliente = rs.getInt("ClientesId");
                java.util.Date fecha = rs.getDate("IncidenciasFechaPrometido");
                String ciudad = rs.getNString("IncidenciasLocalidad");
                String direccion = rs.getNString("IncidenciasDireccion");

                int idgrupo = rs.getInt("IncidenciasGruposId");
                grupo= pgrupo.buscar(idgrupo);
                Date fechaIngreso = rs.getDate("IncidenciasFechaIngreso");
                Date fechaFinalizado = rs.getDate("IncidenciasFechaFinalizado");;
                String tipo = rs.getNString("IncidenciasTipo");
                int idFuncionario = rs.getInt("IncidenciasFuncionariosId");
                funcionario= pfuncionario.buscar(idFuncionario);
                String informe = rs.getNString("IncidenciasInformeCliente");
                String usuario = rs.getNString("IncidenciasUsuario");
                String accesorios = rs.getNString("IncidenciasAccesorios");
                int idestado= rs.getInt("EstadosId");
                estado = pestado.buscar(idestado);
                String comentarioHabladoNombre = rs.getNString("IncidenciasComentarioHabladoNo");
                String falla= rs.getNString("IncidenciasFalla");


                int IncidenciasEquiposCantidad= rs.getInt("IncidenciasEquiposCantidad");

                cliente = pcliente.buscar(idcliente);
                s = new dSolicitud(idSolicitud,cliente,fecha,ciudad,direccion,grupo,fechaIngreso,fechaFinalizado,funcionario,informe,usuario,accesorios,null,comentarioHabladoNombre,IncidenciasEquiposCantidad,estado,tipo);
                s.setDiagnostico(falla);
            }
            rs.close();
        }
        catch (SQLException e) {
            s=null;


        }
        finally {
            return s;
        }
    }

    public boolean asignarRepuestoSolicitud(dSolicitud s, dRepuestos repuestos, int cantidad)
    {
            int rs= mysql.updateSentencia("INSERT INTO salu.incidenciarepuestos(IncidenciaRepuestosId, IncidenciasId, IncidenciaRepuestosNroParte, IncidenciaRepuestosNombre, IncidenciaRepuestosCantidad) " +
                    "VALUES (" + repuestos.getId() + ", " + s.getIdSolicitud() + ", '" + repuestos.getNroParte() + "', '" + repuestos.getNombre() + "', " + cantidad + ")");

            return true;

    }

    public ArrayList<dSolicitud> listarTodos()
    {
        ArrayList<dSolicitud> lista= new ArrayList<dSolicitud>();
        try {
            ResultSet rs= mysql.ejecutarSentencia("SELECT salu.incidencias.* FROM salu.incidencias");

            while (rs.next()) {
                int idSolicitud = rs.getInt("IncidenciasId");
                int idcliente = rs.getInt("ClientesId");
                String ciudad = rs.getNString("IncidenciasLocalidad");
                String tipo = rs.getNString("IncidenciasTipo");
                cliente = pcliente.buscar(idcliente);
                int idestado= rs.getInt("EstadosId");
                estado = pestado.buscar(idestado);
                dSolicitud s = new dSolicitud(idSolicitud, cliente, null, ciudad, "", grupo, null, null, funcionario, "", "", "", null, "", 0, estado, tipo);
                lista.add(s);
            }

            rs.close();
            return lista;
        }
        catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<dSolicitud> listarTodosNoAsignadasFuncionario(dFuncionario funcionario)
    {
        ArrayList<dSolicitud> lista= new ArrayList<dSolicitud>();
        try {
            ResultSet rs= mysql.ejecutarSentencia("select salu.incidencias.* from salu.incidencias where (salu.incidencias.IncidenciasFuncionariosId IS NULL AND salu.incidencias.IncidenciasGruposId IS NULL) AND salu.incidencias.EstadosId <> 3");

            while (rs.next()) {
                int idSolicitud = rs.getInt("IncidenciasId");
                int idcliente = rs.getInt("ClientesId");
                String ciudad = rs.getNString("IncidenciasLocalidad");
                String tipo = rs.getNString("IncidenciasTipo");
                cliente = pcliente.buscar(idcliente);
                int idestado= rs.getInt("EstadosId");
                estado = pestado.buscar(idestado);
                dSolicitud s = new dSolicitud(idSolicitud, cliente, null, ciudad, "", null, null, null, null, "", "", "", null, "", 0, estado, tipo);
                lista.add(s);
            }

            rs.close();;
            return lista;
        }
        catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<dSolicitud> listarSolicitudesFuncionario(dFuncionario f) {
        ArrayList<dSolicitud> lista = new ArrayList<dSolicitud>();
        try {
            ResultSet rs = mysql.ejecutarSentencia("select distinct salu.incidencias.* from salu.incidencias,salu.grupos, salu.funcionarios where salu.incidencias.IncidenciasFuncionariosId=" + f.getIdFuncionario() + " OR salu.incidencias.IncidenciasGruposId=salu.grupos.GruposId AND salu.grupos.GruposId=salu.funcionarios.GruposId AND salu.incidencias.IncidenciasFuncionariosId = "+ f.getIdFuncionario());

            while (rs.next()) {
                int idSolicitud = rs.getInt("IncidenciasId");
                int idcliente = rs.getInt("ClientesId");
                String ciudad = rs.getNString("IncidenciasLocalidad");
                String tipo = rs.getNString("IncidenciasTipo");
                cliente = pcliente.buscar(idcliente);
                int idestado= rs.getInt("EstadosId");
                estado = pestado.buscar(idestado);
                dSolicitud s = new dSolicitud(idSolicitud, cliente, null, ciudad, "", null, null, null, null, "", "", "", null, "", 0, estado, tipo);
                lista.add(s);
            }

            rs.close();
            return lista;
        } catch (SQLException e) {
            return null;
        }
    }


    public void asignarFuncionarioSolicitud(dSolicitud s, dFuncionario f) throws SQLException
    {

        int rs= mysql.updateSentencia("UPDATE salu.incidencias SET salu.incidencias.IncidenciasFuncionariosId = " + f.getIdFuncionario() + " WHERE salu.incidencias.IncidenciasId =" + s.getIdSolicitud());


    }

    public void agregarComentarioHablado(dSolicitud s) throws SQLException
    {
        try {
            InputStream comentario=null;
            if(s.getComentarioHablado()!=null)
                comentario= new FileInputStream(s.getComentarioHablado());
            int rs= mysql.updateSentencia("UPDATE salu.incidencias SET salu.incidencias.IncidenciasComentarioHablado =" + comentario + ", salu.incidencias.IncidenciasComentarioHabladoEx= '3gp', salu.incidencias.IncidenciasComentarioHabladoNo= '" + s.getComentarioHabladoNombre() + "' WHERE salu.incidencias.IncidenciasId =" + s.getIdSolicitud());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void marcarComoResuelta(dSolicitud s) throws SQLException {

            java.sql.Date fechasql= new java.sql.Date(s.getFecha().getTime());
            int rs= mysql.updateSentencia("UPDATE salu.incidencias SET salu.incidencias.IncidenciasFechaFinalizado ='" + fechasql + "', salu.incidencias.EstadosId = 3 WHERE salu.incidencias.IncidenciasId =" + s.getIdSolicitud());

    }
    
}

