package Persistencia;


import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Dominio.dAdjuntos;
import Dominio.dSolicitud;

public class pAdjuntos {

    private MySQL mysql;
    private Connection conn;
    private dSolicitud solicitud;
    private pSolicitud psolicitud;

    public pAdjuntos(MySQL mysql) {

        this.mysql=mysql;
        conn=mysql.getConexionMySQL();
        psolicitud= new pSolicitud(mysql);
    }

    public void alta(dAdjuntos f) throws SQLException
    {
        InputStream inputStream=null;
        if(f.getImagen()!=null) {
            try {

                inputStream = new FileInputStream(f.getImagen());
                ResultSet rs = mysql.ejecutarSentencia("INSERT INTO salu.adjuntos(IncidenciasId, AdjuntosImagen, AdjuntosImagen_GXI, AdjuntosDetalle) " +
                        "VALUES (" + f.getSolicitud().getIdSolicitud() + ", " + inputStream + ", '" + "adjunto" + f.getId() + ".jpg" + "', '" + f.getDescripcion() + "')");
                rs.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public dAdjuntos buscar(int id)
    {
        dAdjuntos f= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.adjuntos.* FROM salu.adjuntos WHERE AdjuntosId=" + id);
            while (rs.next()) {
                int idAdjuntos = rs.getInt("AdjuntosId");
                String detalle = rs.getNString("AdjuntosDetalle");
                int idSolicitud = rs.getInt("IncidenciasId");
                solicitud=psolicitud.buscar(idSolicitud);
//Conversion de LongBlob to file
                // read this file into InputStream
                InputStream inputStream=null;
                OutputStream outputStream=null;
                inputStream = rs.getBinaryStream("AdjuntosImagen");

                File path = new File(Environment.getExternalStorageDirectory()
                        .getPath());
                File image = new File(path,"adjunto" + idAdjuntos + ".jpg");


                // write the inputStream to a FileOutputStream

                outputStream = new FileOutputStream(image);

                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }

                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStream != null) {
                    try {
                        // outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                f = new dAdjuntos(idAdjuntos,solicitud,image,detalle);
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
        //Se pasa como parametro el funcionario modificado
        public void modificar(dAdjuntos f) throws SQLException {
            ResultSet rs= mysql.ejecutarSentencia("UPDATE salu.funcionarios SET +" +
                    "nombre='" + f.getNombre() + "'," +
                    "apellido='" + f.getApellido() + "'," +
                    "idgrupo=" + f.getGrupo().getIdGrupo() + "," +
                    "correo='" + f.getCorreo() + "'," +
                    "contr='" + f.getContr() + "'," +
                    "telefono='" + f.getTelefono() + "' " +
                    "WHERE idfuncionarios=" + f.getIdAdjuntos());
            rs.close();
    
        }
    
        public void eliminar(dAdjuntos f) throws SQLException {
            ResultSet rs= mysql.ejecutarSentencia("DELETE FROM salu.funcionarios WHERE idfuncionarios=" + f.getIdAdjuntos());
            rs.close();
        }
    */
    public ArrayList<dAdjuntos> listarTodos()
    {
        ArrayList<dAdjuntos> lista= new ArrayList<dAdjuntos>();
        try {
            ResultSet rs= mysql.ejecutarSentencia("SELECT salu.adjuntos.* FROM salu.adjuntos");
            while (rs.next()) {
                int idAdjuntos = rs.getInt("AdjuntosId");
                String detalle = rs.getNString("AdjuntosDetalle");
                int idSolicitud = rs.getInt("IncidenciasId");
                solicitud=psolicitud.buscar(idSolicitud);

                //Conversion de LongBlob to file
                // read this file into InputStream
                InputStream inputStream=null;
                OutputStream outputStream=null;
                inputStream = rs.getBinaryStream("AdjuntosImagen");

                File path = new File(Environment.getExternalStorageDirectory()
                        .getPath());
                File image = new File(path,"adjunto" + idAdjuntos + ".jpg");


                // write the inputStream to a FileOutputStream

                outputStream = new FileOutputStream(image);

                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }

                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStream != null) {
                    try {
                        // outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                dAdjuntos f = new dAdjuntos(idAdjuntos,solicitud,image,detalle);
                lista.add(f);
            }
            rs.close();;
            return lista;
        }
        catch (SQLException e) {
            return null;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }


    public ArrayList<dAdjuntos> listarFuncionariosSolicitud(dSolicitud solicitud)
    {
        ArrayList<dAdjuntos> lista= new ArrayList<dAdjuntos>();
        try {
            ResultSet rs= mysql.ejecutarSentencia("SELECT  \n" +
                    "    salu.adjuntos.*\n" +
                    "FROM\n" +
                    "    salu.adjuntos,\n" +
                    "    salu.incidencias \n" +
                    "WHERE\n" +
                    "    salu.adjuntos.IncidenciasId = salu.incidencias.IncidenciasId \n" +
                    "        AND salu.incidencias.IncidenciasId = " + solicitud.getIdSolicitud());
            while (rs.next()) {
                int idAdjuntos = rs.getInt("AdjuntosId");
                String detalle = rs.getNString("AdjuntosDetalle");
                int idSolicitud = rs.getInt("IncidenciasId");
                solicitud=psolicitud.buscar(idSolicitud);

                //Conversion de LongBlob to file
                // read this file into InputStream
                InputStream inputStream=null;
                OutputStream outputStream=null;
                inputStream = rs.getBinaryStream("AdjuntosImagen");

                File path = new File(Environment.getExternalStorageDirectory()
                        .getPath());
                File image = new File(path,"adjunto" + idAdjuntos + ".jpg");


                // write the inputStream to a FileOutputStream

                outputStream = new FileOutputStream(image);

                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }

                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStream != null) {
                    try {
                        // outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                dAdjuntos f = new dAdjuntos(idAdjuntos,solicitud,image,detalle);
                lista.add(f);
            }
            rs.close();
            return lista;
        }
        catch (SQLException e) {
            return null;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

}
