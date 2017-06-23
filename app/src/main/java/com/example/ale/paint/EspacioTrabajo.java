package com.example.ale.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Ale on 17/05/2017.
 */
public class EspacioTrabajo extends View{

    private Path Trazo;                 //Variable que guarda el trazo que hago
    private Paint Pintar, Canvas;             //Variable que permite dibujar pintar en el espacio
    private int paintColor=0xFF660000;      //Color inicial con el que se empieza a pintar
    private Canvas drawCanvas;
    private Bitmap BitCanvas;               //Me permite guardar el bitmap generado

    public EspacioTrabajo(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

        private void setupDrawing()//Clase para interactuar con Canvas
        {

            Trazo=new Path();               //Inicializo
            Pintar=new Paint();

            Pintar.setColor(paintColor);    //Seteo el color inicial con el que comienza a pintar
            Pintar.setAntiAlias(true);      //
            Pintar.setStrokeWidth(20);
            Pintar.setStyle(Paint.Style.STROKE);       //Permite pintar solo bordes no rellenar y los otros dos son para q el trazo
            Pintar.setStrokeJoin(Paint.Join.ROUND);    //mas suave
            Pintar.setStrokeCap(Paint.Cap.ROUND);

            Canvas=new Paint(Paint.DITHER_FLAG);  //Permite pintar mas difuminado
        }

    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w,h,oldw,oldh);
        BitCanvas=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        drawCanvas= new Canvas(BitCanvas);
    }

    // Pinta la Vista, es llamado desde el EventTouch

    protected void onDraw(Canvas canvas){
        canvas.drawBitmap(BitCanvas,0,0,Canvas);
        canvas.drawPath(Trazo,Pintar);
    }

    //Controla los touch del usuario

    public boolean onTouchEvent (MotionEvent Event){
        float touchX=Event.getX();
        float touchY=Event.getY();

        switch (Event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Trazo.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                Trazo.lineTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_UP:
                Trazo.lineTo(touchX, touchY);
                drawCanvas.drawPath(Trazo, Pintar);
                Trazo.reset();
                break;
            default:
                return false;
            }
        invalidate();
        return true;
    }
}
