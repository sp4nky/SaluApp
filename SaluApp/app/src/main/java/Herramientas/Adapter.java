package Herramientas;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import uy.com.salu.www.saluapp.R;

public class Adapter extends ArrayAdapter<Object> {

    private static Tool[] datos = new Tool[]{
            new Tool("IP Servidor",
                    "la ip",
                    R.drawable.tuerca),
            new Tool("Puerto",
                    "puerto madero",
                    R.drawable.tuerca),
            new Tool("Usuario",
                    "yop",
                    R.drawable.tuerca),
            new Tool("Contrasena",
                    "la pascuare",
                    R.drawable.tuerca),
    };

    Activity context;


    public Adapter(Activity context) {
        super(context, R.layout.listitem, datos);
        this.context= context;
    }

    public void setTools(String ip,String puerto, String usuario, String contrasena){
        datos[0].setSubtitulo(ip);
        datos[1].setSubtitulo(puerto);
        datos[2].setSubtitulo(usuario);
        datos[3].setSubtitulo(contrasena);
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
