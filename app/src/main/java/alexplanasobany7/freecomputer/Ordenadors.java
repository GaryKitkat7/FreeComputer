package alexplanasobany7.freecomputer;

import android.os.AsyncTask;
import android.util.Log;

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
import java.util.ArrayList;

/**
 * Created by alexplanasobany on 22/2/17.
 */

public class Ordenadors{
    private String nombre;
    private int idDrawable;
    private boolean Reserva;

    public Ordenadors(String nombre, int idDrawable, boolean Reserva) {
        this.nombre = nombre;
        this.idDrawable = idDrawable;
        this.Reserva = Reserva;
    }

    public String getNombre() {
        return nombre; //Retorna el nom del PC
    }

    public int getIdDrawable() {
        return idDrawable; //Retorna el identificador de la imatge que hi ha en la carpeta drawable
    }
    public boolean getReserva(){
        return Reserva;
    }

    public int getId() {
        return nombre.hashCode();
    }

    // TODO: Recorda cambiar les imatges
    public void PosarFotoReserva(int posicio){
        Ordenadors.ITEMS[posicio] = new Ordenadors("Lliure", R.drawable.pc, true);
    }

    public void PosarFotoNoReserva(int posicio){
        Ordenadors.ITEMS[posicio] = new Ordenadors("Reservat", R.drawable.no_pc, false);
    }

    public static String Sala = MainActivity.sala;

    public static int N, files, columnes;

    static {
        if (Sala.equals("008") || Sala.equals("010")) {
            N = 30;
            files = 5;
            columnes = 6;
        } else if (Sala.equals("011")) {
            N = 25;
            files = 5;
            columnes = 5;
        } else if (Sala.equals("012") || Sala.equals("017") || Sala.equals("018") || Sala.equals("AI1")
                || Sala.equals("ARE")) {
            N = 20;
            files = 4;
            columnes = 5;
        } else if (Sala.equals("AI2")) {
            N = 10;
            files = 2;
            columnes = 5;
        }
    }

    public static Ordenadors[] ITEMS = new Ordenadors[N];
    private static int estatReserva;

    public static int i;
    static{
        for(int fi = 0; fi < files; fi++){
            for(int col = 0; col < columnes; col++){
                for (i = 0; i < N; i++){
                    ITEMS[i] = new Ordenadors("Lliure", R.drawable.pc, true);
                }
            }
        }
        /*int fi = 0, col = 0;
        new ConsultarDades().execute("http://10.0.2.2/FreeComputer/consultarPC.php?fila"+
                (fi+1)+"&columna="+(col+1)+"sala="+Sala);*/

    }

    public static Ordenadors getItem(int id) {
        for (Ordenadors item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    private static class ConsultarDades extends AsyncTask<String, Void, String> {
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
            Log.d("ALEXPLANAA", result);
            try {
                ja = new JSONArray(result);
                Log.d("reposta", "" + ja);
                estatReserva = ja.getInt(5);
                /*if(estatReserva == 0){
                    ITEMS[i] = new Ordenadors("Lliure", R.drawable.pc, true);
                }else{
                    ITEMS[i] = new Ordenadors("Reservat", R.drawable.no_pc, false);
                }*/

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("reposta", "No ENtRAAA");
            }
        }
    }

    private static String downloadUrl(String myurl) throws IOException {
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
            Log.d("reposta", "La res-+posta es: " + responseCode);
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

    public static String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

}
