package alexplanasobany7.freecomputer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class LlistaClassesActivity extends AppCompatActivity{

    private ListView LlistaAULAPCs;
    private ArrayList<ItemLlistaClases> arrayList;
    private AdaptadorDeLaLlistaPCs adaptadorDeLaLlistaPCs;
    public String[] OrdLLiures = PantallaEsperaPrincipalActivity.sales;
    private String Sala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llista_classes);

        LlistaAULAPCs = (ListView) findViewById(R.id.Llista);
        arrayList = new ArrayList<>();

        CargarLlista();

        LlistaAULAPCs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    Sala = "008";
                }else if(position == 1){
                    Sala = "010";
                }else if(position == 2){
                    Sala = "011";
                }else if(position==3){
                    Sala = "012";
                }else if(position == 4){
                    Sala = "017";
                }else{
                    Sala = "018";
                }

                Intent intent = new Intent(getApplicationContext(), InteriorClassesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("sala", Sala);
                getApplicationContext().startActivity(intent);
            }
        });

    }

    private void CargarLlista() {
        arrayList.add(new ItemLlistaClases("Aula 008", CalcularNumPCs(OrdLLiures, "008")));
        arrayList.add(new ItemLlistaClases("Aula 010", CalcularNumPCs(OrdLLiures, "010")));
        arrayList.add(new ItemLlistaClases("Aula 011", CalcularNumPCs(OrdLLiures, "011")));
        arrayList.add(new ItemLlistaClases("Aula 012", CalcularNumPCs(OrdLLiures, "012")));
        arrayList.add(new ItemLlistaClases("Aula 017", CalcularNumPCs(OrdLLiures, "017")));
        arrayList.add(new ItemLlistaClases("Aula 018", CalcularNumPCs(OrdLLiures, "018")));

        adaptadorDeLaLlistaPCs = new AdaptadorDeLaLlistaPCs(this, arrayList);
        LlistaAULAPCs.setAdapter(adaptadorDeLaLlistaPCs);
    }

    public String CalcularNumPCs(String[] vector, String sala) {
        String ResultatFinal = "";
        int i = 0;
        if (sala.equals("008")) {
            for (int j = 0; j < 30; j++) {
                if (vector[j].equals("1")) {
                    i++;
                }
            }
            ResultatFinal = String.valueOf(i) + "/30";
        } else if (sala.equals("010")) {
            for (int j = 0; j < 30; j++) {
                if (vector[j + 30].equals("1")) {
                    i++;
                }
            }
            ResultatFinal = String.valueOf(i) + "/30";
        } else if (sala.equals("011")) {
            for (int j = 0; j < 25; j++) {
                if (vector[j + 60].equals("1")) {
                    i++;
                }
            }
            ResultatFinal = String.valueOf(i) + "/25";
        } else if (sala.equals("012")) {
            for (int j = 0; j < 20; j++) {
                if (vector[j + 85].equals("1")) {
                    i++;
                }
            }
            ResultatFinal = String.valueOf(i) + "/20";
        } else if (sala.equals("017")) {
            for (int j = 0; j < 20; j++) {
                if (vector[j + 105].equals("1")) {
                    i++;
                }
            }
            ResultatFinal = String.valueOf(i) + "/20";
        } else if (sala.equals("018")) {
            for (int j = 0; j < 20; j++) {
                if (vector[j + 125].equals("1")) {
                    i++;
                }
            }
            ResultatFinal = String.valueOf(i) + "/20";
        }
        return ResultatFinal;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opcions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Mapa) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        } else {
            Intent intent = new Intent(this, LlistaClassesActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
