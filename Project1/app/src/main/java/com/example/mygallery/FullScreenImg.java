package com.example.mygallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
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

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class FullScreenImg extends AppCompatActivity {

    ImageView imageView;
    VideoView videoView;
    MediaController mediaControls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_img);

        imageView = findViewById(R.id.imageView);
        videoView = findViewById(R.id.videoView);
        Intent i = getIntent();
        String position = i.getExtras().getString("id");

        if ( position.toLowerCase().endsWith(".jpg") || position.toLowerCase().endsWith(".png") || position.toLowerCase().endsWith(".jpeg")) {
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
                Intent intent = new Intent(this,Image_detail. class);
                Intent i = getIntent();
                String position = i.getExtras().getString("id");
                intent.putExtra("id",position);

                Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}