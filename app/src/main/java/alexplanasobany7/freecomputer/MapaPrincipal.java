package alexplanasobany7.freecomputer;

import android.app.Activity;
import android.app.Application;
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
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

/**
 * Created by alexplanasobany on 10/4/17.
 */

public class MapaPrincipal extends View{

    private Paint paint, paintLletres, paintVerd, paintAmbar, paintVermell;
    private RectF menjador, sala008, sala010, sala011, sala012, sala017,sala018;
    public Map<RectF, String> rectangles = new HashMap<>();
    public Vector<Paint> ColorSala = new Vector<>();
    public List<RectF> rectangle = new ArrayList<>();
    public String sala, Sala008, Sala010, Sala011, Sala012, Sala017, Sala018, SalaTemp;
    public int S8,S10,S11,S12, S17, S18, STemp;
    Context context;
    public String[] OrdenadorsLLiures;


    public MapaPrincipal(Context context,String[] strings){
        super(context);
        this.context = context;
        this.OrdenadorsLLiures = strings;
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("Rectangles", String.valueOf(rectangles));
        float touchX = event.getX();
        float touchY = event.getY();
        Log.d("PosicioX", String.valueOf(touchX));
        Log.d("PosicioY", String.valueOf(touchY));
        switch(event.getAction()){
            case MotionEvent.ACTION_UP:
                Log.d("AlexPlana", "Apretat");
                for(RectF rect : rectangle){
                    Log.d("Alexxx", String.valueOf(rect));
                    if(rect.contains(touchX,touchY)){
                        Log.d("Plana", "Entrar a la Clase");
                        sala = rectangles.get(rect);
                        Log.d("La sala es:",sala);
                        startActivity();

                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("Alex", "Desplaçant la pantalla");
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

        paintLletres = new Paint();
        paintLletres.setAntiAlias(true);
        paintLletres.setColor(Color.BLACK);
        paintLletres.setStyle(Paint.Style.FILL);
        paintLletres.setTextSize(50);

        //TODO: COMPLETAR LES SALES QUE FALTEN
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

        //canvas.drawText(CalcularNumeroClasse(OrdenadorsLLiures,Sala012),350,100,paintLletres);
        /*canvas.drawText(NumPCs,370,600,paintLletres);*/


        //Crear les aules
        //Primera aula -> del pixel 100 al 600 de llarg i ample +/-8 en les horitzontals per compensar l'ample de la linea
        /*canvas.drawLine(92,100,408,100,paint);
        canvas.drawLine(92,92,92,800,paint);
        canvas.drawLine(408,92,408,800,paint);
        canvas.drawLine(92,800,408,800,paint);
        //012
        canvas.drawText("012",200,965,paintLletres);
        canvas.drawLine(92,800,92,1100,paint);
        canvas.drawLine(408,800,408,1100,paint);
        canvas.drawLine(92,1100,408,1100,paint);
        //011
        canvas.drawText("011",200,1325,paintLletres);
        canvas.drawLine(92,1100,92,1500,paint);
        canvas.drawLine(92,1500,408,1500,paint);
        canvas.drawLine(408,1100,408,1500,paint);
        //010
        canvas.drawText("010",200,1725,paintLletres);
        canvas.drawLine(92,1500,92,1900,paint);
        canvas.drawLine(408,1500,408,1900,paint);
        canvas.drawLine(92,1900,408,1900,paint);
        //Sala Conferencies
        canvas.drawText("S.C.",200,2125,paintLletres);
        canvas.drawLine(92,1900,92,2308,paint);
        canvas.drawLine(408,1900,408,2308,paint);
        canvas.drawLine(92,2300,408,2300,paint);
        //008
        canvas.drawText("008",200,2565,paintLletres);
        canvas.drawLine(92,2392,92,2708,paint);
        canvas.drawLine(408,2392,408,2708,paint);
        canvas.drawLine(92,2400,408,2400,paint);
        canvas.drawLine(92,2700,408,2700,paint);

        //Porta + WC
        canvas.drawLine(400,100,900,100,paint);
        canvas.drawLine(400,400,900,400,paint);
        canvas.drawLine(900,100,900,408,paint);
        //017
        canvas.drawText("017",1100,275,paintLletres);
        canvas.drawLine(900,100,1400,100,paint);
        canvas.drawLine(908,400,1400,400,paint);
        canvas.drawLine(1400,100,1400,408,paint);
        //018
        canvas.drawText("018",1600,275,paintLletres);
        canvas.drawLine(1400,100,1908,100,paint);
        canvas.drawLine(1400,400,1908,400,paint);
        canvas.drawLine(1908,92,1908,408,paint);

        //PATI COTXES
        canvas.drawLine(500,492,500,800,paint);
        canvas.drawLine(492,500,2000,500,paint);
        canvas.drawLine(492,800,2000,800, paint);

        //015
        canvas.drawLine(500,800,500,1100,paint);
        canvas.drawLine(500,800,1300,800,paint);
        canvas.drawLine(492,1100,1300,1100,paint);
        canvas.drawLine(1300,792,1300,1108,paint);
        //Antiga Copisteria
        canvas.drawText("015",850,965,paintLletres);
        canvas.drawLine(1300,1100,2008,1100,paint);
        canvas.drawLine(2000,792,2000,1108,paint);

        //Pati + Menjador
        canvas.drawLine(500,1200,2000,1200,paint);
        canvas.drawLine(500,2300,2000,2300,paint);
        canvas.drawLine(500,1192,500,2308,paint);
        canvas.drawLine(2000,1192,2000,2308,paint);
        canvas.drawRect(menjador,areaRestringida);*/

        //TODO: COM SÉ QUINA CLASSE ESTÀ OBERTA. CONSULTAR BBDD??
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

    /*public Paint ColorSales(String salaTemporal){
        SalaTemp = CalcularNumeroClasse(OrdenadorsLLiures,rectangles.get(salaTemporal));
        STemp = Integer.parseInt(SalaTemp);

        if(STemp < 5){
            return paintVerd;
        }else if (STemp >= 1 && STemp <=5){
            return paintAmbar;
        }else{
            return paintVermell;
        }



        return ColorSala;
    }*/
}
