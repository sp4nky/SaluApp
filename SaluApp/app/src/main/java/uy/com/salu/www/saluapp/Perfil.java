package uy.com.salu.www.saluapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Dominio.dControladora;
import Dominio.dFuncionario;


public class Perfil extends ActionBarActivity {

    private TextView txtNombre;
    private TextView txtCorreo;
    private TextView txtTelefono;
    private TextView txtGrupo;
    private ImageView imgPerfil;
    private TextView txtLocalidad;
    private TextView txtEstado;
    private TextView txtDireccion;
    private TextView txtCelular;
    private TextView txtCargo;

    private Bundle bundle;
    private String correo;

    private String usuarioBD;
    private String contrBD;
    private String ipBD;
    private String puertoBD;

    private dControladora controladora;
    private dFuncionario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        txtNombre = (TextView) findViewById(R.id.txtNombreFuncionario);
        txtCorreo = (TextView) findViewById(R.id.txtCorreoFuncionario);
        txtTelefono = (TextView) findViewById(R.id.txtTelefonoFuncionario);
        txtGrupo = (TextView) findViewById(R.id.txtGrupoFuncionario);

        txtLocalidad = (TextView) findViewById(R.id.txtLocalidadFuncionario);
        txtEstado= (TextView) findViewById(R.id.txtEstadoFuncionario);
        txtDireccion = (TextView) findViewById(R.id.txtDireccionFuncionario);
        txtCelular= (TextView) findViewById(R.id.txtCelularFuncionario);
        txtCargo = (TextView) findViewById(R.id.txtCargoFuncionarios);

        imgPerfil = (ImageView) findViewById(R.id.imgPerfil);
        imgPerfil.setImageResource(R.drawable.ic_perfil_predeterminada);

        bundle = getIntent().getExtras();
        correo = bundle.getString("usuario");
        usuarioBD = bundle.getString("usuarioBD");
        contrBD = bundle.getString("contrBD");
        ipBD = bundle.getString("ipBD");
        puertoBD = bundle.getString("puertoBD");

        controladora = dControladora.getInstance(usuarioBD, contrBD, ipBD, puertoBD);
        if (controladora.getMysql().getConexionMySQL() != null) {
            usuario=controladora.buscarFuncionario(correo);
            txtNombre.setText(usuario.getNombre());
            txtCorreo.setText(correo);
            txtTelefono.setText(usuario.getTelefono());
            txtGrupo.setText(usuario.getGrupo().getNombre());

            txtLocalidad.setText(usuario.getLocalidad());
            txtEstado.setText(usuario.getEstado());
            txtDireccion.setText(usuario.getDireccion());
            txtCelular.setText(usuario.getCelular());
            txtCargo.setText(usuario.getCargo());

            if (usuario.getFoto()!=null)
            {

                Bitmap bmp= BitmapFactory.decodeFile(usuario.getFoto().getPath());
                imgPerfil.setImageBitmap(bmp);
                //imgPerfil.setImageURI(Uri.fromFile(usuario.getFoto()));
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Se ha perido la conexion intentelo mas tarde", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void finish() {
        if(usuario.getFoto()!=null)
        {
            usuario.getFoto().delete();
        }
        super.finish();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ic_retroceso)
        {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

}