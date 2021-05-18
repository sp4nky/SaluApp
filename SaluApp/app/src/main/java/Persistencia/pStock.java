package Persistencia;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Dominio.dModelo;
import Dominio.dStock;

public class pStock{
//Stock es equipos
    private pModelo pmodelo;
    private pTipo ptipo;
    private MySQL mysql;
    private Connection conn;

    public pStock(MySQL mysql) {
        this.mysql=mysql;
        conn=mysql.getConexionMySQL();
        pmodelo= new pModelo(mysql);
        ptipo= new pTipo(mysql);
    }

    public dStock buscar(String nroSerie)
    {
        dStock f= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.equipos.* FROM salu.equipos WHERE EquiposNroSerie='" + nroSerie + "'");
            if(rs.next()) {
                int id = rs.getInt("EquiposId");
                //String nroSerie = rs.getNString("EquiposNroSerie");
                int idmodelo = rs.getInt("ModelosEquiposId");
/*
                //Conversion de LongBlob to file
                File path = new File(Environment.getExternalStorageDirectory()
                        .getPath());
                File image = new File(path,rs.getString("EquiposFotoNombre") + "." + rs.getString("EquiposFotoExt"));

                FileOutputStream fos = new FileOutputStream(image);

                byte[] buffer = new byte[1];
                InputStream is = rs.getBinaryStream("EquiposFoto");
                while (is.read(buffer) > 0) {
                    fos.write(buffer);
                }
                fos.close();
*/
                dModelo modelo = pmodelo.buscar(idmodelo);
                f= new dStock(id,modelo,nroSerie,null,"","");

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

    public dStock buscar(int id)
    {
        dStock f= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.equipos.* FROM salu.equipos WHERE EquiposId='" + id + "'");
            if(rs.next()) {
                String nroSerie = rs.getNString("EquiposNroSerie");
                //String nroSerie = rs.getNString("EquiposNroSerie");
                int idmodelo = rs.getInt("ModelosEquiposId");
/*
                //Conversion de LongBlob to file
                File path = new File(Environment.getExternalStorageDirectory()
                        .getPath());
                File image = new File(path,rs.getString("EquiposFotoNombre")  + "." + rs.getString("EquiposFotoExt"));

                FileOutputStream fos = new FileOutputStream(image);

                byte[] buffer = new byte[1];
                InputStream is = rs.getBinaryStream("EquiposFoto");
                while (is.read(buffer) > 0) {
                    fos.write(buffer);
                }
                fos.close();
*/
                dModelo modelo = pmodelo.buscar(idmodelo);
                f= new dStock(id,modelo,nroSerie,null,"","");

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


    public ArrayList<dStock> listarTodos()
    {
        ArrayList<dStock> lista= new ArrayList<dStock>();
        try {
            ResultSet rs= mysql.ejecutarSentencia("SELECT salu.equipos.* FROM salu.equipos");
            while (rs.next()) {
                int id = rs.getInt("EquiposId");
                String nroSerie = rs.getNString("EquiposNroSerie");
                int idmodelo = rs.getInt("ModelosEquiposId");
/*
                //Conversion de LongBlob to file
                File path = new File(Environment.getExternalStorageDirectory()
                        .getPath());
                File image = new File(path,rs.getString("EquiposFotoNombre") + "." + rs.getString("EquiposFotoExt"));

                FileOutputStream fos = new FileOutputStream(image);

                byte[] buffer = new byte[1];
                InputStream is = rs.getBinaryStream("EquiposFoto");
                while (is.read(buffer) > 0) {
                    fos.write(buffer);
                }
                fos.close();
*/
                dModelo modelo = pmodelo.buscar(idmodelo);
                dStock f= new dStock(id,modelo,nroSerie,null,"","");
                lista.add(f);
            }
  //          rs.close();;
            return lista;
        }
        catch (SQLException e) {
            return null;
        }
    }
}
