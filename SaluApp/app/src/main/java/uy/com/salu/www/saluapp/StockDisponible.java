package uy.com.salu.www.saluapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Dominio.dControladora;
import Dominio.dStock;


public class StockDisponible extends ActionBarActivity {

    private Bundle bundle;
    private String correo;
    private TableLayout tablaStock;
    private dStock stock;
    private String usuarioBD;
    private String contrBD;
    private String ipBD;
    private String puertoBD;
    private dControladora controladora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_disponible);

        bundle = getIntent().getExtras();
        correo= bundle.getString("usuario");
        usuarioBD = bundle.getString("usuarioBD");
        contrBD = bundle.getString("contrBD");
        ipBD = bundle.getString("ipBD");
        puertoBD = bundle.getString("puertoBD");

        controladora= new dControladora(usuarioBD,contrBD,ipBD,puertoBD);



        tablaStock= (TableLayout) this.findViewById(R.id.StockDisponible);


        TableRow tr_head = new TableRow(StockDisponible.this);
        //tr_head.setId(13);
        tr_head.setBackgroundColor(Color.GRAY);
        tr_head.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
/*
        TextView label_fecha = new TextView(StockDisponible.this);
        // label_fecha.setId(25);
        label_fecha.setText("Nombre");
        label_fecha.setTextColor(Color.WHITE);
        label_fecha.setPadding(5, 5, 5, 5);
        tr_head.addView(label_fecha);

        TextView label_cantidad = new TextView(StockDisponible.this);
        // label_cantidad.setId(26);
        label_cantidad.setText("Cant.");
        label_cantidad.setTextColor(Color.WHITE);
        label_cantidad.setPadding(5, 5, 5, 5);
        tr_head.addView(label_cantidad);
*/
        TextView label_marca = new TextView(StockDisponible.this);
        // label_nota.setId(26);
        label_marca.setText("Marca");
        label_marca.setTextColor(Color.WHITE);
        label_marca.setBackgroundColor(Color.GRAY);
        label_marca.setPadding(5, 5, 5, 5);
        tr_head.addView(label_marca);

        TextView label_modelo = new TextView(StockDisponible.this);
        // label_nota.setId(26);
        label_modelo.setText("Modelo");
        label_modelo.setTextColor(Color.WHITE);
        label_modelo.setPadding(5, 5, 5, 5);
        label_modelo.setBackgroundColor(Color.GRAY);
        tr_head.addView(label_modelo);

        TextView label_tipo = new TextView(StockDisponible.this);
        // label_nota.setId(26);
        label_tipo.setText("Nro Serie");
        label_tipo.setTextColor(Color.WHITE);
        label_tipo.setPadding(5, 5, 5, 5);
        label_tipo.setBackgroundColor(Color.GRAY);
        tr_head.addView(label_tipo);

        tablaStock.addView(tr_head, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        if (controladora.getMysql().getConexionMySQL()!= null) {
            ArrayList<dStock> listaStock = controladora.listarStock();
            for (int i = 0; i < listaStock.size(); i++) {


                stock = listaStock.get(i);

                TableRow tr = new TableRow(StockDisponible.this);
                if (i % 2 != 0)
                    tr.setBackgroundColor(Color.GRAY);
                else
                    tr.setBackgroundColor(Color.rgb(189, 202, 93));
                // tr.setId(100+i);
                tr.setLayoutParams(new LayoutParams(
                        LayoutParams.FILL_PARENT,
                        LayoutParams.WRAP_CONTENT));

/*
                TextView labelnombre = new TextView(StockDisponible.this);
                //labelnombre.setId(200+i);
                labelnombre.setText(stock.getNombre());
                labelnombre.setPadding(5, 0, 5, 0);
                labelnombre.setTextColor(Color.WHITE);
                tr.addView(labelnombre);

                TextView labelcantidad = new TextView(StockDisponible.this);
                // labelcantidad.setId(300+i);
                labelcantidad.setText(String.valueOf(stock.getCantidad()));
                labelcantidad.setTextColor(Color.WHITE);
                tr.addView(labelcantidad);
*/
                TextView labelmarca = new TextView(StockDisponible.this);
                // labelmarca.setId(300+i);
                labelmarca.setText(String.valueOf(stock.getModelo().getMarca().getNombre()));
                labelmarca.setTextColor(Color.WHITE);
                if (i % 2 != 0)
                    labelmarca.setBackgroundColor(Color.GRAY);
                else
                    labelmarca.setBackgroundColor(Color.rgb(189, 202, 93));
                tr.addView(labelmarca);

                TextView labelmodelo = new TextView(StockDisponible.this);
                // labelmodelo.setId(300+i);
                labelmodelo.setText(String.valueOf(stock.getModelo().getNombre()));
                labelmodelo.setTextColor(Color.WHITE);
                if (i % 2 != 0)
                    labelmodelo.setBackgroundColor(Color.GRAY);
                else
                    labelmodelo.setBackgroundColor(Color.rgb(189, 202, 93));
                tr.addView(labelmodelo);

                TextView labeltipo = new TextView(StockDisponible.this);
                // labeltipo.setId(300+i);
                labeltipo.setText(String.valueOf(stock.getNroSerie()));
                labeltipo.setTextColor(Color.WHITE);
                if (i % 2 != 0)
                    labeltipo.setBackgroundColor(Color.GRAY);
                else
                    labeltipo.setBackgroundColor(Color.rgb(189, 202, 93));
                tr.addView(labeltipo);


                tablaStock.addView(tr, new TableLayout.LayoutParams(
                        LayoutParams.FILL_PARENT,
                        LayoutParams.WRAP_CONTENT));

            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No se a podido conecar con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stock_disponible, menu);
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
                Intent i = new Intent(StockDisponible.this, clase);
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

        if (id == R.id.ic_retroceso)
        {
            finish();

        }

        return super.onOptionsItemSelected(item);
    }
}
