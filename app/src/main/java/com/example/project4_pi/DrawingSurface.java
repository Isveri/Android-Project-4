package com.example.project4_pi;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;

public class DrawingSurface extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private SurfaceHolder surfaceHolder;
    private Thread thread;
    private boolean threadWorking = false;

    private Object block = new Object();



    public DrawingSurface(Context context, AttributeSet attrs) {
        super(context, attrs);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    public void resume(){
        thread = new Thread(this);
        threadWorking  = true;
        thread.start();
    }

    public void stop(){
        threadWorking = false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        synchronized (block){
            ////
        }
        return true;
    }

    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public void run(){
        while(threadWorking){
            Canvas kanwa = null;
            try{
                synchronized (surfaceHolder) {
                    if(!surfaceHolder.getSurface().isValid()) continue;
                    kanwa = surfaceHolder.lockCanvas(null);
                    synchronized (block) {
                        if (threadWorking) {
                            ////
                        }
                    }
                }
            } finally{
                if (kanwa != null) {
                    surfaceHolder.unlockCanvasAndPost(kanwa);
                }
            }
            try{
                Thread.sleep(1000 / 25);
            }catch(InterruptedException e){

            }
        }
    }



    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        threadWorking = false;
    }

}
