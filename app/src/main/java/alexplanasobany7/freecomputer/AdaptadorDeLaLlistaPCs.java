package alexplanasobany7.freecomputer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alexplanasobany on 11/5/17.
 */

public class AdaptadorDeLaLlistaPCs extends BaseAdapter {

    private ArrayList<ItemLlistaClases> itemLlistaClasesArrayList;
    private Context context;

    public AdaptadorDeLaLlistaPCs(Context context, ArrayList<ItemLlistaClases> itemLlistaClasesArrayList) {
        this.context = context;
        this.itemLlistaClasesArrayList = itemLlistaClasesArrayList;
    }

    @Override
    public int getCount() {
        return itemLlistaClasesArrayList.size();
    }

    @Override
    public ItemLlistaClases getItem(int position) {
        return itemLlistaClasesArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, viewGroup, false);
        }

        TextView Aula = (TextView) view.findViewById(R.id.Classe);
        TextView NumPCs = (TextView) view.findViewById(R.id.Ocupats);

        ItemLlistaClases itemLlistaClases = getItem(position);
        Aula.setText(itemLlistaClases.getAula());
        NumPCs.setText(itemLlistaClases.getNumPCs());
        int posicio = itemLlistaClases.getNumPCs().indexOf("/");
        int NPCs = Integer.parseInt(itemLlistaClases.getNumPCs().substring(0,posicio));
        if(NPCs > 5){
            Aula.setBackgroundColor(Color.rgb(30,217,137));
            NumPCs.setBackgroundColor(Color.rgb(30,217,137));
        }else if(NPCs <= 5 && NPCs >= 2){
            Aula.setBackgroundColor(Color.rgb(237,211,67));
            NumPCs.setBackgroundColor(Color.rgb(237,211,67));
        }else{
            Aula.setBackgroundColor(Color.rgb(235,96,96));
            NumPCs.setBackgroundColor(Color.rgb(235,96,96));
        }


        return view;
    }
}
