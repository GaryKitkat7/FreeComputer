package alexplanasobany7.freecomputer;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;

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
import java.util.Vector;
import java.util.concurrent.ExecutionException;

/**
 * Created by alexplanasobany on 10/4/17.
 */

public class MapaPrincipal extends View{

    private Paint paint, paintLletres, paintVerd, paintAmbar, paintVermell, paintAulaTancada;
    private RectF menjador, sala008, sala010, sala011, sala012, sala017,sala018;
    public Map<RectF, String> rectangles = new HashMap<>();
    public Vector<Paint> ColorSala = new Vector<>();
    public List<RectF> rectangle = new ArrayList<>();
    public String sala, Sala008, Sala010, Sala011, Sala012, Sala017, Sala018, SalaTemp;
    public int S8,S10,S11,S12, S17, S18, STemp, dia, ii = 0;
    Context context;
    public List<String> aulesOcup;
    public String[] OrdenadorsLLiures, Sales = PantallaEsperaPrincipalActivity.Sales, aulesOcupades;
    long down, diff;
    public ArrayList<ItemHoraris> itemHorarisArrayList , itemSala;
    private AdaptadorLlistaHoraris adaptadorLlistaHoraris;
    private ListView llista;



    public MapaPrincipal(Context context,String[] strings, int dia, String[] AulesOc){
        super(context);
        this.context = context;
        this.dia = dia;
        this.OrdenadorsLLiures = strings;
        this.aulesOcupades = AulesOc;
        init();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float touchX = event.getX();
        final float touchY = event.getY();
        final Handler handler = new Handler();
        Runnable mLongPressed = new Runnable() {
            public void run() {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Proxims Horaris");

                new ConsultarDadesHorari().execute("http://95.85.16.142/ConsultarHoraris.php?id_clase="+sala+"&id_dia="+dia);

                llista = new ListView(getContext());

                adaptadorLlistaHoraris = new AdaptadorLlistaHoraris(getContext(),itemHorarisArrayList);
                llista.setAdapter(adaptadorLlistaHoraris);


                builder.setView(llista);
                final Dialog dialog = builder.create();

                dialog.show();
            }
        };
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down = System.currentTimeMillis();
                break;

            case MotionEvent.ACTION_UP:
                diff = System.currentTimeMillis() - down;
                Log.d("Temps diferencia", String.valueOf(diff));
                if(diff > 700){
                    for(RectF rect : rectangle){
                        if(rect.contains(touchX,touchY)){
                            sala = rectangles.get(rect);
                            handler.postDelayed(mLongPressed,0);
                        }
                    }
                }else{
                    for(RectF rect : rectangle) {
                        if (rect.contains(touchX, touchY)) {
                            sala = rectangles.get(rect);
                            startActivity();

                        }
                    }
                }
                break;
        }
        return true;
    }

    private void startActivity() {
        Intent myIntent = new Intent(getContext(),InteriorClassesActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra("sala", sala);
        myIntent.putExtra("Sales", OrdenadorsLLiures);
        getContext().startActivity(myIntent);
    }

    public void init(){
        Glide.with(getContext()).load(R.drawable.tr1tr2min).asBitmap().into(new SimpleTarget<Bitmap>(2800,2800) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                setBackground(drawable);
            }
        });

        itemHorarisArrayList = new ArrayList<>();

        itemSala = new ArrayList<>();

        // INICIALITZAR TOTS ELS PAINTS
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(16.0f);
        paint.setStyle(Paint.Style.STROKE);

        paintVerd = new Paint();
        paintVerd.setAntiAlias(true);
        paintVerd.setColor(Color.GREEN);
        paintVerd.setStrokeWidth(16.0f);
        paintVerd.setStyle(Paint.Style.FILL);

        paintAmbar = new Paint();
        paintAmbar.setAntiAlias(true);
        paintAmbar.setColor(Color.YELLOW);
        paintAmbar.setStrokeWidth(16.0f);
        paintAmbar.setStyle(Paint.Style.FILL);

        paintVermell = new Paint();
        paintVermell.setAntiAlias(true);
        paintVermell.setColor(Color.RED);
        paintVermell.setStrokeWidth(16.0f);
        paintVermell.setStyle(Paint.Style.FILL);

        paintAulaTancada = new Paint();
        paintAulaTancada.setAntiAlias(true);
        paintAulaTancada.setColor(Color.GRAY);
        paintAulaTancada.setStrokeWidth(16.0f);
        paintAulaTancada.setStyle(Paint.Style.FILL);

        paintLletres = new Paint();
        paintLletres.setAntiAlias(true);
        paintLletres.setColor(Color.BLACK);
        paintLletres.setStyle(Paint.Style.FILL);
        paintLletres.setTextSize(50);

        sala008 = new RectF(99,2157,321,2494);
        sala010 = new RectF(99,1390,330,1716);
        sala011 = new RectF(99,1037,330,1379);
        sala012 = new RectF(99,765,333,1028);
        sala017 = new RectF(547,81,771,324);
        sala018 = new RectF(775,81,981,324);

        rectangle.add(sala008);
        rectangle.add(sala010);
        rectangle.add(sala011);
        rectangle.add(sala012);
        rectangle.add(sala017);
        rectangle.add(sala018);

        rectangles.put(sala008, "008");
        rectangles.put(sala010, "010");
        rectangles.put(sala011, "011");
        rectangles.put(sala012, "012");
        rectangles.put(sala017, "017");
        rectangles.put(sala018, "018");

    }


    @Override
    public void onDraw(Canvas canvas){
        Sala008 = CalcularNumeroClasse(OrdenadorsLLiures,"008");
        S8 = Integer.valueOf(Sala008);

        Sala010 = CalcularNumeroClasse(OrdenadorsLLiures,"010");
        S10 = Integer.valueOf(Sala010);

        Sala011 = CalcularNumeroClasse(OrdenadorsLLiures,"011");
        S11 = Integer.valueOf(Sala011);

        Sala012 = CalcularNumeroClasse(OrdenadorsLLiures,"012");
        S12 = Integer.valueOf(Sala012);

        Sala017 = CalcularNumeroClasse(OrdenadorsLLiures,"017");
        S17 = Integer.valueOf(Sala017);

        Sala018 = CalcularNumeroClasse(OrdenadorsLLiures,"018");
        S18 = Integer.valueOf(Sala018);



        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.pc);
        float MAX_IMAGE_SIZE = 50;
        Bitmap scaledBitmap = scaleDown(bitmap, MAX_IMAGE_SIZE, true);


        if(S8 > 5){
            canvas.drawRect(sala008,paintVerd);
        }else if (S8 >= 1 && S8 <=5){
            canvas.drawRect(sala008,paintAmbar);
        }else{
            canvas.drawRect(sala008,paintVermell);
        }
        canvas.drawBitmap(scaledBitmap,270,2439,paint);
        if (S8 > 9){
            canvas.drawText(Sala008,211,2479,paintLletres);
        }else{
            canvas.drawText(Sala008,241,2479,paintLletres);
        }

        if(S10 > 5){
            canvas.drawRect(sala010,paintVerd);
        }else if (S10 >= 1 && S10 <=5){
            canvas.drawRect(sala010,paintAmbar);
        }else{
            canvas.drawRect(sala010,paintVermell);
        }
        canvas.drawBitmap(scaledBitmap,270,1656,paint);
        if (S10 > 9){
            canvas.drawText(Sala010,211,1696,paintLletres);
        }else{
            canvas.drawText(Sala010,241,1696,paintLletres);
        }

        if(S11 > 5){
            canvas.drawRect(sala011,paintVerd);
        }else if (S11 >= 1 && S11 <=5){
            canvas.drawRect(sala011,paintAmbar);
        }else{
            canvas.drawRect(sala011,paintVermell);
        }
        canvas.drawBitmap(scaledBitmap,270,1319,paint);
        if (S11 > 9){
            canvas.drawText(Sala011,211,1359,paintLletres);
        }else{
            canvas.drawText(Sala011,241,1359,paintLletres);
        }

        if(S12 > 5){
            canvas.drawRect(sala012,paintVerd);
        }else if (S12 >= 1 && S12 <=5){
            canvas.drawRect(sala012,paintAmbar);
        }else{
            canvas.drawRect(sala012,paintVermell);
        }
        canvas.drawBitmap(scaledBitmap,280,975,paint);
        if (S12 > 9){
            canvas.drawText(Sala012,220,1015,paintLletres);
        }else{
            canvas.drawText(Sala012,250,1015,paintLletres);
        }

        if(S17 > 5){
            canvas.drawRect(sala017,paintVerd);
        }else if (S17 >= 1 && S17 <=5){
            canvas.drawRect(sala017,paintAmbar);
        }else{
            canvas.drawRect(sala017,paintVermell);
        }
        canvas.drawBitmap(scaledBitmap,719,268,paint);
        if (S17 > 9){
            canvas.drawText(Sala017,659,308,paintLletres);
        }else{
            canvas.drawText(Sala017,689,308,paintLletres);
        }

        if(S18 > 5){
            canvas.drawRect(sala018,paintVerd);
        }else if (S18 >= 1 && S18 <=5){
            canvas.drawRect(sala018,paintAmbar);
        }else{
            canvas.drawRect(sala018,paintVermell);
        }
        canvas.drawBitmap(scaledBitmap,927,268,paint);
        if (S18 > 9){
            canvas.drawText(Sala018,867,308,paintLletres);
        }else{
            canvas.drawText(Sala018,897,308,paintLletres);
        }

        for(int i = 0; aulesOcupades[i] != null; i++){
            if (aulesOcupades[i].equals("008")){
                canvas.drawRect(sala008,paintAulaTancada);
                rectangle.remove(sala008);
            }else if(aulesOcupades[i].equals("010")){
                canvas.drawRect(sala010,paintAulaTancada);
                rectangle.remove(sala010);
            }else if(aulesOcupades[i].equals("011")){
                canvas.drawRect(sala011,paintAulaTancada);
                rectangle.remove(sala011);
            }else if(aulesOcupades[i].equals("012")){
                canvas.drawRect(sala012,paintAulaTancada);
                rectangle.remove(sala012);
            }else if(aulesOcupades[i].equals("017")){
                canvas.drawRect(sala017,paintAulaTancada);
                rectangle.remove(sala017);
            }else if(aulesOcupades[i].equals("018")){
                canvas.drawRect(sala018,paintAulaTancada);
                rectangle.remove(sala018);
            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(2800, 2800);
    }

    public String CalcularNumeroClasse(String[] vector, String sala){
        String ResultatFinal = "";
        int resfin, total;
        int i = 0;
        if(sala.equals("008")){
            total = 30;
            for(int j = 0; j < 30; j++){
                if(vector[j].equals("1")){
                    i++;
                }
            }
            resfin = total - i;
            ResultatFinal = String.valueOf(resfin);
        }else if(sala.equals("010")){
            total = 30;
            for(int j = 0; j < 30; j++){
                if(vector[j+30].equals("1")){
                    i++;
                }
            }
            resfin = total - i;
            ResultatFinal = String.valueOf(resfin);
        }else if(sala.equals("011")){
            total = 25;
            for(int j = 0; j < 25; j++){
                if(vector[j+60].equals("1")){
                    i++;
                }
            }
            resfin = total - i;
            ResultatFinal = String.valueOf(resfin);
        }else if (sala.equals("012")){
            total = 20;
            for(int j = 0; j < 20; j++){
                if(vector[j+85].equals("1")){
                    i++;
                }
            }
            resfin = total - i;
            ResultatFinal = String.valueOf(resfin);
        }else if (sala.equals("017")){
            total = 20;
            for(int j = 0; j < 20; j++){
                if(vector[j+105].equals("1")){
                    i++;
                }
            }
            resfin = total - i;
            ResultatFinal = String.valueOf(resfin);
        }else if (sala.equals("018")){
            total = 20;
            for(int j = 0; j < 20; j++){
                if(vector[j+125].equals("1")){
                    i++;
                }
            }
            resfin = total - i;
            ResultatFinal = String.valueOf(resfin);
        }
        return ResultatFinal;
    }

    public Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
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

        protected void onPostExecute(String result) {
            JSONArray ja;
            try {
                ja = new JSONArray(result);
                itemHorarisArrayList.clear();
                ii = 0;
                for(int j = 0; j < ja.length(); j++){
                    String consulta = ja.getString(j).substring(2);
                    int coma = consulta.indexOf(",");
                    String assig = consulta.substring(0,coma-1);
                    consulta = consulta.substring(coma+2);
                    coma = consulta.indexOf(",");
                    String hi = consulta.substring(0,coma-1);
                    consulta = consulta.substring(coma+2);
                    coma = consulta.indexOf(",");
                    String hf = consulta.substring(0,coma-1);
                    itemHorarisArrayList.add(ii,new ItemHoraris(assig,hi,hf));
                    ii++;
                }

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
