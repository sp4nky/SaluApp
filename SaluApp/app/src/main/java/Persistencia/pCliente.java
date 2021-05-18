package Persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Dominio.dCliente;


public class pCliente {
    private MySQL mysql;
    private Connection conn;

    public pCliente(MySQL mysql) {
        this.mysql=mysql;
        conn=mysql.getConexionMySQL();
    }
/*
    public void alta(dCliente c) throws SQLException
    {
        ResultSet rs= mysql.ejecutarSentencia("INSERT INTO salu.clientes(nombre, apellido, telefono, correo) " +
                "VALUES ('" + c.getNombre() + "', '" + c.getApellido() + "', '" + c.getTelefono() + "', '" + c.getCorreo() + "')");
        rs.close();
    }
*/
    public dCliente buscar(String correo)
    {
        dCliente c= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.clientes.* FROM salu.clientes WHERE ClientesMail='" + correo + "'");
            if(rs.next()) {
                int idCliente = rs.getInt("ClientesId");
                String nombre = rs.getNString("ClientesNombre");
                String celular = rs.getNString("ClientesCelular");
                String correocli = rs.getNString("ClientesMail");
                String telefono = rs.getNString("ClientesTelefono");
                String direccion = rs.getNString("ClientesDireccion");
                String localidad = rs.getNString("ClientesLocalidad");
                String estado = rs.getNString("ClientesEstado");
                String contacto = rs.getNString("ClientesContacto");
                String ci = rs.getNString("ClientesCI");
                c = new dCliente(idCliente,nombre,direccion,localidad,estado,telefono,celular,correo,ci,contacto);
            }
            rs.close();
        }
        catch (SQLException e) {
            c=null;


        }
        finally {
            return c;
        }
    }

    public dCliente buscar(int idcliente)
    {
        dCliente c= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.clientes.* FROM salu.clientes WHERE ClientesId='" + idcliente + "'");
            if(rs.next()) {
                int idCliente = rs.getInt("ClientesId");
                String nombre = rs.getNString("ClientesNombre");
                String celular = rs.getNString("ClientesCelular");
                String correocli = rs.getNString("ClientesMail");
                String telefono = rs.getNString("ClientesTelefono");
                String direccion = rs.getNString("ClientesDireccion");
                String localidad = rs.getNString("ClientesLocalidad");
                String estado = rs.getNString("ClientesEstado");
                String contacto = rs.getNString("ClientesContacto");
                String ci = rs.getNString("ClientesCI");
                c = new dCliente(idCliente,nombre,direccion,localidad,estado,telefono,celular,correocli,ci,contacto);
            }
            rs.close();
        }
        catch (SQLException e) {
            c=null;


        }
        finally {
            return c;
        }
    }
/*
    //Se pasa como parametro el funcionario modificado
    public void modificar(dCliente c) throws SQLException {
        ResultSet rs= mysql.ejecutarSentencia("UPDATE salu.clientes SET +" +
                "nombre='" + c.getNombre() + "'," +
                "apellido='" + c.getApellido() + "'," +
                "correo='" + c.getCorreo() + "'," +
                "telefono='" + c.getTelefono() + "' " +
                "WHERE idfuncionarios=" + c.getIdCliente());
        rs.close();

    }

    public void eliminar(dCliente f) throws SQLException {
        ResultSet rs= mysql.ejecutarSentencia("DELETE FROM salu.clientes WHERE idclientes=" + f.getIdCliente());
        rs.close();
    }
*/
    public ArrayList<dCliente> listarTodos()
    {
        ArrayList<dCliente> lista= new ArrayList<dCliente>();
        try {
            ResultSet rs= mysql.ejecutarSentencia("SELECT salu.clientes.* FROM salu.clientes");
            while (rs.next()) {
                int idCliente = rs.getInt("ClientesId");
                String nombre = rs.getNString("ClientesNombre");
                String celular = rs.getNString("ClientesCelular");
                String correocli = rs.getNString("ClientesMail");
                String telefono = rs.getNString("ClientesTelefono");
                String direccion = rs.getNString("ClientesDireccion");
                String localidad = rs.getNString("ClientesLocalidad");
                String estado = rs.getNString("ClientesEstado");
                String contacto = rs.getNString("ClientesContacto");
                String ci = rs.getNString("ClientesCI");
                dCliente c = new dCliente(idCliente,nombre,direccion,localidad,estado,telefono,celular,correocli,ci,contacto);
                lista.add(c);
            }
            rs.close();;
            return lista;
        }
        catch (SQLException e) {
            return null;
        }
    }



}
