package uy.com.salu.www.saluapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;

import Dominio.dControladora;
import Dominio.dFuncionario;
import Herramientas.Constantes;
import Persistencia.MySQL;
import Persistencia.pFuncionario;


public class InicioSesion extends ActionBarActivity {

    private dFuncionario dfunc;
    private pFuncionario pfunc;


    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Connection conexion;
    private MySQL mysql;
    private dControladora controladora;

    private String usuarioBD= Constantes.usuarioBD;
    private String contrBD=Constantes.contrBD;
    private String ipBD=Constantes.ipBD;
    private String puertoBD=Constantes.puertoBD;

    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.correo);
        mPasswordView = (EditText) findViewById(R.id.contr);
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        preferenceSettings= getPreferences(0);
        usuarioBD= preferenceSettings.getString("usuarioBD", Constantes.usuarioBD);
        contrBD= preferenceSettings.getString("contrBD",Constantes.contrBD);
        ipBD= preferenceSettings.getString("ipBD",Constantes.ipBD);
        puertoBD= preferenceSettings.getString("puertoBD",Constantes.puertoBD);

        if(preferenceSettings.getString("usuario","")!="")
        {
            controladora= new dControladora(usuarioBD,contrBD,ipBD,puertoBD);
            if (controladora.getMysql().getConexionMySQL()==null)
            {
                mEmailView.setText(preferenceSettings.getString("usuario",""));
                Toast.makeText(getApplicationContext(), "No se a podido conecar con el servidor" + " " + controladora.ipBD, Toast.LENGTH_SHORT).show();
            }
            else
            {
                Intent i = new Intent(this, MenuPrincipal.class);
                i.putExtra("usuario", preferenceSettings.getString("usuario",""));
                i.putExtra("ipBD",controladora.ipBD);
                i.putExtra("puertoBD",controladora.puertoBD);
                i.putExtra("usuarioBD",controladora.usuarioBD);
                i.putExtra("contrBD",controladora.contrBD);
                startActivity(i);
            }
        }


        Button mEmailSignInButton = (Button) findViewById(R.id.btninicio);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //si no se conecta con el servidor luego de solucionar el problema de concexion el problema esta aqui
                controladora= new dControladora(usuarioBD,contrBD,ipBD,puertoBD);
                if (controladora.getMysql().getConexionMySQL()==null)
                {
                    Toast.makeText(getApplicationContext(), "No se a podido conecar con el servidor" + " " + controladora.ipBD, Toast.LENGTH_SHORT).show();
                }
                else
                    attemptLogin(view);
            }
        });



    }

    public void attemptLogin(View v) {

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        dfunc=controladora.buscarFuncionarioParaLogin(email);
        if(dfunc==null)
        {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        else {
            if (!dfunc.getContr().equals(password)) {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                focusView = mPasswordView;
                cancel = true;
            }
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            //Se guardan los datos en cache
            preferenceSettings= getPreferences(0);
            preferenceEditor= preferenceSettings.edit();
            preferenceEditor.putString("usuario",email);
            preferenceEditor.putString("ipBD",controladora.ipBD);
            preferenceEditor.putString("puertoBD",controladora.puertoBD);
            preferenceEditor.putString("usuarioBD",controladora.usuarioBD);
            preferenceEditor.putString("contrBD",controladora.contrBD);
            preferenceEditor.commit();

            Intent i = new Intent(this, MenuPrincipal.class);
            i.putExtra("usuario", email);
            i.putExtra("ipBD",controladora.ipBD);
            i.putExtra("puertoBD",controladora.puertoBD);
            i.putExtra("usuarioBD",controladora.usuarioBD);
            i.putExtra("contrBD",controladora.contrBD);
            startActivity(i);

        }
    }



    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio_sesion, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.ic_tuerca)
        {
            Intent i = new Intent(this, Ajustes.class);
            i.putExtra("ipBD",ipBD);
            i.putExtra("puertoBD",puertoBD);
            i.putExtra("usuarioBD",usuarioBD);
            i.putExtra("contrBD", contrBD);
            startActivityForResult(i, 10);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
      //  if ((requestCode == resultCode) && (resultCode == Activity.RESULT_OK)) {

            //Toast.makeText(getApplicationContext(), data.getExtras().getString("ipBD"), Toast.LENGTH_SHORT).show();
            Bundle bundle = data.getExtras();
            usuarioBD = bundle.getString("usuarioBD");
            contrBD = bundle.getString("contrBD");
            ipBD = bundle.getString("ipBD");
            puertoBD = bundle.getString("puertoBD");

            //controladora= new dControladora(usuarioBD,contrBD,ipBD,puertoBD);
      //  }
    }

}
