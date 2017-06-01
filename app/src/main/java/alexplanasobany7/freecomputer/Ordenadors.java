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
    private Ordenadors[] ITEMS;
    public String[] EstatsSales;
    public String[] SalaActual;



    public Ordenadors(String nombre, int idDrawable, boolean Reserva) {
        this.nombre = nombre;
        this.idDrawable = idDrawable;
        this.Reserva = Reserva;
    }

    public Ordenadors(String sala, String[] estatSales) {
        this.Sala = sala;
        this.EstatsSales = estatSales;
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
            } else {
                SalaActual = new String[N];
                for(int j = 0; j < N; j++){
                    SalaActual[j] = EstatsSales[j+30];
                }
            }

        } else if (Sala.equals("011")) {
            N = 25;
            files = 5;
            columnes = 5;
            SalaActual = new String[N];
            for (int j = 0; j < N; j++) {
                SalaActual[j] = EstatsSales[j + 60];
            }
        }else {
            N = 20;
            files = 4;
            columnes = 5;
            SalaActual = new String[N];
            if(Sala.equals("012")){
                for(int j = 0; j < N; j++){
                    SalaActual[j] = EstatsSales[j+85];
                }
            }else if (Sala.equals("017")){
                for(int j = 0; j < N; j++){
                    SalaActual[j] = EstatsSales[j+105];
                }
            }else if (Sala.equals("018")){
                for(int j = 0; j < N; j++){
                    SalaActual[j] = EstatsSales[j+125];
                }
            }
        }
        Log.d("FILES", String.valueOf(files));
        Log.d("COLUMNES", String.valueOf(columnes));
        Log.d("VectorSAlaActual", Arrays.toString(SalaActual));

        ITEMS = new Ordenadors[N];
        int i = 0;
        for (int fi = 0; fi < files; fi++) {
            for (int col = 0; col < columnes; col++) {
                if (SalaActual[i].equals("0")) {
                    ITEMS[i] = new Ordenadors("Lliure", R.drawable.pc, true);
                } else if (SalaActual[i].equals("1")) {
                    ITEMS[i] = new Ordenadors("Ocupat", R.drawable.pcgris2, false);
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
}
