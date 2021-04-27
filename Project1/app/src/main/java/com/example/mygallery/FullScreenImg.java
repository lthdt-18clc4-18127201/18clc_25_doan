package com.example.mygallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.net.URLConnection;
import java.util.ArrayList;

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

        if ( position.toLowerCase().endsWith(".jpg")){
            videoView.setVisibility(View.GONE);
            ArrayList<String> List = (ArrayList<String>) getIntent().getSerializableExtra("list");
            Glide.with(this)
                    .load(position)
                    .into(imageView);
        }else if (position.toLowerCase().endsWith(".mp4") ) {
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
        }else if (position.toLowerCase().endsWith(".3gp") ) {
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
    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }
}