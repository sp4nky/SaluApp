package Persistencia;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Dominio.dMarca;
import Dominio.dModelo;
import Dominio.dTipo;

public class pModelo{

    private MySQL mysql;
    private Connection conn;
    private pMarca pmarca;
    private dMarca marca;
    private pTipo ptipo;
    private dTipo tipo;

    public pModelo(MySQL mysql) {

        this.mysql = mysql;
        pmarca= new pMarca(mysql);
        ptipo = new pTipo(mysql);
    }
/*
    public void alta(dModelo m) throws SQLException {
        ResultSet rs= mysql.ejecutarSentencia("INSERT INTO salu.modelosequipos(ModelosEquiposNombre,TiposEquiposId,MarcasEquiposId) " +
                "VALUES ('" + m.getNombre() + ", " + m.getTipo().getIdTipo() + ", " + m.getMarca().getIdMarca() + "')");
        rs.close();

    }
*/
    public dModelo buscar(String nombre)
    {
        dModelo m= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.modelosequipos.* FROM salu.modelosequipos WHERE ModelosEquiposNombre='" + nombre + "'");
            while (rs.next()) {
                int idModelo = rs.getInt("ModelosEquiposId");
                m = new dModelo(idModelo, nombre);
                marca= pmarca.buscar(rs.getInt("MarcasEquiposId"));
                m.setMarca(marca);
                tipo= ptipo.buscar(rs.getInt("TiposEquiposId"));
                m.setTipo(tipo);
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

    public dModelo buscar(int id)
    {
        dModelo m= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.modelosequipos.* FROM salu.modelosequipos WHERE ModelosEquiposId=" + id);
            while (rs.next()) {
                String nombre = rs.getNString("ModelosEquiposNombre");
                m = new dModelo(id, nombre);
                marca= pmarca.buscar(rs.getInt("MarcasEquiposId"));
                m.setMarca(marca);
                tipo= ptipo.buscar(rs.getInt("TiposEquiposId"));
                m.setTipo(tipo);
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
    public void eliminar(dModelo g) throws SQLException {
        ResultSet rs= mysql.ejecutarSentencia("DELETE FROM salu.modelosequipos WHERE ModelosEquiposId=" + g.getIdModelo() + " OR ModelosEquiposNombre='" + g.getNombre() + "'");
        rs.close();
    }
*/
    public ArrayList<dModelo> listarTodos() {
        ArrayList<dModelo> lista = new ArrayList<dModelo>();
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.modelosequipos.* FROM salu.modelosequipos");

            while (rs.next()) {
                int idModelo = rs.getInt("ModelosEquiposId");
                String nombre = rs.getNString("ModelosEquiposNombre");
                dModelo m = new dModelo(idModelo, nombre);
                marca= pmarca.buscar(rs.getInt("MarcasEquiposId"));
                m.setMarca(marca);
                tipo= ptipo.buscar(rs.getInt("TiposEquiposId"));
                m.setTipo(tipo);
                lista.add(m);
            }
            rs.close();

            return lista;
        } catch (SQLException e) {
            return null;
        }
    }
}
