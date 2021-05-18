package uy.com.salu.www.saluapp;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Dominio.dCliente;
import Dominio.dControladora;
import Dominio.dFuncionario;
import Dominio.dHistorial;
import Dominio.dNotas;
import Dominio.dRepuestos;
import Dominio.dSolicitud;
import Herramientas.DirectionsJSONParser;

public class DetallesSolicitud extends ActionBarActivity implements MediaPlayer.OnCompletionListener {

    private Uri url1;
    private dControladora controladora;
    private Bundle bundle;
    private String correo;
    private int idSolicitud;
    private TableLayout tablaStock;
    private TableLayout tablaTecnicos;
    private TabHost tabs;
    private dRepuestos repuesto;
    private dFuncionario usuario;
    private dFuncionario tecnico;
    private dSolicitud solicitud;
    private Button btnResolver;
    private TextView txtCliente;
    private TextView txtTipo;
    private TextView txtFecha;
    private TextView txtDepartamento;
    private TextView txtCiudad;
    private TextView txtEstado;
    private NumberPicker listViewCantidad;
    private Spinner listViewRepuestos;
    private Button btnAgregarRepuesto;

    private TextView txtDiagnostico;
    private TextView txtDireccion;
    private EditText txtComentario;

    static final LatLng HAMBURG = new LatLng(-34.8581, -56.1708);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    private LatLng miUbicacion;
    private GoogleMap map;

    private LocationManager locationMangaer=null;
    private LocationListener locationListener=null;

    private String usuarioBD;
    private String contrBD;
    private String ipBD;
    private String puertoBD;

    private Button btnAgregarNota;
    private Button btnDetener;
    private ImageButton btnGrabar;
    private Button btnReproducir;
    private static final String TAG = "Debug";
    private Boolean flag = false;
    private Chronometer cronometro;
    private Long cronoFin= SystemClock.elapsedRealtime();
    ArrayList<dRepuestos> listaTodosRepuestos;


