package alexplanasobany7.freecomputer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by alexplanasobany on 10/4/17.
 */

public class MapaPrincipal extends View {

    private Paint paint;

    public MapaPrincipal(Context context) {
        super(context);
        // INICIALITZAR TOTS ELS PAINTS
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(16.0f);
        paint.setStyle(Paint.Style.STROKE);

    }

    @Override
    public void onDraw(Canvas canvas){
        //Crear les aules
        //Primera aula -> del pixel 100 al 400 de llarg i ample +/-8 en les horitzontals per compensar l'ample de la linea
        canvas.drawLine(92,100,408,100,paint);
        canvas.drawLine(100,100,100,400,paint);
        canvas.drawLine(400,100,400,400,paint);
        canvas.drawLine(92,400,408,400,paint);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredWidth = 2000;
        int desiredHeight = 2000;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measurar Amplada
        if (widthMode == MeasureSpec.EXACTLY) {
            //Mida que ha de tenir
            width = widthSize;
        } else {
            width = desiredWidth;
        }

        //Measurar Altura
        if (heightMode == MeasureSpec.EXACTLY && heightSize != 0) {
            //Mida que ha de tenir
            height = heightSize;
        } else {
            //Si la mida es zero, posar-ne una per defecte (Forçar la mida)
            height = desiredHeight;
        }

        //Comprovar que no obtinguin un valor "0" (Pantalla Blanca)
        Log.d("AlexPlana:", String.valueOf(width));
        Log.d("Alex:", String.valueOf(height));

        setMeasuredDimension(width, height);
    }
}
