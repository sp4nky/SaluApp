package uy.com.salu.www.saluapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import Dominio.dControladora;
import Herramientas.Adapter;


public class Ajustes extends ActionBarActivity {

    private ListView lstAjustes;
    private Bundle bundle;
    private dControladora controladora;
    private Context context= this;
    private String usuarioBD;
    private String contrBD;
    private String ipBD;
    private String puertoBD;
    private String catalogoBD;
    private Button volver;
    Adapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        volver= (Button) this.findViewById(R.id.button_volver);

        bundle= getIntent().getExtras();
        usuarioBD = bundle.getString("usuarioBD");
        contrBD = bundle.getString("contrBD");
        ipBD = bundle.getString("ipBD");
        puertoBD = bundle.getString("puertoBD");


        adaptador= new Adapter(this);
        adaptador.setTools(ipBD,puertoBD,usuarioBD,contrBD);
        lstAjustes = (ListView) this.findViewById(R.id.lstAjustes);

        lstAjustes.setAdapter(adaptador);
        lstAjustes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int posicion, long arg3) {

                // get prompts.xml view
                LayoutInflater layoutInflater = LayoutInflater.from(context);

                View promptView = layoutInflater.inflate(R.layout.prompt_dialog_box, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set prompts.xml to be the layout file of the alertdialog builder
                alertDialogBuilder.setView(promptView);
                final TextView title = (TextView) promptView.findViewById(R.id.textBox);

                final EditText input = (EditText) promptView.findViewById(R.id.editBox);
                switch (posicion) {
                    case 0:
                        title.setText("IP Servidor");
                        input.setText(ipBD);
                        break;
                    case 1:
                        title.setText("Puerto");
                        input.setText(puertoBD);
                        break;
                    case 2:
                        title.setText("Usuario");
                        input.setText(usuarioBD);
                        break;
                    case 3:
                        title.setText("Contrasena");
                        input.setText(contrBD);
                        break;
                }

                // setup a dialog window
                alertDialogBuilder
                        .setCancelable(false)

                        .setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                switch (posicion) {
                                    case 0:
                                        ipBD = input.getText().toString();
                                        ipBD = ipBD;
                                        break;
                                    case 1:
                                        puertoBD = input.getText().toString();
                                        puertoBD = puertoBD;
                                        break;
                                    case 2:
                                        usuarioBD = input.getText().toString();
                                        usuarioBD = usuarioBD;
                                        break;
                                    case 3:
                                        contrBD = input.getText().toString();
                                        contrBD = contrBD;
                                        break;
                                }

                                adaptador.setTools(ipBD,puertoBD,usuarioBD,contrBD);

                                lstAjustes.setAdapter(adaptador);
                            }
                        });

                // create an alert dialog
                AlertDialog alertD = alertDialogBuilder.create();
                alertD.show();
            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = getIntent();
                i.putExtra("ipBD",ipBD);
                i.putExtra("puertoBD",puertoBD);
                i.putExtra("usuarioBD",usuarioBD);
                i.putExtra("contrBD", contrBD);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ajustes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.ic_retroceso)
        {
            Intent i = getIntent();
            i.putExtra("ipBD",ipBD);
            i.putExtra("puertoBD",puertoBD);
            i.putExtra("usuarioBD",usuarioBD);
            i.putExtra("contrBD", contrBD);
            setResult(Activity.RESULT_OK, i);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = getIntent();
        i.putExtra("ipBD",ipBD);
        i.putExtra("puertoBD",puertoBD);
        i.putExtra("usuarioBD",usuarioBD);
        i.putExtra("contrBD", contrBD);
        setResult(Activity.RESULT_OK, i);
        finish();;
    }
}