    MediaRecorder recorder;
    MediaPlayer player;
    File archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_solicitud);

        bundle = getIntent().getExtras();
        correo= bundle.getString("usuario");
        idSolicitud = bundle.getInt("solicitud");
        usuarioBD = bundle.getString("usuarioBD");
        contrBD = bundle.getString("contrBD");
        ipBD = bundle.getString("ipBD");
        puertoBD = bundle.getString("puertoBD");

        controladora= new dControladora(usuarioBD,contrBD,ipBD,puertoBD);

        if (controladora.getMysql().getConexionMySQL()!= null) {


            usuario = controladora.buscarFuncionario(correo);
            solicitud = controladora.buscarSolicitud(idSolicitud);

            Resources res = getResources();

            tabs = (TabHost) this.findViewById(R.id.tabHost);
            tabs.setup();

            TabHost.TabSpec spec = tabs.newTabSpec("mitab1");

            spec.setContent(R.id.Detalles);
            spec.setIndicator("Detalles",
                    res.getDrawable(android.R.drawable.ic_dialog_map));
            tabs.addTab(spec);

            //DATOS DE LA SOLICITUD

            //"Nombre y Apellido"
            txtCliente = (TextView) this.findViewById(R.id.txtCliente);
            dCliente cli = solicitud.getCliente();
            txtCliente.setText(cli.getNombre());
            //Tipo
            txtTipo = (TextView) this.findViewById(R.id.txtTipo);
            txtTipo.setText(solicitud.getTipo());

            //dd/mm/aa (dia)
            txtFecha = (TextView) this.findViewById(R.id.txtFecha);
            java.util.Date Fecha = solicitud.getFecha();
            String fechaSolicitud = Fecha.toString();
            txtFecha.setText(fechaSolicitud);

            //(Departamento)
            txtDepartamento = (TextView) this.findViewById(R.id.txtDepartamento);
            txtDepartamento.setText(solicitud.getDepartamento());

            //(Ciudad)
            txtCiudad = (TextView) this.findViewById(R.id.txtCiudad);
            txtCiudad.setText(solicitud.getciudad());

            //(Direccion: Calle Numero)
            txtDireccion = (TextView) this.findViewById(R.id.txtDireccion);
            txtDireccion.setText(solicitud.getDireccion());

            //(Estado de la solicitud)
            txtEstado = (TextView) this.findViewById(R.id.txtEstado);
            txtEstado.setText(solicitud.getEstado().getNombre());


            //(Diagnostico)
            txtDiagnostico = (TextView) this.findViewById(R.id.txtDiagnostico);
            txtDiagnostico.setText(solicitud.getDiagnostico());

            //Boton Resolver
            btnResolver = (Button) this.findViewById(R.id.btnResolver);
            txtComentario = (EditText) this.findViewById(R.id.txtComentario);

            this.btnResolver.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if (archivo != null) {
                        solicitud.setComentarioHablado(archivo);
                        solicitud.setComentarioHabladoNombre("Comentario" + solicitud.getIdSolicitud());
                        if (!controladora.asignarComentarioHablado(solicitud)) {
                            Toast.makeText(getApplicationContext(), "No se a podido conecar con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Calendar cal= Calendar.getInstance();
                    Date hoy=new Date(cal.get(cal.YEAR),cal.get(cal.MONTH),cal.get(cal.DAY_OF_MONTH));
                    solicitud.setFechaFinalizado(hoy);
                    if(!controladora.marcarSolicitudResuelta(solicitud))
                        Toast.makeText(getApplicationContext(), "No se a podido conecar con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(getApplicationContext(), "Se ha marcado la solicitud como resuelta", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
            });

            btnAgregarNota = (Button) this.findViewById(R.id.btnAgregarNota);

            this.btnAgregarNota.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if (txtComentario.getText().toString()!="") {
                        Calendar cal= Calendar.getInstance();

                        Date hoy=new Date(cal.get(cal.YEAR),cal.get(cal.MONTH),cal.get(cal.DAY_OF_MONTH));

                        dNotas nota= new dNotas(0,solicitud,usuario.getNombre(),hoy,txtComentario.getText().toString());
                        if(controladora.agregarNota(nota)) {
                            Toast.makeText(getApplicationContext(), "Se ha agregado el comentario a la solicitud", Toast.LENGTH_SHORT).show();
                            dHistorial historial=new dHistorial(0,hoy,solicitud,usuario.getNombre(),"Ingreso la nota técnica: "+ nota.getTexto());
                            if(!controladora.agregarHistorial(historial)) {
                                Toast.makeText(getApplicationContext(), "Se ha agregado el comentario pero no se ha podido guardar en el historial", Toast.LENGTH_SHORT).show();

                            }
                        }

                        else
                        {
                            Toast.makeText(getApplicationContext(), "No se a podido conecar con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });




            cronometro = (Chronometer) this.findViewById(R.id.cronometro);

            btnGrabar = (ImageButton) this.findViewById(R.id.btnGrabar);
            btnDetener = (Button) this.findViewById(R.id.btnDetener);
            btnReproducir = (Button) this.findViewById(R.id.btnReproducir);
            btnReproducir.setEnabled(false);

            btnGrabar.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                        if(archivo!=null)
                        {
                            archivo.delete();
                        }

                        recorder = new MediaRecorder();
                        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                        File path = new File(Environment.getExternalStorageDirectory()
                                .getPath());
                        try {
                            archivo = File.createTempFile("Comentario" + solicitud.getIdSolicitud(), ".3gp", path);
                        } catch (IOException e) {
                        }
                        recorder.setOutputFile(archivo.getAbsolutePath());
                        try {
                            recorder.prepare();
                        } catch (IOException e) {
                        }

                        recorder.start();
                        cronometro.setBase(SystemClock.elapsedRealtime());
                        cronometro.start();
                        //btnGrabar.setEnabled(false);
                        //btnDetener.setEnabled(true);
                        return true;
                    }
                    else if (event.getAction() == MotionEvent.ACTION_UP){
                        recorder.stop();
                        recorder.release();
                        player = new MediaPlayer();
                        player.setOnCompletionListener(DetallesSolicitud.this);
                        try {
                            player.setDataSource(archivo.getAbsolutePath());
                        } catch (IOException e) {
                        }
                        try {
                            player.prepare();
                        } catch (IOException e) {
                        }
                        //btnGrabar.setEnabled(true);
                        //btnDetener.setEnabled(false);
                        btnReproducir.setEnabled(true);
                        cronometro.stop();
                        cronoFin=cronometro.getBase();
                    }

                    return false;
                }
            });



            spec = tabs.newTabSpec("mitab2");
            spec.setContent(R.id.Mapa);
            spec.setIndicator("",
                    res.getDrawable(android.R.drawable.ic_menu_myplaces));
            tabs.addTab(spec);


            //Creacion de Mapa
            setUpMapIfNeeded();

            // Get LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if(solicitud.getCiudad()!=null) {

                LatLng ubicacionSolicitud = getLocationFromAddress(solicitud.getDireccion() + ", " + solicitud.getciudad() + ", Uruguay");

            map.addMarker(new MarkerOptions()
                .position(ubicacionSolicitud))
                    .setTitle("Solicitud");
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionSolicitud, 15));

                //Si el GPS no está habilitado
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    alertboxGPSDisable();
                } else {
                    //Activamos la capa o layer MyLocation
                    map.setMyLocationEnabled(true);

                    if (ubicacionSolicitud != null) {


                        // Create a criteria object to retrieve provider
                        Criteria criteria = new Criteria();

                        // Get the name of the best provider
                        String provider = locationManager.getBestProvider(criteria, true);

                        // Get Current Location
                        Location myLocation = locationManager.getLastKnownLocation(provider);

                        LatLng origin = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                        LatLng dest = ubicacionSolicitud;

                        // Getting URL to the Google Directions API
                        String url = getDirectionsUrl(origin, dest);

                        DownloadTask downloadTask = new DownloadTask();

                        // Start downloading json data from Google Directions API
                        downloadTask.execute(url);
                    }

                }
            }


            //Pestana Tecnicos

            spec = tabs.newTabSpec("mitab4");
            spec.setContent(R.id.Tecnicos);
            spec.setIndicator("Tecnicos",
                    res.getDrawable(android.R.drawable.ic_dialog_map));
            tabs.addTab(spec);

            //Mostrar Tecnicos asignados a la solicitud

            tablaTecnicos = (TableLayout) this.findViewById(R.id.TecnicosAsignados);
            ArrayList<dFuncionario> listaTecnicos = controladora.listarFuncionariosSolicitud(solicitud);

            TableRow tr_head_Tecnicos = new TableRow(DetallesSolicitud.this);
            //tr_head.setId(13);
            tr_head_Tecnicos.setBackgroundColor(Color.GRAY);
            tr_head_Tecnicos.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView label_nombre = new TextView(DetallesSolicitud.this);
            // label_fecha.setId(25);
            label_nombre.setText("Nombre");
            label_nombre.setTextColor(Color.WHITE);
            label_nombre.setBackgroundColor(Color.GRAY);
            label_nombre.setPadding(5, 5, 5, 5);
            tr_head_Tecnicos.addView(label_nombre);

            TextView label_telefono = new TextView(DetallesSolicitud.this);
            // label_cantidad.setId(26);
            label_telefono.setText("Celular");
            label_telefono.setBackgroundColor(Color.GRAY);
            label_telefono.setTextColor(Color.WHITE);
            label_telefono.setPadding(5, 5, 5, 5);
            tr_head_Tecnicos.addView(label_telefono);


            tablaTecnicos.addView(tr_head_Tecnicos, new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            for (int i = 0; i < listaTecnicos.size(); i++) {


                tecnico = listaTecnicos.get(i);

                TableRow tr = new TableRow(DetallesSolicitud.this);
                if (i % 2 != 0)
                    tr.setBackgroundColor(Color.GRAY);
                else
                    tr.setBackgroundColor(Color.rgb(189, 202, 93));
                // tr.setId(100+i);
                tr.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));


                TextView labelnombre = new TextView(DetallesSolicitud.this);
                //labelnombre.setId(200+i);
                labelnombre.setText(tecnico.getNombre());
                labelnombre.setPadding(5, 0, 5, 0);
                labelnombre.setTextColor(Color.WHITE);
                if (i % 2 != 0)
                    labelnombre.setBackgroundColor(Color.GRAY);
                else
                    labelnombre.setBackgroundColor(Color.rgb(189, 202, 93));
                tr.addView(labelnombre);

                TextView labeltelefono = new TextView(DetallesSolicitud.this);
                // labelcantidad.setId(300+i);
                labeltelefono.setText(String.valueOf(tecnico.getCelular()));
                labeltelefono.setTextColor(Color.WHITE);
                if (i % 2 != 0)
                    labeltelefono.setBackgroundColor(Color.GRAY);
                else
                    labeltelefono.setBackgroundColor(Color.rgb(189, 202, 93));
                tr.addView(labeltelefono);


                tablaTecnicos.addView(tr, new TableLayout.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

            }


            //Mostrar Stock Necesario

            spec = tabs.newTabSpec("mitab3");
            spec.setContent(R.id.Stock);
            spec.setIndicator("Otros",
                    res.getDrawable(android.R.drawable.ic_dialog_map));
            tabs.addTab(spec);

            listViewCantidad = (NumberPicker) this.findViewById(R.id.listViewCantidad);
            listViewCantidad.setMinValue(1);
            listViewCantidad.setMaxValue(20);


            listViewRepuestos = (Spinner) this.findViewById(R.id.listViewRepuestos);


            listaTodosRepuestos = controladora.listarTodosRepuestos();
            ArrayList<String> repuestos = new ArrayList<String>();
            if(listaTodosRepuestos!=null) {
                for (int i = 0; i < listaTodosRepuestos.size(); i++) {
                    repuestos.add(listaTodosRepuestos.get(i).getNombre() + " " + listaTodosRepuestos.get(i).getModelo().getNombre()+ " " + listaTodosRepuestos.get(i).getModelo().getMarca().getNombre());
                }
                ArrayAdapter<String> repuestosAdapter = new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        repuestos);
                listViewRepuestos.setAdapter(repuestosAdapter);
            }




            tablaStock = (TableLayout) this.findViewById(R.id.StockNecesario);


            TableRow tr_head = new TableRow(DetallesSolicitud.this);
            //tr_head.setId(13);
            tr_head.setBackgroundColor(Color.GRAY);
            tr_head.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView label_fecha = new TextView(DetallesSolicitud.this);
            // label_fecha.setId(25);
            label_fecha.setText("Nombre");
            label_fecha.setTextColor(Color.WHITE);
            label_fecha.setPadding(5, 5, 5, 5);
            label_fecha.setBackgroundColor(Color.GRAY);
            tr_head.addView(label_fecha);

            TextView label_cantidad = new TextView(DetallesSolicitud.this);
            // label_cantidad.setId(26);
            label_cantidad.setBackgroundColor(Color.GRAY);
            label_cantidad.setText("Cant.");
            label_cantidad.setTextColor(Color.WHITE);
            label_cantidad.setPadding(5, 5, 5, 5);
            tr_head.addView(label_cantidad);

            TextView label_marca = new TextView(DetallesSolicitud.this);
            // label_nota.setId(26);
            label_marca.setText("Marca");
            label_marca.setBackgroundColor(Color.GRAY);
            label_marca.setTextColor(Color.WHITE);
            label_marca.setPadding(5, 5, 5, 5);
            tr_head.addView(label_marca);

            TextView label_modelo = new TextView(DetallesSolicitud.this);
            // label_nota.setId(26);
            label_modelo.setText("Modelo");
            label_modelo.setTextColor(Color.WHITE);
            label_modelo.setPadding(5, 5, 5, 5);
            label_modelo.setBackgroundColor(Color.GRAY);
            tr_head.addView(label_modelo);

            TextView label_tipo = new TextView(DetallesSolicitud.this);
            // label_nota.setId(26);
            label_tipo.setText("Tipo");
            label_tipo.setTextColor(Color.WHITE);
            label_tipo.setPadding(5, 5, 5, 5);
            label_tipo.setBackgroundColor(Color.GRAY);
            tr_head.addView(label_tipo);

            tablaStock.addView(tr_head, new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));


            final ArrayList<dRepuestos> listaStock = controladora.listarRepuestosSolicitud(solicitud);
            for (int i = 0; i < listaStock.size(); i++) {


                repuesto = listaStock.get(i);

                TableRow tr = new TableRow(DetallesSolicitud.this);
                if (i % 2 != 0)
                    tr.setBackgroundColor(Color.GRAY);
                else
                    tr.setBackgroundColor(Color.rgb(189, 202, 93));
                // tr.setId(100+i);
                tr.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));


                TextView labelnombre = new TextView(DetallesSolicitud.this);
                //labelnombre.setId(200+i);
                labelnombre.setText(repuesto.getNombre());
                //labelnombre.setPadding(5, 0, 5, 0);
                labelnombre.setTextColor(Color.WHITE);
                if (i % 2 != 0)
                    labelnombre.setBackgroundColor(Color.GRAY);
                else
                    labelnombre.setBackgroundColor(Color.rgb(189, 202, 93));
                tr.addView(labelnombre);

                TextView labelcantidad = new TextView(DetallesSolicitud.this);
                // labelcantidad.setId(300+i);
                labelcantidad.setText(String.valueOf(repuesto.getCantidad()));
                labelcantidad.setTextColor(Color.WHITE);
                if (i % 2 != 0)
                    labelcantidad.setBackgroundColor(Color.GRAY);
                else
                    labelcantidad.setBackgroundColor(Color.rgb(189, 202, 93));
                tr.addView(labelcantidad);

                TextView labelmarca = new TextView(DetallesSolicitud.this);
                // labelmarca.setId(300+i);
                labelmarca.setText(String.valueOf(repuesto.getModelo().getMarca().getNombre()));
                labelmarca.setTextColor(Color.WHITE);
                if (i % 2 != 0)
                    labelmarca.setBackgroundColor(Color.GRAY);
                else
                    labelmarca.setBackgroundColor(Color.rgb(189, 202, 93));
                tr.addView(labelmarca);

                TextView labelmodelo = new TextView(DetallesSolicitud.this);
                // labelmodelo.setId(300+i);
                labelmodelo.setText(String.valueOf(repuesto.getModelo().getNombre()));
                labelmodelo.setTextColor(Color.WHITE);
                if (i % 2 != 0)
                    labelmodelo.setBackgroundColor(Color.GRAY);
                else
                    labelmodelo.setBackgroundColor(Color.rgb(189, 202, 93));
                tr.addView(labelmodelo);

                TextView labeltipo = new TextView(DetallesSolicitud.this);
                // labeltipo.setId(300+i);
                labeltipo.setText(String.valueOf(repuesto.getFamilia().getNombre()));
                labeltipo.setTextColor(Color.WHITE);
                if (i % 2 != 0)
                    labeltipo.setBackgroundColor(Color.GRAY);
                else
                    labeltipo.setBackgroundColor(Color.rgb(189, 202, 93));
                tr.addView(labeltipo);


                tablaStock.addView(tr, new TableLayout.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

            }

            btnAgregarRepuesto= (Button) this.findViewById(R.id.btnAgregarRepuesto);
            if(listaTodosRepuestos==null)
                btnAgregarRepuesto.setEnabled(false);
            this.btnAgregarRepuesto.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //if(listViewRepuestos.isSelected()) {
                        repuesto = listaTodosRepuestos.get(listViewRepuestos.getSelectedItemPosition());
                        int cantidadRepuesto= listViewCantidad.getValue();
                        if(!listaStock.contains(repuesto)) {
                            controladora.agregarRepuestoSolicitud(repuesto, solicitud, cantidadRepuesto);
                            TableRow tr = new TableRow(DetallesSolicitud.this);
                            if (listaStock.size() % 2 != 0)
                                tr.setBackgroundColor(Color.GRAY);
                            else
                                tr.setBackgroundColor(Color.rgb(189, 202, 93));
                            // tr.setId(100+i);
                            tr.setLayoutParams(new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.FILL_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));


                            TextView labelnombre = new TextView(DetallesSolicitud.this);
                            //labelnombre.setId(200+i);
                            labelnombre.setText(repuesto.getNombre());
                            //labelnombre.setPadding(5, 0, 5, 0);
                            labelnombre.setTextColor(Color.WHITE);
                            if (listaStock.size() % 2 != 0)
                                labelnombre.setBackgroundColor(Color.GRAY);
                            else
                                labelnombre.setBackgroundColor(Color.rgb(189, 202, 93));
                            tr.addView(labelnombre);

                            TextView labelcantidad = new TextView(DetallesSolicitud.this);
                            // labelcantidad.setId(300+i);
                            labelcantidad.setText(String.valueOf(cantidadRepuesto));
                            labelcantidad.setTextColor(Color.WHITE);
                            if (listaStock.size() % 2 != 0)
                                labelcantidad.setBackgroundColor(Color.GRAY);
                            else
                                labelcantidad.setBackgroundColor(Color.rgb(189, 202, 93));
                            tr.addView(labelcantidad);

                            TextView labelmarca = new TextView(DetallesSolicitud.this);
                            // labelmarca.setId(300+i);
                            labelmarca.setText(String.valueOf(repuesto.getModelo().getMarca().getNombre()));
                            labelmarca.setTextColor(Color.WHITE);
                            if (listaStock.size() % 2 != 0)
                                labelmarca.setBackgroundColor(Color.GRAY);
                            else
                                labelmarca.setBackgroundColor(Color.rgb(189, 202, 93));
                            tr.addView(labelmarca);

                            TextView labelmodelo = new TextView(DetallesSolicitud.this);
                            // labelmodelo.setId(300+i);
                            labelmodelo.setText(String.valueOf(repuesto.getModelo().getNombre()));
                            labelmodelo.setTextColor(Color.WHITE);
                            if (listaStock.size() % 2 != 0)
                                labelmodelo.setBackgroundColor(Color.GRAY);
                            else
                                labelmodelo.setBackgroundColor(Color.rgb(189, 202, 93));
                            tr.addView(labelmodelo);

                            TextView labeltipo = new TextView(DetallesSolicitud.this);
                            // labeltipo.setId(300+i);
                            labeltipo.setText(String.valueOf(repuesto.getFamilia().getNombre()));
                            labeltipo.setTextColor(Color.WHITE);
                            if (listaStock.size() % 2 != 0)
                                labeltipo.setBackgroundColor(Color.GRAY);
                            else
                                labeltipo.setBackgroundColor(Color.rgb(189, 202, 93));
                            tr.addView(labeltipo);


                            tablaStock.addView(tr, new TableLayout.LayoutParams(
                                    ViewGroup.LayoutParams.FILL_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));

                        }
                        else
                            Toast.makeText(getApplicationContext(), "El repuesto que de sea agregar ya se encuentra en la lista", Toast.LENGTH_SHORT).show();

                    }

                //}
            });


            tabs.setCurrentTab(0);

        }
        else
        {
            Toast.makeText(getApplicationContext(), "No se a podido conecar con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
            finish();
        }



    }
