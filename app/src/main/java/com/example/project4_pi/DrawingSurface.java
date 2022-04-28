package com.example.project4_pi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

public class DrawingSurface extends View {

    static Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    static int paintColor = 0xFFFF0000;
    //stroke width
    private  float STROKE_WIDTH = 5f;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;
    //eraser mode
    private boolean erase=false;

    //constructor
    public DrawingSurface(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
        setErase(erase);
    }


    private void setupDrawing(){
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(STROKE_WIDTH);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    //*************************************** View assigned size  ****************************************************

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    public void setErase(boolean isErase){
        erase=isErase;
        drawPaint = new Paint();
        if(erase) {
            setupDrawing();
            int srcColor= 0x00000000;

            PorterDuff.Mode mode = PorterDuff.Mode.CLEAR;
            PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(srcColor, mode);

            drawPaint.setColorFilter(porterDuffColorFilter);

            drawPaint.setColor(srcColor);
            drawPaint.setXfermode(new PorterDuffXfermode(mode));

        }
        else {

            setupDrawing();

        }
    }

    //************************************   draw view  *************************************************************

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    //***************************   respond to touch interaction   **************************************************

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        canvasPaint.setColor(paintColor);
        float touchX = event.getX();
        float touchY = event.getY();
        //respond to down, move and up events

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawCanvas.drawCircle(touchX,touchY,10,drawPaint);
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawPath.lineTo(touchX, touchY);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawCanvas.drawCircle(touchX,touchY,10,drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        //redraw
        invalidate();
        return true;
    }


    public void setColor(int color){
        drawPaint.setColor(color);
    }

    public void clearScreen(){
        drawCanvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
    }

}

