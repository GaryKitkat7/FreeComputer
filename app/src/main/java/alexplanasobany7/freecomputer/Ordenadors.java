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
    public static String[] EstatsSales = MainActivity.sales;
    public static String[] SalaActual;
    public static int N, files, columnes;

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
    }

    /*public static Ordenadors getItem(int id) {
        for (Ordenadors item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }*/

}
