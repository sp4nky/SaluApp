package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Herramientas.Constantes;

public class MySQL {

    private Connection conexionMySQL;
    private static MySQL instance= null;


    public MySQL(String usuarioBD, String contrBD, String ipBD, String puertoBD) {

        conexionMySQL= conectarBDMySQL(usuarioBD, contrBD, ipBD, puertoBD, Constantes.catalogoBD);

    }

    public MySQL() {

        conexionMySQL= conectarBDMySQL(Constantes.usuarioBD, Constantes.contrBD, Constantes.ipBD, Constantes.puertoBD, Constantes.catalogoBD);

    }

    public static MySQL getInstance()
    {
        if(instance == null)
            instance = new MySQL(Constantes.usuarioBD, Constantes.contrBD, Constantes.ipBD, Constantes.puertoBD);
        return instance;
    }

    public Connection getConexionMySQL() {
      //  if(conexionMySQL == null)
     //       conexionMySQL= conectarBDMySQL(Constantes.usuarioBD, Constantes.contrBD, Constantes.ipBD, Constantes.puertoBD, Constantes.catalogoBD);
        return conexionMySQL;
    }

    public Connection conectarBDMySQL (String usuario, String contrasena, String ip, String puerto, String catalogo)
    {
        if (conexionMySQL == null)
        {
            String urlConexionMySQL = "";
            if (catalogo != "")
                urlConexionMySQL = "jdbc:mysql://" + ip + ":" +	puerto + "/" + catalogo;
            else
                urlConexionMySQL = "jdbc:mysql://" + ip + ":" + puerto;
            if (usuario != "" & contrasena != "" & ip != "" & puerto != "")
            {
                try
                {

                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    //Intent i = new Intent(MySQL.this, clase);

                    //Class.forName("com.mysql.jdbc.Driver");
                    //conexionMySQL = DriverManager.getConnection(urlConexionMySQL + "/?user=" + usuario);

                    conexionMySQL =	DriverManager.getConnection(urlConexionMySQL,
                            usuario, contrasena);


                }
                catch (ClassNotFoundException e)
                {

                }
                catch (SQLException e)
                {
                    e.toString();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return conexionMySQL;
    }

    public ResultSet ejecutarSentencia(String sql)
    {
        Statement st= null;
        ResultSet rs= null;
        try {
            st = conexionMySQL.createStatement();
            rs= st.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  rs;
    }

    public int updateSentencia(String sql)
    {
        Statement st= null;
        int rs= 0;
        try {
            st = conexionMySQL.createStatement();
            rs= st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  rs;
    }

}
