package alexplanasobany7.freecomputer;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by alexplanasobany on 25/5/17.
 */

public class ServeiConsultaBBDDActivity extends IntentService {
    public static final String ACTION_RUN_ISERVICE = "alexplanasobany7.freecomputer.action.RUN_INTENT_SERVICE";
    public int i, iii = 0, dia = PantallaEsperaPrincipalActivity.dia, tipusNoti;
    public boolean Vibracio;
    public String HoraActual, UltimMissatge = "";
    public String[] sales, aulesOcupades;
    public ArrayList<String> AulesLliures;
    public String[] Sales = PantallaEsperaPrincipalActivity.Sales;

    public ServeiConsultaBBDDActivity() {
        super("ServeiConsultaBBDDActivity");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_RUN_ISERVICE.equals(action)) {
                handleActionRun();
            }
        }
    }

    private void handleActionRun() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
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


        try {
            // Bucle de simulació
            for (int v = 1; v <= 1000; v++) {
                SharedPreferences sharedPrefs = PreferenceManager
                        .getDefaultSharedPreferences(ServeiConsultaBBDDActivity.this);

                tipusNoti = Integer.parseInt(sharedPrefs.getString("AvisNotis", "1"));
                Vibracio = sharedPrefs.getBoolean("Vibracio",false);

                aulesOcupades = new String[7];
                sales= new String[304];
                for(int z = 0; z < 3; z++){
                    i = 0;
                    String Sala1=Sales[z*2], Sala2 = Sales[(z*2)+1];
                    new ConsultarDades().execute("http://95.85.16.142/Consultar2Sales.php?sala1="+Sala1+
                            "&sala2="+Sala2);
                }

                new ConsultarDadesHorariAula().execute("http://95.85.16.142/ConsultarEstatAula.php?hora="
                        +HoraActual+"&dia="+dia);

                Thread.sleep(2000);

                Intent localIntent = new Intent(ACTION_RUN_ISERVICE)
                        .putExtra("Sales", sales);
                localIntent.putExtra("AulesOcupades", aulesOcupades);
                LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);


                AulesLliures = new ArrayList<>();
                AulesLliures = CalcularAulesPlenes(sales,Sales, aulesOcupades);

                String MissatgeFinal = new String();
                if(AulesLliures == null){
                    MissatgeFinal = "No en hi ha cap de lliure";
                }else{
                    for(int p = 0; p < AulesLliures.size(); p++){
                        if(p!=0){
                            MissatgeFinal = MissatgeFinal + "," + AulesLliures.get(p);
                        }else {
                            MissatgeFinal = AulesLliures.get(p);
                        }
                    }
                }

                if(Vibracio && tipusNoti == 2 && UltimMissatge.equals("No en hi ha cap de lliure")){
                    vibrator.vibrate(2000);
                }else if (Vibracio && tipusNoti == 1 && !UltimMissatge.equals(MissatgeFinal)){
                    vibrator.vibrate(2000);
                }else if(Vibracio && tipusNoti == 0){
                    vibrator.vibrate(2000);
                }

                // Crear Notificació
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_stat_freecomp)
                        .setContentTitle("Les aules disponibles son: ")
                        .setContentText(MissatgeFinal);

                Intent targetIntent = new Intent(getApplicationContext(),
                        PantallaEsperaPrincipalActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(
                        getApplicationContext(), 0, targetIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(contentIntent);

                builder.setAutoCancel(false);
                startForeground(1,builder.build());
                UltimMissatge = MissatgeFinal;
                Thread.sleep(300000);
            }

            stopForeground(false);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> CalcularAulesPlenes(String[] vector, String[] vectorSales, String[] AulesOcupades){
        ArrayList<String> ResultatFinal = new ArrayList<>();
        int total, p =0;
        List<String> aulesOcup = new ArrayList<>();
        for(int k = 0; AulesOcupades[k] != null; k++){
            aulesOcup.add(p,AulesOcupades[k]);
        }
        for(int x = 0; x < vectorSales.length; x++){
            int i = 0;
            if(vectorSales[x].equals("008")){
                total = 30;
                for(int j = 0; j < 30; j++){
                    if(vector[j].equals("1")){
                        i++;
                    }
                }
                if(i < total && !aulesOcup.contains("008")){
                    ResultatFinal.add(" 008");
                }
            }else if(vectorSales[x].equals("010")){
                total = 30;
                for(int j = 0; j < 30; j++){
                    if(vector[j+30].equals("1")){
                        i++;
                    }
                }
                if(i < total && !aulesOcup.contains("010")){
                    ResultatFinal.add(" 010");
                }
            }else if(vectorSales[x].equals("011")){
                total = 25;
                for(int j = 0; j < 25; j++){
                    if(vector[j+60].equals("1")){
                        i++;
                    }
                }
                if(i < total && !aulesOcup.contains("011")){
                    ResultatFinal.add(" 011");
                }
            }else if (vectorSales[x].equals("012")){
                total = 20;
                for(int j = 0; j < 20; j++){
                    if(vector[j+85].equals("1")){
                        i++;
                    }
                }
                if(i < total && !aulesOcup.contains("012")){
                    ResultatFinal.add(" 012");
                }
            }else if (vectorSales[x].equals("017")){
                total = 20;
                for(int j = 0; j < 20; j++){
                    if(vector[j+105].equals("1")){
                        i++;
                    }
                }
                if(i < total && !aulesOcup.contains("017")){
                    ResultatFinal.add(" 017");
                }
            }else if (vectorSales[x].equals("018")){
                total = 20;
                for(int j = 0; j < 20; j++){
                    if(vector[j+125].equals("1")){
                        i++;
                    }
                }
                if(i < total && !aulesOcup.contains("018")){
                    ResultatFinal.add(" 018");
                }
            }
        }
        return ResultatFinal;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
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
            aulesOcupades = new String[7];
            iii = 0;
            try {
                ja = new JSONArray(result);
                Log.d("RESULTATTTJSON",String.valueOf(ja));
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
