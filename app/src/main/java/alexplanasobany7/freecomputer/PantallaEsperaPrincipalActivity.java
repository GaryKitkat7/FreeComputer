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
import android.support.v4.util.ArrayMap;
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
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class PantallaEsperaPrincipalActivity extends AppCompatActivity {

    private static final long TEMPS_ESPERAR = 2000;
    public int i = 0, iii = 0, opcio;
    public static int dia;
    public int tipusPantalla;
    public boolean notis;
    public String HoraActual;
    public String[] sales, aulesOcupades;
    public static String[] Sales = {"008","010","011","012","017","018"};
    private ProgressBar progressBar;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pantalla_espera_principal);

        imageView = (ImageView)findViewById(R.id.imatge_espera);
        Glide.with(this).load(R.drawable.pantprincipal).asBitmap().into(imageView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        Date date = new Date();
        int hora = date.getHours();
        if (hora < 10){
            HoraActual = "0"+String.valueOf(hora);
        }else{
            HoraActual = String.valueOf(hora);
        }
        int minuts = date.getMinutes();
        if(minuts < 10){
            HoraActual = HoraActual + ":0"+String.valueOf(minuts);
        }else {
            HoraActual = HoraActual + ":" +String.valueOf(minuts);
        }
        int segons = date.getSeconds();
        if(segons < 10){
            HoraActual = HoraActual + ":0"+String.valueOf(segons);
        }else{
            HoraActual = HoraActual + ":" +String.valueOf(segons);
        }
        Log.d("HORA ACTUAL", HoraActual);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String dayOfTheWeek = sdf.format(date);

        Log.d("DIA SETAMANA", String.valueOf(dayOfTheWeek));

        if(dayOfTheWeek.equals("lunes") || dayOfTheWeek.equals("Monday") ||
                dayOfTheWeek.equals("Lunes") || dayOfTheWeek.equals("monday")){
            opcio = 1;
            dia = 1;
        }else if(dayOfTheWeek.equals("martes") || dayOfTheWeek.equals("tuesday") ||
                dayOfTheWeek.equals("Martes") || dayOfTheWeek.equals("Tuesday")){
            opcio = 2;
            dia = 2;
        }else if(dayOfTheWeek.equals("miércoles") || dayOfTheWeek.equals("Wednesday") ||
                dayOfTheWeek.equals("Miércoles") || dayOfTheWeek.equals("wednesday")) {
            opcio = 3;
            dia = 3;
        }else if(dayOfTheWeek.equals("jueves") || dayOfTheWeek.equals("Thursday") ||
                dayOfTheWeek.equals("Jueves") || dayOfTheWeek.equals("thursday")) {
            opcio = 4;
            dia = 4;
        }else if(dayOfTheWeek.equals("viernes") || dayOfTheWeek.equals("Friday") ||
                dayOfTheWeek.equals("Viernes") || dayOfTheWeek.equals("friday")){
            opcio = 5;
            dia = 5;
        }else{
            opcio = 1;
            dia = 1;
        }

        new ConsultarDadesHorari().execute("http://95.85.16.142/consultarHorari.php?&id_dia="+dia);


        sales= new String[152];
        aulesOcupades = new String[7];

        for(int z = 0; z < 3; z++){
            String Sala1=Sales[z*2], Sala2 = Sales[(z*2)+1];
            new ConsultarDades().execute("http://95.85.16.142/Consultar2Sales.php?sala1="+Sala1+
                    "&sala2="+Sala2);
        }

        new ConsultarDadesHorariAula().execute("http://95.85.16.142/ConsultarEstatAula.php?hora="
                +HoraActual+"&dia="+opcio);


        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                SharedPreferences sharedPrefs = PreferenceManager
                        .getDefaultSharedPreferences(PantallaEsperaPrincipalActivity.this);

                tipusPantalla = Integer.parseInt(sharedPrefs.getString("PrefTipusPantalla", "1"));
                notis = sharedPrefs.getBoolean("PrefNotificacions",false);

                Log.d("TipusPantalla", String.valueOf(tipusPantalla));
                Log.d("Notis", String.valueOf(notis));


                if(tipusPantalla == 0){
                    //TODO: ULTIMA PANTALLA
                }else if(tipusPantalla == 1 && notis){
                    IniciaServei();
                    Intent intent = new Intent().setClass(
                            PantallaEsperaPrincipalActivity.this,MainActivity.class);
                    intent.putExtra("Sales",sales);
                    intent.putExtra("Dia", opcio);
                    intent.putExtra("AulesOcupades", aulesOcupades);
                    startActivity(intent);
                }else if(tipusPantalla == 2 && notis){
                    IniciaServei();
                    Intent intent = new Intent().setClass(
                            PantallaEsperaPrincipalActivity.this,LlistaClassesActivity.class);
                    intent.putExtra("Sales",sales);
                    intent.putExtra("Dia", opcio);
                    intent.putExtra("AulesOcupades", aulesOcupades);
                    startActivity(intent);
                }else if(tipusPantalla == 1 && !notis){
                    Intent intent = new Intent().setClass(
                            PantallaEsperaPrincipalActivity.this,MainActivity.class);
                    intent.putExtra("Sales",sales);
                    intent.putExtra("Dia", opcio);
                    intent.putExtra("AulesOcupades", aulesOcupades);
                    startActivity(intent);
                }else if (tipusPantalla == 2 && !notis){
                    Intent intent = new Intent().setClass(
                            PantallaEsperaPrincipalActivity.this,LlistaClassesActivity.class);
                    intent.putExtra("Sales", sales);
                    intent.putExtra("Dia", opcio);
                    intent.putExtra("AulesOcupades", aulesOcupades);
                    startActivity(intent);
                }
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask,TEMPS_ESPERAR);
    }

    public void IniciaServei(){
        Intent intent = new Intent(this, ServeiConsultaBBDDActivity.class);
        intent.setAction("alexplanasobany7.freecomputer.action.RUN_INTENT_SERVICE");
        intent.putExtra("Sales",sales);
        intent.putExtra("Hora", HoraActual);
        intent.putExtra("Dia", opcio);
        startService(intent);
    }

    private class ConsultarDadesHorariAula extends AsyncTask<String, Void, String> {
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
                for(int j = 0; j < ja.length(); j++){
                    String substring = ja.getString(j);
                    aulesOcupades[iii] = substring.substring(2,5);
                    iii++;
                }
                Log.d("AulesOcupadesAra", Arrays.toString(aulesOcupades));

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("reposta", "No ENtRAAA");
            }
        }
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
                for(int j = 0; j < ja.length(); j++){
                    String substring = ja.getString(j);
                    sales[i] = substring.substring(2,3);
                    i++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("reposta", "No ENtRAAA");
            }
        }
    }

    private class ConsultarDadesHorari extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                return downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return "URL incorrecta";
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
