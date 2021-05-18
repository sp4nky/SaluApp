package Persistencia;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Dominio.dEmpresa;

public class pEmpresa {

    private MySQL mysql;
    private Connection conn;

    public pEmpresa(MySQL mysql) {
        this.mysql=mysql;
        conn=mysql.getConexionMySQL();
    }
    /*
        public void alta(dEmpresa c) throws SQLException
        {
            ResultSet rs= mysql.ejecutarSentencia("INSERT INTO salu.clientes(nombre, apellido, telefono, correo) " +
                    "VALUES ('" + c.getNombre() + "', '" + c.getApellido() + "', '" + c.getTelefono() + "', '" + c.getCorreo() + "')");
            rs.close();
        }
    */
    /*
    public dEmpresa buscar(String correo)
    {
        dEmpresa c= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.empresa.* FROM salu.empresa WHERE EmpresaMail='" + correo + "'");
            if(rs.next()) {
                int idEmpresa = rs.getInt("ClientesId");
                String nombre = rs.getNString("ClientesNombre");
                String celular = rs.getNString("ClientesCelular");
                String correocli = rs.getNString("ClientesMail");
                String telefono = rs.getNString("ClientesTelefono");
                String direccion = rs.getNString("ClientesDireccion");
                String localidad = rs.getNString("ClientesLocalidad");
                String estado = rs.getNString("ClientesEstado");
                String contacto = rs.getNString("ClientesContacto");
                String ci = rs.getNString("ClientesCI");
                c = new dEmpresa(idEmpresa,nombre,direccion,localidad,estado,telefono,celular,correo,ci,contacto);
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
*/
    public dEmpresa buscar(int id)
    {
        dEmpresa c= null;
        try {
            ResultSet rs = mysql.ejecutarSentencia("SELECT salu.emrpesa.* FROM salu.empresa WHERE EmpresaId='" + id + "'");
            if(rs.next()) {
                int idEmpresa = rs.getInt("EmpresaId");
                String nombre = rs.getNString("EmpresaNombre");
                String celular = rs.getNString("EmpresaCelular");
                String correo = rs.getNString("EmpresaMail");
                String telefono = rs.getNString("EmpresaTelefono");
                String direccion = rs.getNString("EmpresaDireccion");
                String localidad = rs.getNString("EmpresaLocalidad");
                String estado = rs.getNString("EmpresaEstado");
                String contacto = rs.getNString("EmpresaContacto");
                String ci = rs.getNString("EmpresaCI");
                String web = rs.getNString("EmpresaPaginaWeb");
                String rut = rs.getNString("EmpresaRuc");
                String texto = rs.getNString("EmpresaTexto");
                String fax = rs.getNString("EmpresaFax");
                String postal = rs.getNString("EmpresaCodigoPostal");



                c = new dEmpresa(id,nombre,direccion,localidad,estado,telefono,correo,web,rut,null,fax,celular,postal,"");
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
        public void modificar(dEmpresa c) throws SQLException {
            ResultSet rs= mysql.ejecutarSentencia("UPDATE salu.clientes SET +" +
                    "nombre='" + c.getNombre() + "'," +
                    "apellido='" + c.getApellido() + "'," +
                    "correo='" + c.getCorreo() + "'," +
                    "telefono='" + c.getTelefono() + "' " +
                    "WHERE idfuncionarios=" + c.getIdEmpresa());
            rs.close();
    
        }
    
        public void eliminar(dEmpresa f) throws SQLException {
            ResultSet rs= mysql.ejecutarSentencia("DELETE FROM salu.clientes WHERE idclientes=" + f.getIdEmpresa());
            rs.close();
        }
    */
    public ArrayList<dEmpresa> listarTodos()
    {
        ArrayList<dEmpresa> lista= new ArrayList<dEmpresa>();
        try {
            ResultSet rs= mysql.ejecutarSentencia("SELECT salu.empresa.* FROM salu.empresa");
            while (rs.next()) {
                int idEmpresa = rs.getInt("EmpresaId");
                String nombre = rs.getNString("EmpresaNombre");
                String celular = rs.getNString("EmpresaCelular");
                String correo = rs.getNString("EmpresaMail");
                String telefono = rs.getNString("EmpresaTelefono");
                String direccion = rs.getNString("EmpresaDireccion");
                String localidad = rs.getNString("EmpresaLocalidad");
                String estado = rs.getNString("EmpresaEstado");
                String contacto = rs.getNString("EmpresaContacto");
                String ci = rs.getNString("EmpresaCI");
                String web = rs.getNString("EmpresaPaginaWeb");
                String rut = rs.getNString("EmpresaRuc");
                String texto = rs.getNString("EmpresaTexto");
                String fax = rs.getNString("EmpresaFax");
                String postal = rs.getNString("EmpresaCodigoPostal");



                dEmpresa c = new dEmpresa(idEmpresa,nombre,direccion,localidad,estado,telefono,correo,web,rut,null,fax,celular,postal,"");
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