/*
    public void subirArchivo() throws SocketException, UnknownHostException, IOException {

        try {

            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(InetAddress.getByName(ipBD));

            ftpClient.setDefaultPort(21);
            ftpClient.login("Nanis", "");

            ftpClient.changeWorkingDirectory("/ftp");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            BufferedInputStream buffIn=null;

            buffIn=new BufferedInputStream(new FileInputStream(archivo.getPath()));
            ftpClient.enterLocalPassiveMode();
            ftpClient.storeFile(archivo.getName(), buffIn);

            buffIn.close();
            ftpClient.logout();
            ftpClient.disconnect();

        } catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            //Log.i("consola","Ups...");
        }
    }
*/




    public void grabar(View v) {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        File path = new File(Environment.getExternalStorageDirectory()
                .getPath());
        try {
            archivo = File.createTempFile("Comentario" + solicitud.getIdSolicitud(), ".3gp", path);
        } catch (IOException e) {
        }
        recorder.setOutputFile(archivo.getAbsolutePath());
        try {
            recorder.prepare();
        } catch (IOException e) {
        }

        recorder.start();
        cronometro.setBase(SystemClock.elapsedRealtime());
        cronometro.start();
        btnGrabar.setEnabled(false);
        btnDetener.setEnabled(true);

    }

    public void detener(View v) {
        recorder.stop();
        recorder.release();
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        try {
            player.setDataSource(archivo.getAbsolutePath());
        } catch (IOException e) {
        }
        try {
            player.prepare();
        } catch (IOException e) {
        }
        btnGrabar.setEnabled(true);
        btnDetener.setEnabled(false);
        btnReproducir.setEnabled(true);
        cronometro.stop();
        cronoFin=cronometro.getBase();

    }

    public void reproducir(View v) {
        player.start();
        btnGrabar.setEnabled(false);
        btnDetener.setEnabled(false);
        btnReproducir.setEnabled(false);
        cronometro.setBase(SystemClock.elapsedRealtime());
        cronometro.start();
        //tv1.setText("Reproduciendo");
    }

    public void onCompletion(MediaPlayer mp) {
        btnGrabar.setEnabled(true);
        btnDetener.setEnabled(true);
        btnReproducir.setEnabled(true);
        cronometro.stop();
        //cronometro.setBase(cronoFin);
        //tv1.setText("Listo");
    }
    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);

            p1 = new LatLng(location.getLatitude(), location.getLongitude());


        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "No se a podido conecar con el servidor", Toast.LENGTH_SHORT).show();

            //e.printStackTrace();
        }
        return p1;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        int accion = MotionEventCompat.getActionMasked(event);
        if (accion==MotionEvent.EDGE_LEFT)
        {
            Toast.makeText(getApplicationContext(), "izquierda", Toast.LENGTH_SHORT).show();
        }
        else
            if(accion==MotionEvent.EDGE_RIGHT)
            {
                Toast.makeText(getApplicationContext(), "derecha", Toast.LENGTH_SHORT).show();
            }
        return super.onTouchEvent(event);
    }

    private void setUpMapIfNeeded() {
    // Configuramos el objeto GoogleMaps con valores iniciales.
        if (map == null) {
            //Instanciamos el objeto mMap a partir del MapFragment definido bajo el Id "map"
            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapsDir)).getMap();
            // Chequeamos si se ha obtenido correctamente una referencia al objeto GoogleMap
            if (map != null) {
                // El objeto GoogleMap ha sido referenciado correctamente
                //ahora podemos manipular sus propiedades

                //Seteamos el tipo de mapa
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);




            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalles_solicitud, menu);
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
                Intent i = new Intent(DetallesSolicitud.this, clase);
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


    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }



    /*----------Method to create an AlertBox ------------- */
    protected void alertboxGPSDisable() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El dispositivo GPS se encuentra deshabilitado")
                .setCancelable(false)
                .setTitle("** Estado del GPS **")
                .setPositiveButton("Encender GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish the current activity
                                // AlertBoxAdvance.this.finish();
                                Intent myIntent = new Intent(
                                        Settings.ACTION_SECURITY_SETTINGS);
                                startActivity(myIntent);
                                dialog.cancel();
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

    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Waypoints
        String waypoints = "";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;//+"&"+waypoints;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

           // Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service

            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {

            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(2);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            map.addPolyline(lineOptions);
        }
    }

}
