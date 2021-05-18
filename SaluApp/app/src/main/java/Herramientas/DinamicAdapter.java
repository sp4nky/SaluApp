package Herramientas;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import uy.com.salu.www.saluapp.R;

public class DinamicAdapter extends ArrayAdapter<Object> {

    private int largo;

    private static Tool[] datos;

    Activity context;


    public DinamicAdapter(Activity context, Tool[] datos) {
        super(context, R.layout.listitem, datos);
        this.context= context;
    }

    public void setTools(Tool[] datos){
       this.datos=datos;
    }

    public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.listitem, null);

            TextView lblTitulo= (TextView) item.findViewById(R.id.LblTitulo);
            lblTitulo.setText(datos[position].getTitulo());

            TextView lblSubtitulo = (TextView) item.findViewById(R.id.LblSubTitulo);
            lblSubtitulo.setText(datos[position].getSubtitulo());

            ImageView lblImagen = (ImageView) item.findViewById(R.id.LblImagen);
            lblImagen.setImageResource(datos[position].getImagen());

            return item;


    }
}

