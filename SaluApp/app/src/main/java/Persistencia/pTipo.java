package Persistencia;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Dominio.dTipo;

public class pTipo{

    private MySQL mysql;
    private Connection conn;

    public pTipo(MySQL mysql) {
        this.mysql = mysql;
    }

    /*
    public void alta(dTipo m) throws SQLException {
        ResultSet rs= mysql.ejecutarSentencia("INSERT INTO salu.tipos(nombre) " +
                "VALUES ('" + m.getNombre() + "')");
        rs.close();

    }
*/
    public dTipo buscar(String nombre)
    {
        dTipo m= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.tiposequipos.* FROM salu.tiposequipos WHERE TiposEquiposNombre='" + nombre + "'");
            while (rs.next()) {
                int idTipo = rs.getInt("TiposEquiposId");
                m = new dTipo(idTipo, nombre);
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

    public dTipo buscar(int id)
    {
        dTipo m= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.tiposequipos.* FROM salu.tiposequipos WHERE TiposEquiposId=" + id);
            while (rs.next()) {
                String nombre = rs.getNString("TiposEquiposNombre");
                m = new dTipo(id, nombre);
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
    public void eliminar(dTipo g) throws SQLException {
        ResultSet rs= mysql.ejecutarSentencia("DELETE FROM salu.tipos WHERE idtipos=" + g.getIdTipo() + " OR nombre='" + g.getNombre() + "'");
        rs.close();
    }
*/
    public ArrayList<dTipo> listarTodos() {
        ArrayList<dTipo> lista = new ArrayList<dTipo>();
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.tiposequipos.* FROM salu.tiposequipos");
            while (rs.next()) {
                int idTipo = rs.getInt("TiposEquiposId");
                String nombre = rs.getNString("TiposEquiposNombre");
                dTipo m = new dTipo(idTipo, nombre);
                lista.add(m);
            }
            rs.close();

            return lista;
        } catch (SQLException e) {
            return null;
        }
    }
}
