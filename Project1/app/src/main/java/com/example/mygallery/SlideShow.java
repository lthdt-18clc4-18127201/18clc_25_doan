package com.example.mygallery;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.List;

public class SlideShow extends AppCompatActivity {
    ViewFlipper viewFlipper;
    static List<String> images;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_show);
        viewFlipper = findViewById(R.id.v_flipper);
        images = ImageGallery.listofallImages(this);
        images.addAll(ImageGallery.listofallVideo(this));
        for(int i = 0; i < images.size(); i++){
            slideshownIMG(images.get(i));
        }
    }
    public void slideshownIMG(String image){
        ImageView imageView = new ImageView(this);
        Glide.with(this)
                .load(image)
                .into(imageView);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);

    }
}
