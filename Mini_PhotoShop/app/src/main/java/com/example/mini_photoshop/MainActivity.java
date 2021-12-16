package com.example.mini_photoshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    ImageButton zoomin,zoomout,zoombright,zoomdark,zoomrotate,zoomgray;
    MyGraphicView graphicView;
    static float scaleX=1,scaleY=1,angle=0,color=1,satur=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button)findViewById(R.id.button01);

        btn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
                startActivity(intent);

            }
        });
        setTitle("3분 포토샵");

        LinearLayout pictureLayout=(LinearLayout)findViewById(R.id.pictureLayout);
        graphicView=(MyGraphicView)new MyGraphicView(this);
        pictureLayout.addView(graphicView);
        clickIcons();
    }

    public void clickIcons() {
        zoomin=(ImageButton)findViewById(R.id.zoomin);
        zoomin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaleX=scaleX+0.2f;
                scaleY=scaleY+0.2f;
                graphicView.invalidate();
            }
        });
        zoomout=(ImageButton)findViewById(R.id.zoomout);
        zoomout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaleX=scaleX-0.2f;
                scaleY=scaleY-0.2f;
                graphicView.invalidate();
            }
        });
        zoomrotate=(ImageButton)findViewById(R.id.rotate);
        zoomrotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                angle=angle+20;
                graphicView.invalidate();
            }
        });
        zoombright=(ImageButton)findViewById(R.id.zoombright);
        zoombright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color=color+0.2f;
                graphicView.invalidate();
            }
        });
        zoomdark=(ImageButton)findViewById(R.id.zoomdark);
        zoomdark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color=color-0.2f;
                graphicView.invalidate();
            }
        });
        zoomgray=(ImageButton)findViewById(R.id.zoomgray);
        zoomgray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(satur==0)satur=1;
                else satur=0;
                graphicView.invalidate();
            }
        });
    }


    private class MyGraphicView extends View {
        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint=new Paint();
            float[] array={color,0,0,0,0,
                    0,color,0,0,0,
                    0,0,color,0,0,
                    0,0,0,1,0};
            ColorMatrix cm=new ColorMatrix(array);
            if(satur==0)cm.setSaturation(satur);
            paint.setColorFilter(new ColorMatrixColorFilter(cm));

            Bitmap picture= BitmapFactory.decodeResource(getResources(),R.drawable.img01);
            int picX=(this.getWidth()-picture.getWidth())/2;
            int picY=(this.getHeight()-picture.getHeight())/2;
            int cenX=this.getWidth()/2;
            int cenY=this.getHeight()/2;
            canvas.scale(scaleX,scaleY,cenX,cenY);
            canvas.rotate(angle,cenX,cenY);
            canvas.drawBitmap(picture,picX,picY,paint);
            picture.recycle();
        }
    }

}
