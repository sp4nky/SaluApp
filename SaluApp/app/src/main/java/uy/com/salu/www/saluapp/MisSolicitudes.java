package uy.com.salu.www.saluapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Dominio.dControladora;
import Dominio.dFuncionario;
import Dominio.dSolicitud;
import Herramientas.DinamicAdapter;
import Herramientas.Tool;


public class MisSolicitudes extends ActionBarActivity {

    private ListView lstSolicitudes;
    private dSolicitud dsolicitud;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_solicitudes);

        lstSolicitudes = (ListView) this.findViewById(R.id.lstMisSolicitudes);
        bundle= getIntent().getExtras();
        correo= bundle.getString("usuario");

        usuarioBD = bundle.getString("usuarioBD");
        contrBD = bundle.getString("contrBD");
        ipBD = bundle.getString("ipBD");
        puertoBD = bundle.getString("puertoBD");

        controladora= new dControladora(usuarioBD,contrBD,ipBD,puertoBD);

        if (controladora.getMysql().getConexionMySQL()!=null) {
            funcionario = controladora.buscarFuncionarioParaLogin(correo);
            lst = controladora.listarSolicitudesFuncionario(funcionario);

            Tool[] datos= new Tool[lst.size()];
            for (int i=0; i< lst.size();i++)
            {
                Calendar c= Calendar.getInstance();
                Date hoy= new Date(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH);
                if(lst.get(i).getEstado().getId()==3)
                    datos[i]=new Tool(lst.get(i).getciudad(),lst.get(i).getCliente().getNombre(), R.drawable.ic_resuelto);
                else
                    datos[i]=new Tool(lst.get(i).getciudad(),lst.get(i).getCliente().getNombre(), R.drawable.ic_alerta);


            }

            adaptador= new DinamicAdapter(this, datos);


            adaptador.setTools(datos);
            lstSolicitudes = (ListView) this.findViewById(R.id.lstMisSolicitudes);

            lstSolicitudes.setAdapter(adaptador);
            lstSolicitudes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, final int posicion, long arg3) {
                    dsolicitud = lst.get(posicion);
                    if(dsolicitud.getEstado().getId()!=3) {
                        try {
                            Class<?> clase = Class.forName("uy.com.salu.www.saluapp.DetallesSolicitud");
                            Intent i = new Intent(MisSolicitudes.this, clase);
                            i.putExtra("usuario", correo);
                            i.putExtra("ipBD", controladora.ipBD);
                            i.putExtra("puertoBD", controladora.puertoBD);
                            i.putExtra("usuarioBD", controladora.usuarioBD);
                            i.putExtra("contrBD", controladora.contrBD);
                            i.putExtra("solicitud", dsolicitud.getIdSolicitud());
                            startActivity(i);
                        } catch (ClassNotFoundException e) {
                            // TODO Auto-generated catch block
                            //e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "No se a podido conecar con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(), "La solicitud ya se encuentra resuelta", Toast.LENGTH_SHORT).show();


                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No se a podido conecar con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
            //finish();
        }
        
        
        
        
        
/*
        if (controladora.getMysql().getConexionMySQL()!=null) {
            funcionario = controladora.buscarFuncionario(correo);
            lst = controladora.listarSolicitudesFuncionario(funcionario);
            String[] aux = new String[lst.size()];
            for (int i = 0; i < lst.size(); i++) {
                aux[i] = lst.get(i).getCuenta();

            }

            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, aux);
            lstSolicitudes.setAdapter(adaptador);

            lstSolicitudes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, final int posicion, long arg3) {
                    try {
                        Class<?> clase = Class.forName("uy.com.salu.www.saluapp.DetallesSolicitud");
                        Intent i = new Intent(MisSolicitudes.this, clase);
                        i.putExtra("usuario", correo);
                        i.putExtra("ipBD",controladora.ipBD);
                        i.putExtra("puertoBD",controladora.puertoBD);
                        i.putExtra("usuarioBD",controladora.usuarioBD);
                        i.putExtra("contrBD",controladora.contrBD);
                        dsolicitud = lst.get(posicion);
                        i.putExtra("solicitud", dsolicitud.getIdSolicitud());
                        startActivity(i);
                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No se a podido conecar con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
            finish();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mis_solicitudes, menu);
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
                Intent i = new Intent(MisSolicitudes.this, clase);
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
