package alexplanasobany7.freecomputer;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class InteriorClasses extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private int fila, columna, est;
    private boolean estat;
    private String sala, nomPC, edifici;
    private int [][] M;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interior_classes);

        final GridView gridview = (GridView) findViewById(R.id.grid);
        gridview.setAdapter(new AdaptadorDeOrdenadors(this));
        gridview.setOnItemClickListener(this);

        M = new int[5][6];
        int x = 0;
        sala = getIntent().getExtras().getString("sala");
        for(int files = 0; files < 5; files++){
            for(int col = 0; col < 6; col++){
                M[files][col]=x;
                x++;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("Alex", String.valueOf(sala));

        for(int files = 0; files < 5; files++){
            for(int col = 0; col < 6; col++){
                if (M[files][col]==position){
                    Log.d("Plana", String.valueOf(M[files][col]));
                    new ConsultarDades().execute("http://192.168.1.38/FreeComputer/consultarPC.php?fila="
                            +(files+1)+"&columna="+(col+1)+"&sala="+sala);
                }
            }
        }

        Intent intent = new Intent(this, ReservarPC.class);
        intent.putExtra("pos", position);
        intent.putExtra("sala", sala);
        intent.putExtra("nomPC", nomPC);
        intent.putExtra("fila",fila);
        intent.putExtra("columna",columna);
        intent.putExtra("estat",estat);
        startActivity(intent);

        
    }

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

        @Override
        protected void onPostExecute(String result) {
            JSONArray ja = null;
            Log.i("tagconvertstr", "["+result+"]");
            try {
                ja = new JSONArray(result);
                Log.d("reposta", "La resposta es: " + ja);
                edifici = ja.getString(0);
                sala = ja.getString(1);
                fila = ja.getInt(2);
                columna = ja.getInt(3);
                nomPC = ja.getString(4);
                est = ja.getInt(5);
                if(est == 1){
                    estat = true;
                }else estat = false;
                Log.d("estat", String.valueOf(estat));
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


