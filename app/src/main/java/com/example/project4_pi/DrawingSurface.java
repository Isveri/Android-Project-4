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
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class DrawingSurface extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;
    private Boolean threadRunning = true;
    private boolean stopThread = true;
    private Object block = new Object();

    private DrawingThread drawingThread;
    private Paint paint;
    private Bitmap bitmap = null;
    private Canvas canvas = null;

    ///KONSTRUKTORY
    public DrawingSurface(Context context) {
        super(context);
        init();
    }

    public DrawingSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
//        surfaceHolder = getHolder();
//        surfaceHolder.addCallback(this);
        init();
    }

    public DrawingSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /// METODY NADPISANE
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        synchronized (block) {
            if (event != null && canvas!=null) {
                float touchX = event.getX();
                float touchY = event.getY();
                Path path = new Path();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        path.moveTo(touchX, touchY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        canvas.drawPath(path, paint);
                        path.lineTo(touchX, touchY);
                        break;
                    case MotionEvent.ACTION_UP:
                        path.lineTo(touchX, touchY);
                        canvas.drawPath(path, paint);
                        path.reset();
                        break;
                }
                //invalidate();
            }
        }
        return true;
    }


    public boolean performClick() {
        resume();
        return super.performClick();
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        start();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        stop();
    }



    /// METODY STWORZONE
    public void start(){
        synchronized (threadRunning) {
            threadRunning = true;
        }
        drawingThread = new DrawingThread();
        drawingThread.start();
    }

    public void stop(){
        synchronized (threadRunning) {
            threadRunning = false;
        }
//        try {
//            drawingThread.join();
//        } catch (InterruptedException interruptedException) {
//
//        }
    }

    public void resume(){
        if (stopThread) {
            stop();
            stopThread = false;
        }else{
            start();
            stopThread = true;
        }
    }

    public void init(){
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
//        setWillNotDraw(false);
//        setFocusable(true);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL);
        paint.setStyle(Paint.Style.STROKE);
    }

    protected void drawFrame(Canvas canvas){
//        bitmap = Bitmap.createBitmap(getWidth(),getHeight(),Bitmap.Config.ARGB_8888);
//        canvas.drawBitmap(bitmap,255,255,new Paint(Paint.DITHER_FLAG));

    }




    /// klasa obslugująca wątek rysowania
    private class DrawingThread extends Thread{

        public void run(){
            while(threadRunning){
                canvas = null;
                try{
                    synchronized (surfaceHolder) {
                        if(!surfaceHolder.getSurface().isValid()) continue;
                        canvas = surfaceHolder.lockCanvas(null);
                        synchronized (block) {
                            if (threadRunning) {
                                drawFrame(canvas);
                            }
                        }
                    }
                } finally{
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
                try{
                    Thread.sleep(1000 / 25);
                }catch(InterruptedException e){

                }
            }
        }
    }

    public void setColor(int color){
        paint.setColor(color);
    }

    public void clearScreen(){
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
    }
}
