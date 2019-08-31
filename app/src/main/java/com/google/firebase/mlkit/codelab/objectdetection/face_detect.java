package com.google.firebase.mlkit.codelab.objectdetection;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class face_detect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_detect);
        Button btn = (Button) findViewById(R.id.button);
        Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(face_detect.this, MainActivity.class);
                startActivity(i);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView myImageView = (ImageView) findViewById(R.id.imgview);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inMutable=true;
                Bitmap myBitmap = BitmapFactory.decodeResource(
                    //Defining the input image #Test2.jpg
                    getApplicationContext().getResources(),
                    R.drawable.test2,
                    options
                );

                //Defining the detecting squares and their attributes
                Paint myRectPaint = new Paint();
                myRectPaint.setStrokeWidth(5);
                myRectPaint.setColor(Color.RED);
                myRectPaint.setStyle(Paint.Style.STROKE);

                //Creating a canvas to draw on
                Bitmap tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);
                Canvas tempCanvas = new Canvas(tempBitmap);
                tempCanvas.drawBitmap(myBitmap, 0, 0, null);

                //Main Face Detector Code
                FaceDetector faceDetector = new
                        //setTrackingEnabled=False because we are here taking from a static image, not moving one
                        FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false)
                        .build();
                //In Case of failure
                if(!faceDetector.isOperational()){
                    new AlertDialog.Builder(v.getContext()).setMessage("Could not set up the face detector!").show();
                    return;
                }

                //Detect Faces
                Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
                SparseArray<Face> faces = faceDetector.detect(frame);

                //Draw Rectangles on the Faces
                for(int i=0; i<faces.size(); i++) {
                    Face thisFace = faces.valueAt(i);
                    float x1 = thisFace.getPosition().x;
                    float y1 = thisFace.getPosition().y;
                    float x2 = x1 + thisFace.getWidth();
                    float y2 = y1 + thisFace.getHeight();
                    tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 2, 2, myRectPaint);
                }
                myImageView.setImageDrawable(new BitmapDrawable(getResources(),tempBitmap));


            }
        });
    }
}
