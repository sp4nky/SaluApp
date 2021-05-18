package uy.com.salu.www.saluapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Dominio.dControladora;
import Dominio.dFuncionario;
import Dominio.dSolicitud;


public class MenuPrincipal extends ActionBarActivity {

    private Button btnTodasSolicitudes;
    private Button btnMisSolicitudes;
    private Button btnStockDisponible;
    private Button btnSalir;
    private Bundle bundle;
    private String correo;

    private String usuarioBD;
    private String contrBD;
    private String ipBD;
    private String puertoBD;

    private dControladora controladora;
    private ArrayList<dSolicitud> list= new ArrayList<dSolicitud>();
    private dFuncionario usuario;
    private int cantidad=0;
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;

    TimerTask mTimerTask;
    final Handler handler = new Handler();
    Timer t = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        this.btnTodasSolicitudes = (Button) this.findViewById(R.id.btnTodasSolicitudes);
        this.btnMisSolicitudes = (Button) this.findViewById(R.id.btnMisSolicitudes);
        this.btnStockDisponible = (Button) this.findViewById(R.id.btnStockDisponible);
        this.btnSalir = (Button) this.findViewById(R.id.btnSalir);

        bundle= getIntent().getExtras();
        correo= bundle.getString("usuario");
        usuarioBD = bundle.getString("usuarioBD");
        contrBD = bundle.getString("contrBD");
        ipBD = bundle.getString("ipBD");
        puertoBD = bundle.getString("puertoBD");

        controladora= dControladora.getInstance(usuarioBD,contrBD,ipBD,puertoBD);
        if(controladora.getMysql().getConexionMySQL()!=null) {
            usuario = controladora.buscarFuncionarioParaLogin(correo);
            list = controladora.listarSolicitudesFuncionario(usuario);
            if(list!=null) {
                cantidad = list.size();
            }
        }
        doTimerTask();

        this.btnTodasSolicitudes.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                try {
                    Class<?> clase = Class.forName("uy.com.salu.www.saluapp.TodasSolicitudes");
                    Intent i = new Intent(MenuPrincipal.this, clase);
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
        });

        this.btnMisSolicitudes.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                try {
                    Class<?> clase = Class.forName("uy.com.salu.www.saluapp.MisSolicitudes");
                    Intent i = new Intent(MenuPrincipal.this, clase);
                    i.putExtra("usuario", correo);
                    i.putExtra("ipBD",ipBD);
                    i.putExtra("puertoBD",puertoBD);
                    i.putExtra("usuarioBD",usuarioBD);
                    i.putExtra("contrBD",contrBD);
                    startActivity(i);
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Se ha perido la conexion intentelo mas tarde", Toast.LENGTH_SHORT).show();

                    //e.printStackTrace();
                }

            }
        });

        this.btnStockDisponible.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {

                    Class<?> clase = Class.forName("uy.com.salu.www.saluapp.StockDisponible");
                    Intent i = new Intent(MenuPrincipal.this, clase);
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
        });

        this.btnSalir.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
               // try {
                    stopTask();
                    preferenceSettings= getPreferences(0);
                    preferenceEditor= preferenceSettings.edit();
                    preferenceEditor.putString("usuario", "");
                    preferenceEditor.commit();
                    finish();
                    /*
                    Class<?> clase = Class.forName("uy.com.salu.www.saluapp.InicioSesion");

                    Intent i = new Intent(MenuPrincipal.this, clase);
                    startActivity(i);
                    */
              //  } catch (ClassNotFoundException e) {
             //       Toast.makeText(getApplicationContext(), "Se ha perido la conexion intentelo mas tarde", Toast.LENGTH_SHORT).show();
                    //e.printStackTrace();
             //   }

            }
        });
    }

    public void doTimerTask(){
        mTimerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        controladora= dControladora.getInstance(usuarioBD,contrBD,ipBD,puertoBD);
                        if(controladora.getMysql().getConexionMySQL()!=null) {
                            list = controladora.listarSolicitudesFuncionario(usuario);
                            if(cantidad<list.size())
                            {
                                cantidad=list.size();
                                notification1(
                                        cantidad,
                                        R.drawable.ic_launcher,
                                        "SaluApp",
                                        "Se le ha asignado una nueva solicitud",
                                        list.get(list.size()-1)
                                );


                            }
                            //Toast.makeText(getApplicationContext(), "hola" + cantidad, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }};

        // public void schedule (TimerTask task, long delay, long period)
        t.schedule(mTimerTask, 500, 10000);  //

    }

    public void stopTask(){

        if(mTimerTask!=null){
            /*hTextView.setText("Timer canceled: " + nCounter);

            Log.d("TIMER", "timer canceled");*/
            mTimerTask.cancel();

        }

    }

    public void notification1(int id, int iconId, String titulo, String contenido, dSolicitud solicitud) {

        Notification.Builder builder =
                new Notification.Builder(this)
                        .setSmallIcon(iconId)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                        getResources(),
                                        R.drawable.ic_launcher
                                )
                        )
                        .setContentTitle(titulo)
                        .setContentText(contenido);

        Intent notificationIntent = new Intent(MenuPrincipal.this,DetallesSolicitud.class);
        notificationIntent.putExtra("usuario", correo);
        notificationIntent.putExtra("ipBD", ipBD);
        notificationIntent.putExtra("puertoBD", puertoBD);
        notificationIntent.putExtra("usuarioBD", usuarioBD);
        notificationIntent.putExtra("contrBD", contrBD);
        notificationIntent.putExtra("solicitud", solicitud.getIdSolicitud());

        // Sonido por defecto de notificaciones, podemos usar otro
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Uso en API 11 o mayor
        builder.setSound(defaultSound);

        PendingIntent pendingIntent = PendingIntent.getActivity(MenuPrincipal.this, 0, notificationIntent, 0);

        builder.setContentIntent(pendingIntent);

        // Construir la notificación y emitirla
        NotificationManager notifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifyMgr.notify(id, builder.build());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.ic_apagar)
        {

                stopTask();
                preferenceSettings= getPreferences(0);
                preferenceEditor = preferenceSettings.edit();
                preferenceEditor.putString("usuario", "");
                preferenceEditor.commit();


                finish();

        }
        if (id== R.id.ic_perfil)
        {
            try {

                Class<?> clase = Class.forName("uy.com.salu.www.saluapp.Perfil");
                Intent i = new Intent(MenuPrincipal.this, clase);
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

        return super.onOptionsItemSelected(item);
    }
}
