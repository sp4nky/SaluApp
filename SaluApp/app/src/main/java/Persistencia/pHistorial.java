package Persistencia;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Dominio.dHistorial;
import Dominio.dSolicitud;

public class pHistorial  {


    private MySQL mysql;
    private Connection conn;
    private pSolicitud psolicitud;
    private dSolicitud solicitud;

    public pHistorial(MySQL mysql) {

        this.mysql = mysql;
        psolicitud= new pSolicitud(mysql);
    }

    public void alta(dHistorial historial) throws SQLException {
        java.sql.Date fechasql= new java.sql.Date(historial.getFecha().getTime());
        int rs= mysql.updateSentencia("INSERT INTO salu.historial(HistorialFecha,HistorialIncidenciaId,HistorialUsuario,HistorialDescripcion) " +
                "VALUES ('" + fechasql.toString() + "', " + historial.getSolicitud().getIdSolicitud() + ", '" + historial.getUsuario() + "', '" + historial.getDescripcion() + "')");


    }
/*
    public boolean modificar(dHistorial historial)
    {
        java.sql.Date fechasql= new java.sql.Date(historial.getFecha().getTime());
        ResultSet rs= mysql.ejecutarSentencia("UPDATE salu.historial(HistorialFecha,HistorialIncidenciaId,HistorialUsuario,HistorialDescripcion) SET" +
                "HistorialFecha="+ fechasql + ", Historial");
        try {
            rs.close();
        } catch (SQLException e) {
            return false;
        }
    }
*/
    public ArrayList<dHistorial> listarHistorialSolicitud(dSolicitud solicitud)
    {
        ArrayList<dHistorial> list = new ArrayList<dHistorial>();
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.historial.* FROM salu.historial WHERE HistorialIncidenciaId=" + solicitud.getIdSolicitud());
            while (rs.next()) {
                int id = rs.getInt("HistorialId");
                int idSolicitud = rs.getInt("HistorialIncidenciaId");
                String texto = rs.getNString("HistorialDescripcion");
                java.util.Date fecha = rs.getDate("HistorialFecha");
                String usuario = rs.getString("HistorialUsuario");
                solicitud= psolicitud.buscar(idSolicitud);
                dHistorial m = new dHistorial(id, fecha, solicitud, usuario, texto);
                list.add(m);
            }
            rs.close();
        }
        catch (SQLException e) {
            list=null;
        }
        finally {

            return list;
        }
    }

    public dHistorial buscar(int id)
    {
        dHistorial m= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.historial.* FROM salu.historial WHERE HistorialId=" + id);
            while (rs.next()) {
                int idSolicitud = rs.getInt("HistorialIncidenciaId");
                String texto = rs.getNString("HistorialDescripcion");
                java.util.Date fecha = rs.getDate("HistorialFecha");
                String usuario = rs.getString("HistorialUsuario");
                solicitud= psolicitud.buscar(idSolicitud);
                m = new dHistorial(id, fecha, solicitud, usuario, texto);

            }
            rs.close();
        }
        catch (SQLException e) {
            m=null;
        }
        finally {

            return m;
        }
    }

}