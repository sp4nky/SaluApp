package Persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Dominio.dGrupo;


public class pGrupo{

    private MySQL mysql;
    private Connection conn;

    public pGrupo(MySQL mysql) {
        this.mysql = mysql;
    }

    /*
    public void alta(dGrupo g) throws SQLException {
        ResultSet rs= mysql.ejecutarSentencia("INSERT INTO salu.grupos(GruposNombre) " +
                "VALUES ('" + g.getNombre() + "')");
        rs.close();

    }
*/
    public dGrupo buscar(String nombre)
    {
        dGrupo g= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.grupos.* FROM salu.grupos WHERE GruposNombre='" + nombre + "'");
            while (rs.next()) {
                int idGrupo = rs.getInt("GruposId");
                g = new dGrupo(idGrupo, nombre);
            }
            rs.close();
        }
        catch (SQLException e) {
            g=null;
        }
        finally {

            return g;
        }
    }

    public dGrupo buscar(int id)
    {
        dGrupo g= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.grupos.* FROM salu.grupos WHERE GruposId=" + id);
            while (rs.next()) {
                String nombre = rs.getNString("GruposNombre");
                g = new dGrupo(id, nombre);
            }
            rs.close();
        }
        catch (SQLException e) {
            g=null;
        }
        finally {

            return g;
        }
    }

    public void eliminar(dGrupo g) throws SQLException {
        ResultSet rs= mysql.ejecutarSentencia("DELETE FROM salu.grupos WHERE GruposId=" + g.getIdGrupo() + " OR GruposNombre='" + g.getNombre() + "'");
        rs.close();
    }

    public ArrayList<dGrupo> listarTodos()
    {
        ArrayList<dGrupo> lista= new ArrayList<dGrupo>();
        try {
            ResultSet rs= mysql.ejecutarSentencia("SELECT salu.grupos.* FROM salu.grupos");

            while (rs.next()) {
                int idGrupo= rs.getInt("GruposId");
                String nombre = rs.getNString("GruposNombre");
                dGrupo f= new dGrupo(idGrupo, nombre);
                lista.add(f);
            }
            rs.close();
            return lista;
        }
        catch (SQLException e) {
            return null;
        }
    }
}
