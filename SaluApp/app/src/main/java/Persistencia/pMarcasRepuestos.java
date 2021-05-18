package Persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Dominio.dMarcasRepuestos;


public class pMarcasRepuestos{

    private MySQL mysql;
    private Connection conn;

    public pMarcasRepuestos(MySQL mysql) {
        this.mysql = mysql;
    }

/*
    public void alta(dMarcasRepuestos m) throws SQLException {
        ResultSet rs= mysql.ejecutarSentencia("INSERT INTO salu.marcasrepuestos(MarcasRepuestosNombre) " +
                "VALUES ('" + m.getNombre() + "')");
        rs.close();

    }
*/
    public dMarcasRepuestos buscar(String nombre)
    {
        dMarcasRepuestos m= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.marcasrepuestos.* FROM salu.marcasrepuestos WHERE MarcasRepuestosNombre='" + nombre + "'");
            while (rs.next()) {
                int idMarcasRepuestos = rs.getInt("MarcasRepuestosId");
                m = new dMarcasRepuestos(idMarcasRepuestos, nombre);
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

    public dMarcasRepuestos buscar(int id)
    {
        dMarcasRepuestos m= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.marcasrepuestos.* FROM salu.marcasrepuestos WHERE MarcasRepuestosId=" + id);
            while (rs.next()) {
                String nombre = rs.getNString("MarcasRepuestosNombre");
                m = new dMarcasRepuestos(id, nombre);
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
    public void eliminar(dMarcasRepuestos g) throws SQLException {
        ResultSet rs= mysql.ejecutarSentencia("DELETE FROM salu.marcasrepuestos WHERE MarcasRepuestosId=" + g.getId() + " OR MarcasRepuestosNombre='" + g.getNombre() + "'");
        rs.close();
    }
*/
    public ArrayList<dMarcasRepuestos> listarTodos() {
        ArrayList<dMarcasRepuestos> lista = new ArrayList<dMarcasRepuestos>();
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.marcasrepuestos.* FROM salu.marcasrepuestos");

            while (rs.next()) {
                int idMarcasRepuestos = rs.getInt("MarcasRepuestosId");
                String nombre = rs.getNString("MarcasRepuestosNombre");
                dMarcasRepuestos m = new dMarcasRepuestos(idMarcasRepuestos, nombre);
                lista.add(m);
            }
            rs.close();

            return lista;
        } catch (SQLException e) {
            return null;
        }
    }
}

