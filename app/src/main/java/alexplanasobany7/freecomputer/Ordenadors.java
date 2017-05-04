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
import java.util.Arrays;

/**
 * Created by alexplanasobany on 22/2/17.
 */

public class Ordenadors {
    private String nombre, Sala;
    private int idDrawable;
    private boolean Reserva;
    private int N ,files, columnes;
    public int cont = 0;
    //private String[] SalaActual = new String[30];
    private Ordenadors[] ITEMS = new Ordenadors[30];
    public static String[] EstatsSales = PantallaEsperaPrincipalActivity.sales;
    public String[] SalaActual;



    public Ordenadors(String nombre, int idDrawable, boolean Reserva) {
        this.nombre = nombre;
        this.idDrawable = idDrawable;
        this.Reserva = Reserva;
    }

    public Ordenadors(String sala) {
        this.Sala = sala;
        Log.d("Sala", "  "+Sala);
        if (Sala.equals("008") || Sala.equals("010")) {
            N = 30;
            files = 5;
            columnes = 6;
            SalaActual = new String[N];
            if (Sala.equals("008")) {
                for(int j = 0; j < N; j++){
                    SalaActual[j] = EstatsSales[j];
                }
                /*for (int f = 0; f < files; f++) {
                    for (int c = 0; c < columnes; c++) {
                        new ConsultarDades().execute("http://95.85.16.142/consultarPC.php?fila="+
                                (f+1)+"&columna="+(c+1)+"&sala="+Sala);
                    }
                }*/
            } else {
                SalaActual = new String[N];
                for(int j = 0; j < N; j++){
                    SalaActual[j] = EstatsSales[j];
                }
                /*for (int f = 0; f < files; f++) {
                    for (int c = 0; c < columnes; c++) {
                        new ConsultarDades().execute("http://95.85.16.142/consultarPC.php?fila="
                                + (f + 1) + "&columna=" + (c + 1) + "&sala=" + Sala);
                    }
                }*/
            }

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

        Log.d("VectorSAlaActual", Arrays.toString(SalaActual));

        int i = 0;
        for (int fi = 0; fi < files; fi++) {
            for (int col = 0; col < columnes; col++) {
                Log.d("SaLa_Actual", String.valueOf(SalaActual[0]));
                if (SalaActual[i].equals("0")) {
                    ITEMS[i] = new Ordenadors("Lliure", R.drawable.pc, true);
                } else if (SalaActual[i].equals("1")) {
                    ITEMS[i] = new Ordenadors("Reservat", R.drawable.no_pc, false);
                }
                i++;
            }
        }
    }

    public int getLongitud(){
        return ITEMS.length;
    }

    public Ordenadors getPosicio(int position){
        return ITEMS[position];
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

    /*public static String Sala = "008";*/
    /*public static int N, files, columnes;

    static {
        if (Sala.equals("008") || Sala.equals("010")) {
            N = 30;
            files = 5;
            columnes = 6;
            SalaActual = new String[N];
            if(Sala.equals("008")){
                for(int i = 0; i < N; i++){
                    SalaActual[i] = EstatsSales[i];
                }
            }else{
                SalaActual = new String[N];
                for(int i = 0; i < N; i++){
                    SalaActual[i] = EstatsSales[i+30];
                }
            }

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
    private static int i = 0;

    static {
        for (int fi = 0; fi < files; fi++) {
            for (int col = 0; col < columnes; col++) {
                if (SalaActual[i].equals("0")) {
                    ITEMS[i] = new Ordenadors("Lliure", R.drawable.pc, true);
                } else if (SalaActual[i].equals("1")) {
                    ITEMS[i] = new Ordenadors("Reservat", R.drawable.no_pc, false);
                }
                i++;
            }
        }
    }*/

    /*public static Ordenadors getItem(int id) {
        for (Ordenadors item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }*/

    /*private class ConsultarDades extends AsyncTask<String, Void, String> {
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
                SalaActual[cont] = ja.getString(5);
                cont++;

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
*/
}
