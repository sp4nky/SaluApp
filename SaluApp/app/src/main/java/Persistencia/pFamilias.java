package Persistencia;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Dominio.dFamilias;

public class pFamilias {



    private MySQL mysql;
    private Connection conn;

    public pFamilias(MySQL mysql) {
        this.mysql = mysql;
    }

/*
    public void alta(dFamilias m) throws SQLException {
        ResultSet rs= mysql.ejecutarSentencia("INSERT INTO salu.familias(FamiliasNombre) " +
                "VALUES ('" + m.getNombre() + "')");
        rs.close();

    }
*/
    public dFamilias buscar(String nombre)
    {
        dFamilias m= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.familias.* FROM salu.familias WHERE FamiliasNombre='" + nombre + "'");
            while (rs.next()) {
                int idFamilias = rs.getInt("FamiliasId");
                m = new dFamilias(idFamilias, nombre);
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

    public dFamilias buscar(int id)
    {
        dFamilias m= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.familias.* FROM salu.familias WHERE FamiliasId=" + id);
            while (rs.next()) {
                String nombre = rs.getNString("FamiliasNombre");
                m = new dFamilias(id, nombre);
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
    public void eliminar(dFamilias g) throws SQLException {
        ResultSet rs= mysql.ejecutarSentencia("DELETE FROM salu.familias WHERE FamiliasId=" + g.getId() + " OR FamiliasNombre='" + g.getNombre() + "'");
        rs.close();
    }
*/
    public ArrayList<dFamilias> listarTodos() {
        ArrayList<dFamilias> lista = new ArrayList<dFamilias>();
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.familias.* FROM salu.familias");

            while (rs.next()) {
                int idFamilias = rs.getInt("FamiliasId");
                String nombre = rs.getNString("FamiliasNombre");
                dFamilias m = new dFamilias(idFamilias, nombre);
                lista.add(m);
            }
            rs.close();

            return lista;
        } catch (SQLException e) {
            return null;
        }
    }
}
