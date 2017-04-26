package alexplanasobany7.freecomputer;

/**
 * Created by alexplanasobany on 22/2/17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class AdaptadorDeOrdenadors extends BaseAdapter {
    private Context context;

    public AdaptadorDeOrdenadors(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Ordenadors.ITEMS.length;
    }

    @Override
    public Ordenadors getItem(int position) {
        return Ordenadors.ITEMS[position];
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId(); //Retorna l'identificador de l'item que hi ha en la posició rebuda per parametre
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

        final Ordenadors item = getItem(position);
        imagenCoche.setImageResource(item.getIdDrawable());
        nombreCoche.setText(item.getNombre());

        return view;
    }
}
