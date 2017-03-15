package alexplanasobany7.freecomputer;

import android.os.AsyncTask;
import android.util.Log;

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

/**
 * Created by alexplanasobany on 22/2/17.
 */

public class Ordenadors {
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

    /**
     * Obte un item basat en el seu ID
     *
     * @param id
     * @return Ordenadors
     */

    public static int N = 10;
    public static Ordenadors[] ITEMS = new Ordenadors[N];


    static {
        for (int i = 0; i < N; i++){
            //new ConsultarDades().execute("http://10.0.2.2/")
            ITEMS[i] = new Ordenadors("Lliure", R.drawable.pc, true);
        }
    }


    public static Ordenadors getItem(int id) {
        for (Ordenadors item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
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
                Log.d("reposta", "La resposta es: " + ja);


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
