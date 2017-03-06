package alexplanasobany7.freecomputer;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by alexplanasobany on 2/3/17.
 */

public class DadesParticularsOrdenadors {
    private String NomPC;
    private int fila;
    private int columna;
    private String sala;
    private String edifici;
    private boolean estat;


    public DadesParticularsOrdenadors(String nom, int fil, int col, String sala, String edif, boolean estat){
        this.NomPC = nom;
        this.fila = fil;
        this.columna = col;
        this.sala = sala;
        this.edifici = edif;
        this.estat = estat;
    }

    public int getColumna() {
        return columna;
    }

    public String getEdifici() {
        return edifici;
    }

    public int getFila() {
        return fila;
    }

    public String getSala() {
        return sala;
    }

    public String getNomPC() {
        return NomPC;
    }

    public boolean isEstat() {
        return estat;
    }
}
