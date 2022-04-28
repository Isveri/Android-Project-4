package com.example.project4_pi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class DrawingSurface extends View {

    static Path drawingPath;
    private Paint drawPaint, canvasPaint, circlePaint;
    static int baseColor = 0xFFFF0000;
    private  float STROKE_WIDTH = 5f;
    private Canvas canvas;
    private Bitmap bitmap;


    //constructors
    public DrawingSurface(Context context){
        super(context);
        init();
    }

    public DrawingSurface(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    public DrawingSurface(Context context, AttributeSet attrs,
                          int defStyleAttr){
        super(context, attrs, defStyleAttr);
        init();
    }

    //initialization
    private void init(){
        drawingPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(baseColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(STROKE_WIDTH);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        circlePaint = new Paint();
        circlePaint.setColor(baseColor);
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeWidth(STROKE_WIDTH);
        circlePaint.setStyle(Paint.Style.FILL);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    // zmiana rozmiaru w przypadku przekrecenia ekranu
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawingPath, drawPaint);
    }

    // obsluga listenera
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        canvasPaint.setColor(baseColor);
        float X = event.getX();
        float Y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                canvas.drawCircle(X,Y,10,circlePaint);
                drawingPath.moveTo(X, Y);
                break;
            case MotionEvent.ACTION_MOVE:
                canvas.drawPath(drawingPath, drawPaint);
                drawingPath.lineTo(X, Y);
                break;
            case MotionEvent.ACTION_UP:
                drawingPath.lineTo(X, Y);
                canvas.drawPath(drawingPath, drawPaint);
                canvas.drawCircle(X,Y,10,circlePaint);
                drawingPath.reset();
                break;
            default:
                return false;
        }
        invalidate();   // refreshuje
        return true;
    }

    // zmiana koloru pisaka
    public void setColor(int color){
        drawPaint.setColor(color);
        circlePaint.setColor(color);
    }
    // czyszczenie ekranu
    public void clearScreen(){
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
    }

}

