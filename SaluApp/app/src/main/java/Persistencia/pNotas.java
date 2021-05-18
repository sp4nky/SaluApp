package Persistencia;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Dominio.dNotas;
import Dominio.dSolicitud;

public class pNotas {


    private MySQL mysql;
    private Connection conn;
    private pSolicitud psolicitud;
    private dSolicitud solicitud;

    public pNotas(MySQL mysql) {

        this.mysql = mysql;
        psolicitud= new pSolicitud(mysql);
    }

    public boolean alta(dNotas m){
        java.sql.Date fechasql= new java.sql.Date(m.getFecha().getYear(),m.getFecha().getMonth(),m.getFecha().getDay());
        int rs= mysql.updateSentencia("INSERT INTO salu.notastecnicas(IncidenciasId,NotasTecnicasTexto,NotasTecnicasFecha,NotasTecnicasFuncionariosNombr) " +
                "VALUES(" + m.getSolicitud().getIdSolicitud() + ", '" + m.getTexto() + "', '" + fechasql.toString() + "', '" + m.getNombreFuncionario() + "')");
        return true;
    }
/*
    public dNotas buscar(String nombre)
    {
        dNotas m= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.notastecnicas.* FROM salu.notastecnicas WHERE NotasEquiposNombre='" + nombre + "'");
            while (rs.next()) {
                int idNota = rs.getInt("NotasEquiposId");
                m = new dNotas(idNota, nombre);
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
*/
    public dNotas buscar(int id)
    {
        dNotas m= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.notastecnicas.* FROM salu.notastecnicas WHERE NotasTecnicasId=" + id);
            while (rs.next()) {
                int idSolicitud = rs.getInt("IncidenciasId");
                String texto = rs.getNString("NotasTecnicasTexto");
                java.util.Date fecha = rs.getDate("NotasTecnicasFecha");
                String nombreFuncionario = rs.getString("NotasTecnicasFuncionariosNombr");
                solicitud= psolicitud.buscar(idSolicitud);
                m = new dNotas(id, solicitud, nombreFuncionario, fecha, texto);

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

    public void eliminar(dNotas g) throws SQLException {
        int rs= mysql.updateSentencia("DELETE FROM salu.notastecnicas WHERE NotasTecnicasId=" + g.getId());

    }

    public ArrayList<dNotas> listarTodos() {
        ArrayList<dNotas> lista = new ArrayList<dNotas>();
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.notastecnicas.* FROM salu.notastecnicas");

            while (rs.next()) {
                int idSolicitud = rs.getInt("IncidenciasId");
                String texto = rs.getNString("NotasTecnicasTexto");
                java.util.Date fecha = rs.getDate("NotasTecnicasFecha");
                String nombreFuncionario = rs.getString("NotasTecnicasFuncionariosNombr");
                int id = rs.getInt("NotasTecnicasId");
                solicitud= psolicitud.buscar(idSolicitud);
                dNotas m = new dNotas(id, solicitud, nombreFuncionario, fecha, texto);
                lista.add(m);
            }
            rs.close();

            return lista;
        } catch (SQLException e) {
            return null;
        }
    }
}
