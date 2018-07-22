package com.example.android.ballbounce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class AnimatedView extends android.support.v7.widget.AppCompatImageView{
    private Context mContext;
    int x = -1;
    boolean drawLine = false;
    int y = -1;
    int level=0;
    Paint stroke;
    int bleft,btop,bright,bbottom;
    Paint textPaint,refreshPaint,boundary,endpaint;
    int counter=0;
    String gameover="";
    Coordinate point;
    private int xVelocity = 10;
    private int yVelocity = 5;
    int pwid,phei;
    Boolean played=false;
    Boolean over=true;
    int areascore;
    private Handler h;
    private final int FRAME_RATE = 30;
    BitmapDrawable ball;

    public AnimatedView(Context context, AttributeSet attrs)  {
        super(context, attrs);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
         pwid = metrics.widthPixels;
         phei = metrics.heightPixels;
        btop=0;bleft=0;bbottom=1548;bright=pwid;
        mContext = context;
        stroke = new Paint();
        ball = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.bounceball);
        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        refreshPaint=new Paint();
        endpaint=new Paint();
        endpaint.setColor(Color.RED);
        refreshPaint.setColor(Color.WHITE);
        boundary=new Paint();
        boundary.setColor(Color.WHITE);
        boundary.setStyle(Paint.Style.STROKE);
        h = new Handler();

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:
                        drawLine = true;
                        played=true;
                        point=new Coordinate(((int) event.getX()), ((int) event.getY()));
                      //  drawingBitmap= cut(drawingBitmap);
                        invalidate();


                }


                return true;
            }
        });

    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            if(over)
            invalidate();
        }
    };

    protected void onDraw(Canvas c) {

        //c=new Canvas(cropBitmap);

        if (x<0 && y <0) {
            x = (bright-bleft)/2;
            y = (bbottom-btop)/2;
        }
        else
        {
            x += xVelocity;
            y += yVelocity;
            if ((x > (bright) - ball.getBitmap().getWidth()) || (x < bleft)) {
                xVelocity = xVelocity*-1;
            }
            if ((y > (bbottom) - ball.getBitmap().getHeight()) || (y < btop)) {
                yVelocity = yVelocity*-1;
            }
        }

        Log.e("bound"," "+bleft+" "+btop+" "+bbottom+" "+bright);
        c.drawRect(30, 1550,300, 1700, refreshPaint);

        if(drawLine)
        {
            if(point.getX()>30 && point.getX()<300 && point.getY()<1700 && point.getY()>1550)
            {
                counter++;
                Log.e("counter","   "+counter%2);
            }
            else if(counter%2==1) {
                stroke.setColor(Color.WHITE);
                stroke.setStyle(Paint.Style.STROKE);
                c.drawLine(0, point.getY(), this.getWidth(), point.getY(), stroke);
                c.drawText(
                        gameover, // Text to draw
                        pwid/2, // x
                        phei/2, // y
                        textPaint // Paint
                );
                bbottom=point.getY();
                if (y+20>=bbottom)
                {
                    c.drawRect(0,0,pwid,phei,endpaint);
                    areascore=100-(((bright-bleft)*(bbottom-btop))/60000);
                    gameover="SCORE: "+areascore;
                    Log.e("score","SCORE: "+areascore);
                    c.drawText(
                            gameover, // Text to draw
                            pwid/2, // x
                            phei/2, // y
                            textPaint // Paint
                    );
                    over=false;
                }
            }
            else {
                stroke.setColor(Color.WHITE);
                stroke.setStyle(Paint.Style.STROKE);
                c.drawLine(point.getX(), this.getHeight(), point.getX(), 0, stroke);
                c.drawText(
                        "V", // Text to draw
                        800, // x
                        1750, // y
                        textPaint // Paint
                );
                bright=point.getX();
                if(x+20>bright-10)
                {
                    c.drawRect(0,0,pwid,phei,endpaint);
                    areascore=100-(((bright-bleft)*(bbottom-btop))/60000);
                    gameover="SCORE: "+areascore;
                    Log.e("score","SCORE: "+areascore);
                    c.drawText(
                            gameover, // Text to draw
                            pwid/2, // x
                            phei/2, // y
                            textPaint // Paint
                    );
                    over=false;
                }
            }
        }
//        if( (100-(((bright-bleft)*(bbottom-btop))/60000)>50))
//          levelup(level++);
        if(over)
        {c.drawRect(bleft,btop,bright,bbottom,boundary);
        c.drawBitmap(ball.getBitmap(), x, y, null);
        h.postDelayed(r, FRAME_RATE);}

    }
    private void levelup(int lev)
    {
        xVelocity+=lev+4;
        yVelocity+=lev+2;
    }
}