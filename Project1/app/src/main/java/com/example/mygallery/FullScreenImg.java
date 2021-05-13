package com.example.mygallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class FullScreenImg extends AppCompatActivity {

    ImageView imageView;
    VideoView videoView;
    MediaController mediaControls;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_img);

        imageView = findViewById(R.id.imageView);
        videoView = findViewById(R.id.videoView);
        Intent i = getIntent();
        String position = i.getExtras().getString("id");

        String action = i.getAction();
        String type = i.getType();


        if (Intent.ACTION_VIEW.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                videoView.setVisibility(View.GONE);
                imageUri = i.getData();//cái này là uri khi m mở default //get intent từ thư mục hệ thống
                Glide.with(this)
                        .load(imageUri)
                        .into(imageView);
            }
        }else if ( position.toLowerCase().endsWith(".jpg") || position.toLowerCase().endsWith(".png") || position.toLowerCase().endsWith(".jpeg")) {
            videoView.setVisibility(View.GONE);
            List<String> List = (List<String>) getIntent().getSerializableExtra("list");
            Glide.with(this)
                    .load(position)
                    .into(imageView);
        }else if (position.toLowerCase().endsWith(".mp4") || position.toLowerCase().endsWith(".3gp")) {
            if (mediaControls == null) {
                imageView.setVisibility(View.GONE);
                // create an object of media controller class
                mediaControls = new MediaController(this);
                mediaControls.setAnchorView(videoView);

                // set the media controller for video view
                videoView.setMediaController(mediaControls);
                // set the uri for the video view
                videoView.setVideoURI(Uri.parse(position));
                // start a video
                videoView.start();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.i_detail: {
                Intent i = getIntent();
                String position = i.getExtras().getString("id");
                Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,Image_detail. class);
                intent.putExtra("id",position);
                startActivity(intent);
            }
        }
        switch (item.getItemId()) {
            case R.id.i_wallpaper: {
                Intent i = getIntent();
                String position = i.getExtras().getString("id");
                Glide.with(this)
                        .load(position)
                        .into(imageView);
                Bitmap bmpImg = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                WallpaperManager wallManager = WallpaperManager.getInstance(getApplicationContext());
                try {
                    wallManager.setBitmap(bmpImg);
                    Toast.makeText(this, "Wallpaper Set Successfully!!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(this, "Setting WallPaper Failed!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        switch (item.getItemId()) {
            case R.id.i_edit: {
                Intent i = getIntent();
                String position = i.getExtras().getString("id");
                Glide.with(this)
                        .load(position)
                        .into(imageView);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}