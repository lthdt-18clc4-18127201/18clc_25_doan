package com.example.mygallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;

public class Image_detail extends AppCompatActivity {
    TextView ID, res, size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        Intent i = getIntent();
        String position = i.getExtras().getString("id");
        ID.findViewById(R.id.i_detail);
        ID.setText(position);
        String pickedImagePath = position;
        BitmapFactory.Options bitMapOption=new BitmapFactory.Options();
        bitMapOption.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(pickedImagePath, bitMapOption);
        int imageWidth=bitMapOption.outWidth;
        int imageHeight=bitMapOption.outHeight;
        res.findViewById(R.id.i_res);
        ID.setText(imageWidth + "x" + imageHeight);
    }
}