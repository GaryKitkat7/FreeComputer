package alexplanasobany7.freecomputer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

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

    private static final long TEMPS_ESPERAR = 1000;
    public int i = 0;
    public int tipusPantalla, NOTIS;
    public boolean notis;
//    public int KEY;
    public String[] sales;
    public static String[] Sales = {"008","010","011","012","017","018"};
    private int fila5 = 5, columna6 = 6, columna5 = 5, fila4 = 4;
    private String Sala008 = "008", Sala010 = "010", Sala011 = "011", Sala012 = "012";
    private String Sala017 = "017", Sala018 = "018";
    private ProgressBar progressBar;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pantalla_espera_principal);
//        KEY = getIntent().getExtras().getInt("KEY");

        imageView = (ImageView)findViewById(R.id.imatge_espera);
        Glide.with(this).load(R.drawable.pantprincipal).asBitmap().into(imageView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);


        sales= new String[152];

        for(int z = 0; z < 3; z++){
            String Sala1=Sales[z*2], Sala2 = Sales[(z*2)+1];
            new ConsultarDades().execute("http://95.85.16.142/Consultar2Sales.php?sala1="+Sala1+
                    "&sala2="+Sala2);
        }


        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                SharedPreferences sharedPrefs = PreferenceManager
                        .getDefaultSharedPreferences(PantallaEsperaPrincipalActivity.this);

                StringBuilder builder = new StringBuilder();

                tipusPantalla = Integer.parseInt(String.valueOf(builder.append(
                        sharedPrefs.getString("PrefTipusPantalla", "NULL"))));
                //notis = Boolean.parseBoolean(String.valueOf(builder.append(sharedPrefs.getString("PrefTipusPantalla", "NULL"))));

                builder.append("\n Pantalla: " +
                        sharedPrefs.getString("PrefTipusPantalla", "NULL"));

                builder.append("\n Notificacions: " +
                        sharedPrefs.getBoolean("PrefNotificacions", false));

                Log.d("gknrkgnre", String.valueOf(tipusPantalla));


                if(tipusPantalla == 0){

                }else if(tipusPantalla == 1){
                    Intent intent = new Intent().setClass(
                            PantallaEsperaPrincipalActivity.this,MainActivity.class);
                    intent.putExtra("Sales",sales);
                    startActivity(intent);
                }else if(tipusPantalla == 2){
                    Intent intent = new Intent().setClass(
                            PantallaEsperaPrincipalActivity.this,LlistaClassesActivity.class);
                    intent.putExtra("Sales",sales);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent().setClass(
                            PantallaEsperaPrincipalActivity.this,MainActivity.class);
                    intent.putExtra("Sales",sales);
                    startActivity(intent);
                }


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
