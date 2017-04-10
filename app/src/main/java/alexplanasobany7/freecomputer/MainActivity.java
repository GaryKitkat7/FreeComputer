package alexplanasobany7.freecomputer;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private String Sala008 = "008", Sala010 = "010";
    private TextView texte;
    private String estat;
    public int i = 0;
    public static String[] sales;
    private int fila008 = 5, columna008 = 6;
    public static String sala = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapaPrincipal mapaPrincipal = new MapaPrincipal(this);
        ScrollView scrollView = new ScrollView(this);
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);

        horizontalScrollView.addView(scrollView);
        scrollView.addView(mapaPrincipal);
        setContentView(horizontalScrollView);

        sales= new String[300];
        /*Sala008 = (Button)findViewById(R.id.Classe008);
        Sala010 = (Button) findViewById(R.id.Classe010);*/

        for (int f = 0; f < fila008; f++){
            for (int c = 0; c < columna008; c++){
                new ConsultarDades().execute("http://95.85.16.142/consultarPC.php?fila="
                        +(f+1)+"&columna="+(c+1)+"&sala="+Sala008);
            }
        }

        for (int f = 0; f < fila008; f++){
            for (int c = 0; c < columna008; c++){
                new ConsultarDades().execute("http://95.85.16.142/consultarPC.php?fila="
                        +(f+1)+"&columna="+(c+1)+"&sala="+Sala010);
            }
        }


    }

    /*public void EntraClasse (View view){
        Button b = (Button)view;
        if(b.getText().equals("008")){
            sala = Sala008.getText().toString();
        }else if(b.getText().equals("010")){
            sala = Sala010.getText().toString();
        }

        Intent intent = new Intent(this, InteriorClassesActivity.class);
        intent.putExtra("sala", sala);
        startActivity(intent);
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
                sales[i] = ja.getString(5);
                i++;
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


