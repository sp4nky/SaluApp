package Persistencia;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import Dominio.dFuncionario;
import Dominio.dGrupo;
import Dominio.dSolicitud;

public class pFuncionario{

    private pGrupo pgrup;
    private MySQL mysql;
    private Connection conn;


    public pFuncionario(MySQL mysql) {

        this.mysql=mysql;
        conn=mysql.getConexionMySQL();
        pgrup= new pGrupo(mysql);
    }
/*
    public void alta(dFuncionario f) throws SQLException
    {
        ResultSet rs= mysql.ejecutarSentencia("INSERT INTO salu.funcionarios(nombre, apellido, idgrupo, correo, contr, telefono) " +
                "VALUES ('" + f.getNombre() + "', '" + f.getApellido() + "', " + f.getGrupo().getIdGrupo() + ", '" + f.getCorreo() + "', '" + f.getContr() + "', '" + f.getTelefono() + "')");
        rs.close();
    }
*/
    public dFuncionario buscar(String correo)
    {
        dFuncionario f= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.funcionarios.* FROM salu.funcionarios WHERE FuncionariosCorreo='" + correo + "'");
            while (rs.next()) {
                int idFuncionario = rs.getInt("FuncionariosId");
                String nombre = rs.getNString("FuncionarioNombre");
                String direccion = rs.getNString("FuncionariosDireccion");
                String localidad = rs.getNString("FuncionariosLocalidad");
                String telefono = rs.getNString("FuncionariosTelefono");
                String celular = rs.getNString("FuncionariosCelular");
                String contr = rs.getNString("FuncionariosPassword");
                String estado = rs.getNString("FuncionariosEstado");
                Date fchNac = rs.getDate("FuncionariosFechaNac");
                String ci = rs.getNString("FuncionariosCI");
                Date fchIngreso = rs.getDate("FuncionariosFechaIngreso");

                //Conversion de LongBlob to file
                // read this file into InputStream
                InputStream inputStream=null;
                OutputStream outputStream=null;
                inputStream = rs.getBinaryStream("FuncionariosFoto");
                File image=null;
                if(inputStream!=null) {
                    File path = new File(Environment.getExternalStorageDirectory()
                            .getPath());
                    image = new File(path, "f" + idFuncionario + ".jpg");


                    // write the inputStream to a FileOutputStream

                    outputStream = new FileOutputStream(image);

                    int read = 0;
                    byte[] bytes = new byte[1024];

                    while ((read = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read);
                    }
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
                String cargo = rs.getNString("FuncionariosCargo");
                dGrupo grupo= pgrup.buscar(rs.getInt("GruposId"));

                f = new dFuncionario(idFuncionario,nombre,grupo,correo,contr,telefono,direccion,localidad,estado,celular,ci,fchNac,image,cargo,fchIngreso);
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

    public dFuncionario buscarParaLogin(String correo)
    {
        dFuncionario f= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.funcionarios.* FROM salu.funcionarios WHERE FuncionariosCorreo='" + correo + "'");
            while (rs.next()) {
                int idFuncionario = rs.getInt("FuncionariosId");
                String nombre = rs.getNString("FuncionarioNombre");

                String contr = rs.getNString("FuncionariosPassword");

                f = new dFuncionario(idFuncionario,nombre,null,correo,contr,"","","","","","",null,null,"",null);
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

    public dFuncionario buscarParaLogin(int id)
    {
        dFuncionario f= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.funcionarios.* FROM salu.funcionarios WHERE FuncionariosId='" + id + "'");
            while (rs.next()) {
                int idFuncionario = rs.getInt("FuncionariosId");
                String nombre = rs.getNString("FuncionarioNombre");
                String correo = rs.getNString("FuncionariosCorreo");
                String contr = rs.getNString("FuncionariosPassword");

                f = new dFuncionario(idFuncionario,nombre,null,correo,contr,"","","","","","",null,null,"",null);
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

    public dFuncionario buscar(int id)
    {
        dFuncionario f= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.funcionarios.* FROM salu.funcionarios WHERE FuncionariosId='" + id + "'");
            while (rs.next()) {
                int idFuncionario = rs.getInt("FuncionariosId");
                String nombre = rs.getNString("FuncionarioNombre");
                String direccion = rs.getNString("FuncionariosDireccion");
                String localidad = rs.getNString("FuncionariosLocalidad");
                String telefono = rs.getNString("FuncionariosTelefono");
                String celular = rs.getNString("FuncionariosCelular");
                String contr = rs.getNString("FuncionariosPassword");
                String estado = rs.getNString("FuncionariosEstado");
                Date fchNac = rs.getDate("FuncionariosFechaNac");
                String ci = rs.getNString("FuncionariosCI");
                Date fchIngreso = rs.getDate("FuncionariosFechaIngreso");
                String correo = rs.getNString("FuncionariosCorreo");
                //Conversion de LongBlob to file
                // read this file into InputStream
                InputStream inputStream=null;
                OutputStream outputStream=null;
                inputStream = rs.getBinaryStream("FuncionariosFoto");

                File path = new File(Environment.getExternalStorageDirectory()
                        .getPath());
                File image = new File(path,"f" + idFuncionario + ".jpg");


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
                String cargo = rs.getNString("FuncionariosCargo");
                dGrupo grupo= pgrup.buscar(rs.getInt("GruposId"));

                f = new dFuncionario(idFuncionario,nombre,grupo,correo,contr,telefono,direccion,localidad,estado,celular,ci,fchNac,image,cargo,fchIngreso);
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
    public void modificar(dFuncionario f) throws SQLException {
        ResultSet rs= mysql.ejecutarSentencia("UPDATE salu.funcionarios SET +" +
                "nombre='" + f.getNombre() + "'," +
                "apellido='" + f.getApellido() + "'," +
                "idgrupo=" + f.getGrupo().getIdGrupo() + "," +
                "correo='" + f.getCorreo() + "'," +
                "contr='" + f.getContr() + "'," +
                "telefono='" + f.getTelefono() + "' " +
                "WHERE idfuncionarios=" + f.getIdFuncionario());
        rs.close();

    }

    public void eliminar(dFuncionario f) throws SQLException {
        ResultSet rs= mysql.ejecutarSentencia("DELETE FROM salu.funcionarios WHERE idfuncionarios=" + f.getIdFuncionario());
        rs.close();
    }
*/
    public ArrayList<dFuncionario> listarTodos()
    {
        ArrayList<dFuncionario> lista= new ArrayList<dFuncionario>();
        try {
            ResultSet rs= mysql.ejecutarSentencia("SELECT salu.funcionarios.* FROM salu.funcionarios");
            while (rs.next()) {
                int idFuncionario = rs.getInt("FuncionariosId");
                String nombre = rs.getNString("FuncionarioNombre");
                String direccion = rs.getNString("FuncionariosDireccion");
                String localidad = rs.getNString("FuncionariosLocalidad");
                String telefono = rs.getNString("FuncionariosTelefono");
                String celular = rs.getNString("FuncionariosCelular");
                String contr = rs.getNString("FuncionariosPassword");
                String estado = rs.getNString("FuncionariosEstado");
                String correo = rs.getNString("FuncionariosCorreo");
                Date fchNac = rs.getDate("FuncionariosFechaNac");
                String ci = rs.getNString("FuncionariosCI");
                Date fchIngreso = rs.getDate("FuncionariosFechaIngreso");
//Conversion de LongBlob to file
                // read this file into InputStream
                InputStream inputStream=null;
                OutputStream outputStream=null;
                inputStream = rs.getBinaryStream("FuncionariosFoto");

                File path = new File(Environment.getExternalStorageDirectory()
                        .getPath());
                File image = new File(path,"f" + idFuncionario + ".jpg");


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
                String cargo = rs.getNString("FuncionariosCargo");
                dGrupo grupo= pgrup.buscar(rs.getInt("GruposId"));

                dFuncionario f = new dFuncionario(idFuncionario,nombre,grupo,correo,contr,telefono,direccion,localidad,estado,celular,ci,fchNac,image,cargo,fchIngreso);
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


    public ArrayList<dFuncionario> listarFuncionariosSolicitud(dSolicitud solicitud)
    {
        ArrayList<dFuncionario> lista= new ArrayList<dFuncionario>();
        try {
            ResultSet rs= mysql.ejecutarSentencia("SELECT DISTINCT \n" +
                    "    salu.funcionarios.*\n" +
                    "FROM\n" +
                    "    salu.funcionarios,\n" +
                    "    salu.grupos, salu.incidencias\n" +
                    "WHERE\n" +
                    "    salu.funcionarios.GruposId = salu.grupos.GruposId\n" +
                    "        AND salu.incidencias.IncidenciasGruposId = salu.grupos.GruposId AND salu.incidencias.IncidenciasId= " + solicitud.getIdSolicitud() + " OR salu.incidencias.IncidenciasFuncionariosId=salu.funcionarios.FuncionariosId");
            while (rs.next()) {
                int idFuncionario = rs.getInt("FuncionariosId");
                String nombre = rs.getNString("FuncionarioNombre");
                String telefono = rs.getNString("FuncionariosTelefono");
                String celular = rs.getNString("FuncionariosCelular");
                String correo = rs.getNString("FuncionariosCorreo");

                String cargo = rs.getNString("FuncionariosCargo");
                dGrupo grupo= pgrup.buscar(rs.getInt("GruposId"));

                dFuncionario f = new dFuncionario(idFuncionario,nombre,grupo,correo,"",telefono,"","","",celular,"",null,null,cargo,null);
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
