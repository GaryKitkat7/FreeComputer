package alexplanasobany7.freecomputer;

/**
 * Created by alexplanasobany on 22/2/17.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class AdaptadorDeOrdenadors extends BaseAdapter {
    private Context context;
    private String[] estatSales;
    private String sala;
    private Ordenadors ordenadors;


    public AdaptadorDeOrdenadors(Context context, String Sala, String[] EstatSales) {
        super();
        this.context = context;
        this.sala = Sala;
        this.estatSales = EstatSales;
        ordenadors = new Ordenadors(sala, estatSales);
        Log.d("SalaAdapter", sala);
    }

    public int getCount() {
        return ordenadors.getLongitud();
    }

    @Override
    //Cambiar el tipus a Ordenador
    public Ordenadors getItem(int position) {
        return ordenadors.getPosicio(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    // Crea una nova imatge en cada posició del GridView
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item, viewGroup, false);
        }

        ImageView imagenCoche = (ImageView) view.findViewById(R.id.imatgePC); //Imatge Variable a cada posicio
        TextView nombreCoche = (TextView) view.findViewById(R.id.nomPC); //Nom Variable a cada posicio

        Ordenadors item = getItem(position);
        Log.d("Pos",String.valueOf(position));
        imagenCoche.setImageResource(item.getIdDrawable());
        nombreCoche.setText(item.getNombre());

        return view;
    }
}
