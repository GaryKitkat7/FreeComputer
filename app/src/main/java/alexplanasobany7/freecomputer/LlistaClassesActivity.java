package alexplanasobany7.freecomputer;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.security.AccessController.getContext;

public class LlistaClassesActivity extends AppCompatActivity{

    private ListView LlistaAULAPCs;
    public String[] sales;
    public int i = 0;
    ProgressDialog progressDialog;
    public String[] Sales = PantallaEsperaPrincipalActivity.Sales;
    private ArrayList<ItemLlistaClases> arrayList;
    private AdaptadorDeLaLlistaPCs adaptadorDeLaLlistaPCs;
    public String[] OrdLLiures;
    private String Sala;
    public int KEY_LLISTA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llista_classes);
        OrdLLiures = getIntent().getExtras().getStringArray("Sales");
        Log.d("Ordinadors Lliures", Arrays.toString(OrdLLiures));

        LlistaAULAPCs = (ListView) findViewById(R.id.Llista);
        arrayList = new ArrayList<>();

        IntentFilter filter = new IntentFilter();
        filter.addAction(ServeiConsultaBBDDActivity.ACTION_RUN_ISERVICE);

        ResponseReceiver receiver = new ResponseReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,filter);

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
                intent.putExtra("Sales", OrdLLiures);
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
        int i = 0, total;
        if (sala.equals("008")) {
            total = 30;
            for (int j = 0; j < 30; j++) {
                if (vector[j].equals("1")) {
                    i++;
                }
            }
            ResultatFinal = String.valueOf(total - i) + "/30";
        } else if (sala.equals("010")) {
            total = 30;
            for (int j = 0; j < 30; j++) {
                if (vector[j + 30].equals("1")) {
                    i++;
                }
            }
            ResultatFinal = String.valueOf(total-i) + "/30";
        } else if (sala.equals("011")) {
            total = 25;
            for (int j = 0; j < 25; j++) {
                if (vector[j + 60].equals("1")) {
                    i++;
                }
            }
            ResultatFinal = String.valueOf(total - i) + "/25";
        } else if (sala.equals("012")) {
            total = 20;
            for (int j = 0; j < 20; j++) {
                if (vector[j + 85].equals("1")) {
                    i++;
                }
            }
            ResultatFinal = String.valueOf(total-i) + "/20";
        } else if (sala.equals("017")) {
            total = 20;
            for (int j = 0; j < 20; j++) {
                if (vector[j + 105].equals("1")) {
                    i++;
                }
            }
            ResultatFinal = String.valueOf(total-i) + "/20";
        } else if (sala.equals("018")) {
            total = 20;
            for (int j = 0; j < 20; j++) {
                if (vector[j + 125].equals("1")) {
                    i++;
                }
            }
            ResultatFinal = String.valueOf(total-i) + "/20";
        }
        return ResultatFinal;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opcions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();

        if (id == R.id.Configuracio){
            Intent intent = new Intent(this, UserSettingsActivity.class);
            startActivity(intent);
        }else if(id == R.id.Refresca){
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Actualitzant...");
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();

            sales= new String[152];

            for(int z = 0; z < 3; z++){
                String Sala1=Sales[z*2], Sala2 = Sales[(z*2)+1];
                i = 0;
                new ConsultarDades().execute("http://95.85.16.142/Consultar2Sales.php?sala1="+Sala1+
                        "&sala2="+Sala2);
            }

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            };

            Timer timer = new Timer();
            timer.schedule(timerTask,1500);
        }else if(id == R.id.Mapa){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Sales", OrdLLiures);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(menuItem);

    }

    private class ResponseReceiver extends BroadcastReceiver {

        private ResponseReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            OrdLLiures = new String[152];
            OrdLLiures = intent.getExtras().getStringArray("Sales");
            Log.d("ALEXPLANASOBANY", Arrays.toString(sales));
            CargarLlista();
        }
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Mapa) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Sales", OrdLLiures);
            startActivity(intent);
            return true;
        } else if(id == R.id.Llista){
            Intent intent = new Intent(this, LlistaClassesActivity.class);
            intent.putExtra("Sales", OrdLLiures);
            startActivity(intent);
        }else{
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Actualitzant...");
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();

            sales= new String[151];

            for(int z = 0; z < 3; z++){
                String Sala1=Sales[z*2], Sala2 = Sales[(z*2)+1];
                new ConsultarDades().execute("http://95.85.16.142/Consultar2Sales.php?sala1="+Sala1+
                        "&sala2="+Sala2);
            }

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), LlistaClassesActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Sales", sales);
                    startActivity(intent);
                }
            };

            Timer timer = new Timer();
            timer.schedule(timerTask,3000);
        }

        return super.onOptionsItemSelected(item);
    }*/

    private class ConsultarDades extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                return downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return "URL incorrecta";
            }
        }

        protected void onPostExecute(String result) {
            JSONArray ja;
            try {
                ja = new JSONArray(result);
                Log.d("JASONNNN", String.valueOf(ja.length()));
                for(int j = 0; j < ja.length(); j++){
                    String substring = ja.getString(j);
                    sales[i] = substring.substring(2,3);
                    i++;
                }

                Log.d("ALEXPLANA1", Arrays.toString(sales));

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("reposta", "No ENtRAAA");
            }
        }
    }

    private String downloadUrl(String myurl) throws IOException {
        myurl = myurl.replace(" ", "%20");
        InputStream stream = null;
        int len = 500;
        try {
            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            // Open communications link (network traffic occurs here).
            connection.connect();
            int responseCode = connection.getResponseCode();
            Log.d("reposta", "La resposta es: " + responseCode);
            // Retrieve the response body as an InputStream.
            stream = connection.getInputStream();

            //Convertir el InputString a String
            String ContentAsString = readIt(stream, len);
            return ContentAsString;

        } finally {
            // Close Stream and disconnect HTTPS connection.
            if (stream != null) {
                stream.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

}
