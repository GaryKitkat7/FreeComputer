package alexplanasobany7.freecomputer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alexplanasobany on 3/6/17.
 */

public class AdaptadorLlistaHoraris extends BaseAdapter{

    private ArrayList<ItemHoraris> itemHorarisArrayList;
    private Context context;

    public AdaptadorLlistaHoraris(Context context, ArrayList<ItemHoraris> itemHorarisArrayList) {
        this.context = context;
        this.itemHorarisArrayList = itemHorarisArrayList;
    }

    @Override
    public int getCount() {
        return itemHorarisArrayList.size();
    }

    @Override
    public ItemHoraris getItem(int position) {
        return itemHorarisArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.llista_horaris_item, parent, false);
        }

        TextView Assignatures = (TextView) view.findViewById(R.id.Assignatura);
        TextView Hora_inici = (TextView) view.findViewById(R.id.Hora_inici);
        TextView Hora_final = (TextView) view.findViewById(R.id.Hora_final);

        ItemHoraris itemHoraris = getItem(position);
        Assignatures.setText(itemHoraris.getAssignatura());
        Hora_inici.setText(itemHoraris.getData_Inici());
        Hora_final.setText(itemHoraris.getData_final());

        return view;
    }
}
