package Persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Dominio.dMarca;

public class pMarca{

    private MySQL mysql;
    private Connection conn;

    public pMarca(MySQL mysql) {
        this.mysql = mysql;
    }
/*

    public void alta(dMarca m) throws SQLException {
        ResultSet rs= mysql.ejecutarSentencia("INSERT INTO salu.marcasequipos(MarcasEquiposNombre) " +
                "VALUES ('" + m.getNombre() + "')");
        rs.close();

    }
*/
    public dMarca buscar(String nombre)
    {
        dMarca m= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.marcasequipos.* FROM salu.marcasequipos WHERE MarcasEquiposNombre='" + nombre + "'");
            while (rs.next()) {
                int idMarca = rs.getInt("MarcasEquiposId");
                m = new dMarca(idMarca, nombre);
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

    public dMarca buscar(int id)
    {
        dMarca m= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.marcasequipos.* FROM salu.marcasequipos WHERE MarcasEquiposId=" + id);
            while (rs.next()) {
                String nombre = rs.getNString("MarcasEquiposNombre");
                m = new dMarca(id, nombre);
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
    public void eliminar(dMarca g) throws SQLException {
        ResultSet rs= mysql.ejecutarSentencia("DELETE FROM salu.marcasequipos WHERE MarcasEquiposId=" + g.getIdMarca() + " OR MarcasEquiposNombre='" + g.getNombre() + "'");
        rs.close();
    }
*/
    public ArrayList<dMarca> listarTodos() {
        ArrayList<dMarca> lista = new ArrayList<dMarca>();
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.marcasequipos.* FROM salu.marcasequipos");

            while (rs.next()) {
                int idMarca = rs.getInt("MarcasEquiposId");
                String nombre = rs.getNString("MarcasEquiposNombre");
                dMarca m = new dMarca(idMarca, nombre);
                lista.add(m);
            }
            rs.close();

            return lista;
        } catch (SQLException e) {
            return null;
        }
    }
}
