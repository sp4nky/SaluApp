package Persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Dominio.dFamilias;
import Dominio.dModelosRepuestos;
import Dominio.dRepuestos;
import Dominio.dSolicitud;

public class pRepuestos {


    private pModelosRepuestos pmodelo;
    private dModelosRepuestos modelo;
    private dFamilias familias;
    private pFamilias pfamilias;
    private MySQL mysql;
    private Connection conn;

    public pRepuestos(MySQL mysql) {
        this.mysql=mysql;
        conn=mysql.getConexionMySQL();
        pmodelo= new pModelosRepuestos(mysql);
        pfamilias= new pFamilias(mysql);
    }
/*
    public void alta(dRepuestos r) throws SQLException
    {
        ResultSet rs= mysql.ejecutarSentencia("INSERT INTO salu.repuestos(RepuestosNroParte, RepuestosCodBarras, ModelosRepuestosId, RepuestosFoto, FamiliasId, RepuestosCantidad, RepuestosNombre, RepuestosFotoExt, RepuestosFotoNombre) " +
                "VALUES ('" + r.getNroParte() + "', '" + r.getCodigoBarras() + "', " + r.getModelo().getId() + ", " + s.getModelo().getIdModelo() + ", " + s.getTipo().getIdTipo() + ")");
        rs.close();
    }
*/
    public dRepuestos buscar(String nroParte)
    {
        dRepuestos f= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.repuestos.* FROM salu.repuestos WHERE RepuestosNroParte='" + nroParte + "'");
            if(rs.next()) {
                int id = rs.getInt("RepuestosId");
                //String nroSerie = rs.getNString("EquiposNroSerie");
                int idmodelo = rs.getInt("ModelosRepuestosId");
                modelo = pmodelo.buscar(idmodelo);
  /*
                //Conversion de LongBlob to file
                File path = new File(Environment.getExternalStorageDirectory()
                        .getPath());
                File image = new File(path,rs.getString("RepuestosFotoNombre") + "." + rs.getString("RepuestosFotoExt"));

                FileOutputStream fos = new FileOutputStream(image);

                byte[] buffer = new byte[1];
                InputStream is = rs.getBinaryStream("RepuestosFoto");
                while (is.read(buffer) > 0) {
                    fos.write(buffer);
                }
                fos.close();
*/
                int idFamilia = rs.getInt("FamiliasId");
                familias= pfamilias.buscar(idFamilia);
                int cantidad = rs.getInt("RepuestosCantidad");
                String nombreRepuesto = rs.getNString("RepuestosNombre");
                String codBarras = rs.getNString("RepuestosCodBarras");


                f= new dRepuestos(id,nroParte,codBarras,modelo,null,familias,cantidad,nombreRepuesto,"","");

            }
            rs.close();
        }
        catch (SQLException e) {
            f=null;


        }
        finally {
            return f;
        }
    }

    public dRepuestos buscar(int id)
    {
        dRepuestos f= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.repuestos.* FROM salu.repuestos WHERE RepuestosId='" + id + "'");
            if(rs.next()) {
                int idRepuesto = rs.getInt("RepuestosId");
                //String nroSerie = rs.getNString("EquiposNroSerie");
                int idmodelo = rs.getInt("ModelosRepuestosId");
                modelo = pmodelo.buscar(idmodelo);
  /*
                //Conversion de LongBlob to file
                File path = new File(Environment.getExternalStorageDirectory()
                        .getPath());
                File image = new File(path,rs.getString("RepuestosFotoNombre") + "." + rs.getString("RepuestosFotoExt"));

                FileOutputStream fos = new FileOutputStream(image);

                byte[] buffer = new byte[1];
                InputStream is = rs.getBinaryStream("RepuestosFoto");
                while (is.read(buffer) > 0) {
                    fos.write(buffer);
                }
                fos.close();
*/
                int idFamilia = rs.getInt("FamiliasId");
                familias= pfamilias.buscar(idFamilia);
                int cantidad = rs.getInt("RepuestosCantidad");
                String nombreRepuesto = rs.getNString("RepuestosNombre");
                String codBarras = rs.getNString("RepuestosCodBarras");
                String nroParte = rs.getNString("RepuestosNroParte");

                f= new dRepuestos(id,nroParte,codBarras,modelo,null,familias,cantidad,nombreRepuesto,"","");

            }
            rs.close();
        }
        catch (SQLException e) {
            f=null;


        }
        finally {
            return f;
        }
    }
    /*
        public ArrayList<dStock> listarStockSolicitud(dSolicitud solicitud)
        {
            ArrayList<dStock> lista= new ArrayList<dStock>();
            try {
                ResultSet rs= mysql.ejecutarSentencia("SELECT salu.stock.* FROM salu.stock, salu.solicitudstock WHERE salu.stock.idstock=salu.solicitudstock.idstock AND salu.solicitudstock.idsolicitud = " + solicitud.getIdSolicitud());

                while (rs.next()) {
                    int idStock = rs.getInt("idstock");
                    String nombrestock = rs.getNString("nombre");
                    int cantidad = rs.getInt("cantidad");
                    int idmarca = rs.getInt("idmarca");
                    int idmodelo = rs.getInt("idmodelo");
                    int idtipo = rs.getInt("idtipo");


                    dMarca marca= pmarca.buscar(idmarca);
                    dModelo modelo = pmodelo.buscar(idmodelo);
                    dTipo tipo = ptipo.buscar(idtipo);


                    dStock s = new dStock(idStock, nombrestock, cantidad, modelo, tipo, marca);
                    lista.add(s);
                }
                rs.close();;
                return lista;
            }
            catch (SQLException e) {
                return null;
            }
        }
    */
    public ArrayList<dRepuestos> listarTodos()
    {
        ArrayList<dRepuestos> lista= new ArrayList<dRepuestos>();
        try {
            ResultSet rs= mysql.ejecutarSentencia("SELECT salu.repuestos.* FROM salu.repuestos");
            while (rs.next()) {
                int id = rs.getInt("RepuestosId");
                //String nroSerie = rs.getNString("EquiposNroSerie");
                int idmodelo = rs.getInt("ModelosRepuestosId");
                modelo = pmodelo.buscar(idmodelo);
          /*
                //Conversion de LongBlob to file
                File path = new File(Environment.getExternalStorageDirectory()
                        .getPath());
                File image = new File(path,rs.getString("RepuestosFotoNombre") + "." + rs.getString("RepuestosFotoExt"));

                FileOutputStream fos = new FileOutputStream(image);

                byte[] buffer = new byte[1];
                InputStream is = rs.getBinaryStream("RepuestosFoto");
                while (is.read(buffer) > 0) {
                    fos.write(buffer);
                }
                fos.close();
*/
                int idFamilia = rs.getInt("FamiliasId");
                familias= pfamilias.buscar(idFamilia);
                int cantidad = rs.getInt("RepuestosCantidad");
                String nombreRepuesto = rs.getNString("RepuestosNombre");
                String codBarras = rs.getNString("RepuestosCodBarras");
                String nroParte = rs.getNString("RepuestosNroParte");


                dRepuestos f= new dRepuestos(id,nroParte,codBarras,modelo,null,familias,cantidad,nombreRepuesto,"","");
                lista.add(f);
            }
            //rs.close();
            return lista;
        }
        catch (SQLException e) {
            String error=e.toString();
            return null;
        }
    }

    public ArrayList<dRepuestos> listarRepuestosSolicitud(dSolicitud solicitud)
    {
        ArrayList<dRepuestos> lista= new ArrayList<dRepuestos>();
        try {
            ResultSet rs= mysql.ejecutarSentencia("SELECT salu.repuestos.*,salu.incidenciarepuestos.IncidenciaRepuestosCantidad FROM salu.repuestos, salu.incidenciarepuestos WHERE salu.repuestos.RepuestosId=salu.incidenciarepuestos.IncidenciaRepuestosId AND salu.incidenciarepuestos.IncidenciasId="+solicitud.getIdSolicitud());
            while (rs.next()) {
                int id = rs.getInt("RepuestosId");
                //String nroSerie = rs.getNString("EquiposNroSerie");
                int idmodelo = rs.getInt("ModelosRepuestosId");
                modelo = pmodelo.buscar(idmodelo);

                /*
                //Conversion de LongBlob to file
                File path = new File(Environment.getExternalStorageDirectory()
                        .getPath());
                File image = new File(path,rs.getString("RepuestosFotoNombre") + "." + rs.getString("RepuestosFotoExt"));

                FileOutputStream fos = new FileOutputStream(image);

                byte[] buffer = new byte[1];
                InputStream is = rs.getBinaryStream("RepuestosFoto");
                while (is.read(buffer) > 0) {
                    fos.write(buffer);
                }
                fos.close();
                */

                int idFamilia = rs.getInt("FamiliasId");
                familias= pfamilias.buscar(idFamilia);
                int cantidad = rs.getInt("IncidenciaRepuestosCantidad");
                String nombreRepuesto = rs.getNString("RepuestosNombre");
                String codBarras = rs.getNString("RepuestosCodBarras");
                String nroParte = rs.getNString("RepuestosNroParte");


                dRepuestos f= new dRepuestos(id,nroParte,codBarras,modelo,null,familias,cantidad,nombreRepuesto,"","");
                lista.add(f);

            }
            //rs.close();
            return lista;
        }
        catch (SQLException e) {
            return null;
        }
    }
}
