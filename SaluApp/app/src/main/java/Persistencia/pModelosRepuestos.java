package Persistencia;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Dominio.dMarcasRepuestos;
import Dominio.dModelosRepuestos;

public class pModelosRepuestos {


    private MySQL mysql;
    private Connection conn;
    private pMarcasRepuestos pmarca;
    private dMarcasRepuestos marca;

    public pModelosRepuestos(MySQL mysql) {

        this.mysql = mysql;
        pmarca= new pMarcasRepuestos(mysql);
    }
/*
    public void alta(dModelosRepuestos m) throws SQLException {
        ResultSet rs= mysql.ejecutarSentencia("INSERT INTO salu.modelosrepuestos(ModelosRepuestosNombre,MarcasRepuestosId) " +
                "VALUES ('" + m.getNombre() + ", " + m.getId() + "')");
        rs.close();

    }
*/
    public dModelosRepuestos  buscar(String nombre)
    {
        dModelosRepuestos  m= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.modelosrepuestos.* FROM salu.modelosrepuestos WHERE ModelosRepuestosNombre='" + nombre + "'");
            while (rs.next()) {
                int idModelosRepuestos  = rs.getInt("ModelosRepuestosId");
                marca= pmarca.buscar(rs.getInt("MarcasRepuestosId"));
                String nombreModelo = rs.getNString("ModelosRepuestosNombre");
                m = new dModelosRepuestos (idModelosRepuestos, nombreModelo, marca);
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

    public dModelosRepuestos  buscar(int id)
    {
        dModelosRepuestos  m= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.modelosrepuestos.* FROM salu.modelosrepuestos WHERE ModelosRepuestosId=" + id);
            while (rs.next()) {
                int idModelosRepuestos  = rs.getInt("ModelosRepuestosId");
                marca= pmarca.buscar(rs.getInt("MarcasRepuestosId"));
                String nombreModelo = rs.getNString("ModelosRepuestosNombre");
                m = new dModelosRepuestos (idModelosRepuestos, nombreModelo, marca);
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
    public void eliminar(dModelosRepuestos  g) throws SQLException {
        ResultSet rs= mysql.ejecutarSentencia("DELETE FROM salu.modelosrepuestos WHERE ModelosRepuestosId=" + g.getId() + " OR ModelosRepuestosNombre='" + g.getNombre() + "'");
        rs.close();
    }
*/
    public ArrayList<dModelosRepuestos > listarTodos() {
        ArrayList<dModelosRepuestos > lista = new ArrayList<dModelosRepuestos >();
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.modelosrepuestos.* FROM salu.modelosrepuestos");

            while (rs.next()) {
                int idModelosRepuestos  = rs.getInt("ModelosRepuestosId");
                marca= pmarca.buscar(rs.getInt("MarcasRepuestosId"));
                String nombreModelo = rs.getNString("ModelosRepuestosNombre");
                dModelosRepuestos m = new dModelosRepuestos (idModelosRepuestos, nombreModelo, marca);
                lista.add(m);
            }
            rs.close();

            return lista;
        } catch (SQLException e) {
            return null;
        }
    }
}
