package Persistencia;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Dominio.dEstado;

public class pEstado {

    private MySQL mysql;
    private Connection conn;

    public pEstado(MySQL mysql) {
        this.mysql = mysql;
    }

    /*
    public void alta(dEstado m) throws SQLException {
        ResultSet rs= mysql.ejecutarSentencia("INSERT INTO salu.tipos(nombre) " +
                "VALUES ('" + m.getNombre() + "')");
        rs.close();

    }
*/

    public dEstado buscar(int id)
    {
        dEstado m= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.estados.* FROM salu.estados WHERE EstadosId=" + id);
            while (rs.next()) {
                String nombre = rs.getNString("EstadosNombre");
                String tarea = rs.getNString("EstadosTarea");
                m = new dEstado(id, nombre,tarea);
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
    /*
        public void eliminar(dEstado g) throws SQLException {
            ResultSet rs= mysql.ejecutarSentencia("DELETE FROM salu.tipos WHERE idtipos=" + g.getIdEstado() + " OR nombre='" + g.getNombre() + "'");
            rs.close();
        }
    */
    public ArrayList<dEstado> listarTodos() {
        ArrayList<dEstado> lista = new ArrayList<dEstado>();
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.estados.* FROM salu.estados");
            while (rs.next()) {
                int id = rs.getInt("EstadosId");
                String nombre = rs.getNString("EstadosNombre");
                String tarea = rs.getNString("EstadosTarea");
                dEstado m = new dEstado(id, nombre,tarea);
                lista.add(m);
            }
            rs.close();

            return lista;
        } catch (SQLException e) {
            return null;
        }
    }
}