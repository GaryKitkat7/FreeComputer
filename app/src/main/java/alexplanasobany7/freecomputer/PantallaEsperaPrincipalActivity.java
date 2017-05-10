package alexplanasobany7.freecomputer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

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
import java.util.Timer;
import java.util.TimerTask;

public class PantallaEsperaPrincipalActivity extends AppCompatActivity {

    private static final long TEMPS_ESPERAR = 9000;
    public int i = 0;
    public static String[] sales;
    private int fila5 = 5, columna6 = 6, columna5 = 5, fila4 = 4;
    private String Sala008 = "008", Sala010 = "010", Sala011 = "011", Sala012 = "012";
    private String Sala017 = "017", Sala018 = "018";
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pantalla_espera_principal);
        /*progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);*/

        sales= new String[160];

        for (int f = 0; f < fila5; f++){
            for (int c = 0; c < columna6; c++){
                new ConsultarDades().execute("http://95.85.16.142/consultarPC.php?fila="
                        +(f+1)+"&columna="+(c+1)+"&sala="+Sala008);
            }
        }

        for (int f = 0; f < fila5; f++){
            for (int c = 0; c < columna6; c++){
                new ConsultarDades().execute("http://95.85.16.142/consultarPC.php?fila="
                        +(f+1)+"&columna="+(c+1)+"&sala="+Sala010);
            }
        }

        for (int f = 0; f < fila5; f++){
            for (int c = 0; c < columna5; c++){
                new ConsultarDades().execute("http://95.85.16.142/consultarPC.php?fila="
                        +(f+1)+"&columna="+(c+1)+"&sala="+Sala011);
            }
        }

        for (int f = 0; f < fila4; f++){
            for (int c = 0; c < columna5; c++){
                new ConsultarDades().execute("http://95.85.16.142/consultarPC.php?fila="
                        +(f+1)+"&columna="+(c+1)+"&sala="+Sala012);
            }
        }

        for (int f = 0; f < fila4; f++){
            for (int c = 0; c < columna5; c++){
                new ConsultarDades().execute("http://95.85.16.142/consultarPC.php?fila="
                        +(f+1)+"&columna="+(c+1)+"&sala="+Sala017);
            }
        }

        for (int f = 0; f < fila4; f++){
            for (int c = 0; c < columna5; c++){
                new ConsultarDades().execute("http://95.85.16.142/consultarPC.php?fila="
                        +(f+1)+"&columna="+(c+1)+"&sala="+Sala018);
            }
        }



        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                Intent intent = new Intent().setClass(
                        PantallaEsperaPrincipalActivity.this,MainActivity.class);
                startActivity(intent);

                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask,TEMPS_ESPERAR);
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
