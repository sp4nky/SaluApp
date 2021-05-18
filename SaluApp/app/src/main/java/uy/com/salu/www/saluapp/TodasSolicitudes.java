package uy.com.salu.www.saluapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

import Dominio.dControladora;
import Dominio.dFuncionario;
import Dominio.dSolicitud;
import Herramientas.DinamicAdapter;
import Herramientas.Tool;
import Persistencia.pFuncionario;
import Persistencia.pSolicitud;


public class TodasSolicitudes extends ActionBarActivity {

    private ListView lstSolicitudes;
    private pSolicitud psolicitud;//= new pSolicitud();
    private dSolicitud dsolicitud;
    private pFuncionario pfunc;//= new pFuncionario();
    private dFuncionario funcionario;
    private ArrayList<dSolicitud> lst;
    private Bundle bundle;
    private String correo;

    private String usuarioBD;
    private String contrBD;
    private String ipBD;
    private String puertoBD;
    private dControladora controladora;

    private DinamicAdapter adaptador;
    private Context context= this;
    Tool[] datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todas_solicitudes);

        lstSolicitudes = (ListView) this.findViewById(R.id.lstSolicitudes);
        bundle= getIntent().getExtras();
        correo= bundle.getString("usuario");

        usuarioBD = bundle.getString("usuarioBD");
        contrBD = bundle.getString("contrBD");
        ipBD = bundle.getString("ipBD");
        puertoBD = bundle.getString("puertoBD");

        controladora= new dControladora(usuarioBD,contrBD,ipBD,puertoBD);

        if (controladora.getMysql().getConexionMySQL()!=null) {

            funcionario = controladora.buscarFuncionario(correo);
            lst = controladora.listarTodosNoAsignadasFuncionario(funcionario);
            datos= new Tool[lst.size()];
            for (int i=0; i< lst.size();i++)
            {
                if(lst.get(i).getEstado().getId()!=3)
                    datos[i]=new Tool(lst.get(i).getciudad(),lst.get(i).getCliente().getNombre(), R.drawable.ic_alerta);

            }

            adaptador= new DinamicAdapter(this, datos);


            adaptador.setTools(datos);

            lstSolicitudes = (ListView) this.findViewById(R.id.lstSolicitudes);
            lstSolicitudes.setAdapter(adaptador);

            lstSolicitudes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, final int posicion, long arg3) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(TodasSolicitudes.this);
                    builder.setMessage("¿Desea asignarse esta solicitud?")
                            .setCancelable(false)
                            .setTitle("Confirmar")
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            try {
                                                controladora.asignarSolicitudFuncionario(lst.get(posicion), funcionario);
                                                lst.remove(lst.get(posicion));
                                                datos = new Tool[lst.size()];
                                                for (int i = 0; i < lst.size(); i++) {
                                                    datos[i] = new Tool(lst.get(i).getciudad(), lst.get(i).getCliente().getNombre(), R.drawable.abc_item_background_holo_dark);

                                                }

                                                adaptador = new DinamicAdapter(TodasSolicitudes.this, datos);


                                                adaptador.setTools(datos);

                                                lstSolicitudes.setAdapter(adaptador);

                                            } catch (SQLException e) {
                                                dialog.cancel();
                                                Toast.makeText(getApplicationContext(), "No se a podido conecar con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    })
                            .setNegativeButton("Cancelar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // cancel the dialog box
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

        }
        else
        {

            Toast.makeText(getApplicationContext(), "No se a podido conecar con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todas_solicitudes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id== R.id.ic_perfil)
        {
            try {

                Class<?> clase = Class.forName("uy.com.salu.www.saluapp.Perfil");
                Intent i = new Intent(TodasSolicitudes.this, clase);
                i.putExtra("usuario", correo);
                i.putExtra("ipBD",ipBD);
                i.putExtra("puertoBD",puertoBD);
                i.putExtra("usuarioBD",usuarioBD);
                i.putExtra("contrBD",contrBD);
                startActivity(i);
            } catch (ClassNotFoundException e) {
                Toast.makeText(getApplicationContext(), "Se ha perido la conexion intentelo mas tarde", Toast.LENGTH_SHORT).show();
                //e.printStackTrace();
            }
        }
        if (id== R.id.ic_retroceso) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
