package com.example.s2android5;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ImageView imageView;
    //We are going to use volley (third part library ) Objects Making them global
    RequestQueue requestQueue;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView);
        imageView=findViewById(R.id.imageView);
        requestQueue= Volley.newRequestQueue(this);
        editText=findViewById(R.id.editTextTextPersonName);



    }
    public void fetchString(View view){
        //We are going to use volley (third part library ) Objects
        String url="https://api.agify.io?name="+editText.getText().toString();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             textView.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             textView.setText(error.getLocalizedMessage());

            }
        });
        requestQueue.add(stringRequest);

    }
    public void fetchImage(View view){
        //We are going to use volley (third part library ) Objects
        String url="https://images.dog.ceo//breeds//mastiff-tibetan//n02108551_369.jpg";
        ImageRequest imageRequest=new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
            imageView.setImageBitmap(response);

            }
        }, 0,0, null, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

     requestQueue.add(imageRequest);
    }
}