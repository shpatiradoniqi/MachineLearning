package com.example.s2android5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {
  ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView=findViewById(R.id.imageView2);


    }
    public void showMessage(String title,String message){
        new AlertDialog.Builder(this).setTitle(title).setMessage(message).setCancelable(true).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123 && resultCode==Activity.RESULT_OK){
            Bundle extra=data.getExtras();
            Bitmap bitmap=(Bitmap) extra.get("data");
            imageView.setImageBitmap(bitmap);
            detectFace(bitmap);



        }
    }
    public void detectFace(Bitmap bitmap){
        //first step
        FaceDetectorOptions highAccOpts=
                new FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .build();


        //Second step create a detector
        FaceDetector detector= FaceDetection.getClient(highAccOpts);
        //third step an appropriate image
        InputImage image=InputImage.fromBitmap(bitmap,270);

        //Fourth Step Create task that returns your image rec results
        Task<List<Face>> result=detector.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<Face>>() {
                    @Override
                    public void onSuccess(@NonNull List<Face> faces) {
                        showMessage("Number of faces found:", String.valueOf(faces.size()));
                        for (Face face : faces) {
                            if (face.getSmilingProbability() != null) {
                                float smiling = face.getSmilingProbability();
                                showMessage("Smiling", String.valueOf(smiling));

                            }

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull  Exception e) {


                    }
                });
    }

    public void getImage(View view){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,123);


    }
}