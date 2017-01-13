package ru.explead.breakpassword;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ru.explead.breakpassword.app.App;
import ru.explead.breakpassword.logic.Controller;

/**
 * Created by develop on 13.01.2017.
 */

public class Surface extends SurfaceView implements SurfaceHolder.Callback {

    public GameThread mThread;
    private Controller controller;


    public Surface(Context context) {
        super(context);
        init();
    }

    public Surface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Surface(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        controller = App.getController();
        createPaint();
        createBitmap();
        getHolder().addCallback(this);
    }



    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);
    }

    private void createPaint() {

    }

    @SuppressWarnings("deprecation")
    private void createBitmap() {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        mThread.setRunning(false);
        while (retry) {
            try {
                mThread.join();
                retry = false;
            }
            catch (InterruptedException e) { }
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        mThread = new GameThread(getHolder(), this);
        mThread.setRunning(true);
        mThread.start();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    public class GameThread extends Thread {

        private SurfaceView view;
        private SurfaceHolder surfaceHolder;
        public boolean running = false;

        public GameThread(SurfaceHolder surfaceHolder, SurfaceView view) {
            this.surfaceHolder = surfaceHolder;
            this.view = view;
        }

        public void setRunning(boolean run)
        {
            running = run;
        }

        @SuppressLint("WrongCall")
        public void run() {
            Canvas canvas;
            while (running) {
                canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas();
                    synchronized (view.getHolder()) {
                        onDraw(canvas);
                    }
                }
                catch (Exception e) { }
                finally {
                    if (canvas != null) {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }catch(Exception e) {}
                    }
                }
            }
        }
    }
}