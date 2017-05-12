package alexplanasobany7.freecomputer;

import android.app.Activity;
import android.app.Application;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alexplanasobany on 10/4/17.
 */

public class MapaPrincipal extends View{


    private Paint paint, paintLletres, areaRestringida;
    private RectF menjador, sala008, sala010, sala011, sala012, sala017,sala018;
    public Map<RectF, String> rectangles = new HashMap<>();
    public List<RectF> rectangle = new ArrayList<>();
    public String sala;
    Context context;
    public String[] OrdenadorsLLiures = PantallaEsperaPrincipalActivity.sales;


    public MapaPrincipal(Context context) {
        super(context);
        this.context = context;
        // INICIALITZAR TOTS ELS PAINTS
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(16.0f);
        paint.setStyle(Paint.Style.STROKE);

        paintLletres = new Paint();
        paintLletres.setAntiAlias(true);
        paintLletres.setColor(Color.BLACK);
        paintLletres.setStyle(Paint.Style.FILL);
        paintLletres.setTextSize(50);

        areaRestringida = new Paint();
        areaRestringida.setAntiAlias(true);
        areaRestringida.setColor(Color.rgb(50,50,50));
        areaRestringida.setStyle(Paint.Style.FILL);
        areaRestringida.setStrokeWidth(16.0f);

        //TODO: COMPLETAR LES SALES QUE FALTEN
        menjador = new RectF(508, 1208, 1992, 2292);
        sala008 = new RectF(108, 2408, 392, 2692);
        sala010 = new RectF(108,1508,392,1892);
        sala012 = new RectF(108,808,396,1092);
        sala011 = new RectF(108,1108,392,1492);
        sala017 = new RectF(908,108,1392,392);
        sala018 = new RectF(1408,108,1892,392);

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
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("Rectangles", String.valueOf(rectangles));
        float touchX = event.getX();
        float touchY = event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_UP:
                Log.d("AlexPlana", "Apretat");
                for(RectF rect : rectangle){
                    Log.d("Alexxx", String.valueOf(rect));
                    Log.d("PosicioX", String.valueOf(touchX));
                    Log.d("PosicioY", String.valueOf(touchY));
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

            case MotionEvent.ACTION_DOWN:
                for(RectF rect : rectangle){
                    if(rect.contains(touchX,touchY)){
                        sala = rectangles.get(rect);
                        Toast toast = Toast.makeText(context,CalcularNumeroClasse(
                                OrdenadorsLLiures,sala),Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
        }
        return true;
    }

    private void startActivity() {
        Intent myIntent = new Intent(getContext(),InteriorClassesActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra("sala", sala);
        getContext().startActivity(myIntent);
    }


    @Override
    public void onDraw(Canvas canvas){
        //Crear les aules
        //Primera aula -> del pixel 100 al 600 de llarg i ample +/-8 en les horitzontals per compensar l'ample de la linea
        canvas.drawLine(92,100,408,100,paint);
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
        canvas.drawRect(menjador,areaRestringida);

        //TODO: COM SÉ QUINA CLASSE ESTÀ OBERTA. CONSULTAR BBDD??
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(2800, 2800);
    }

    public String CalcularNumeroClasse(String[] vector, String sala){
        String ResultatFinal = "";
        int i = 0;
        if(sala.equals("008")){
            for(int j = 0; j < 30; j++){
                if(vector[j].equals("1")){
                    i++;
                }
            }
            ResultatFinal = String.valueOf(i) + "/30";
        }else if(sala.equals("010")){
            for(int j = 0; j < 30; j++){
                if(vector[j+30].equals("1")){
                    i++;
                }
            }
            ResultatFinal = String.valueOf(i) + "/30";
        }else if(sala.equals("011")){
            for(int j = 0; j < 25; j++){
                if(vector[j+60].equals("1")){
                    i++;
                }
            }
            ResultatFinal = String.valueOf(i) + "/25";
        }else if (sala.equals("012")){
            for(int j = 0; j < 20; j++){
                if(vector[j+85].equals("1")){
                    i++;
                }
            }
            ResultatFinal = String.valueOf(i) + "/20";
        }else if (sala.equals("017")){
            for(int j = 0; j < 20; j++){
                if(vector[j+105].equals("1")){
                    i++;
                }
            }
            ResultatFinal = String.valueOf(i) + "/20";
        }else if (sala.equals("018")){
            for(int j = 0; j < 20; j++){
                if(vector[j+125].equals("1")){
                    i++;
                }
            }
            ResultatFinal = String.valueOf(i) + "/20";
        }
        return ResultatFinal;
    }
}
