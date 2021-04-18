package com.example.mygallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class FullScreenImg extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_img);

        imageView = findViewById(R.id.imageView);

        Intent i = getIntent();
        int position = i.getExtras().getInt("id");
        ImageAdaptor imageAdaptor = new ImageAdaptor(this);
        imageView.setImageResource(imageAdaptor.mThumbIds[position]);

    }
}