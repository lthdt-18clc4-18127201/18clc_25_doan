package com.example.mygallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.WallpaperManager;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FullScreenImg extends AppCompatActivity {

    ImageView imageView;
    VideoView videoView;
    MediaController mediaControls;
    Uri imageUri;
    String key;
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
            }return true;
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
            }return true;
        }
        switch (item.getItemId()) {
            case R.id.i_delete:
                Intent i = getIntent();
                String position = i.getExtras().getString("id");
                File file = new File(position);
                //  getContentResolver().delete(finalImageUri, null, null);
                //  getApplicationContext().deleteFile(finalImageUri.getPath());
                file.delete();
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" +  Environment.getExternalStorageDirectory())));
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("content://" +  Environment.getExternalStorageDirectory())));
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
                return true;
            case R.id.i_Share:
                Intent shareIntent = new Intent();//share
                shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                shareIntent.setAction(Intent.ACTION_SEND);
                Uri imageUri1 = FileProvider.getUriForFile(
                        this,
                        "com.example.mygallery", //(use your app signature + ".provider" )
                        new File(key));

                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri1);
                shareIntent.setType("image/*");
                grantUriPermission("android", imageUri1, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "Share File"));
                return true;
            /*case R.id.i_edit:
                Intent Intent = new Intent(getBaseContext(), img_edit.class);
                Intent.putExtra("key2", imageUri.toString());
                startActivityForResult(Intent, 0);
                return true;
            case R.id.i_copy:
                // Gets a handle to the clipboard service.
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);


                // Declares the Uri to paste to the clipboard
                Uri copyUri = Uri.parse(CONTACTS + COPY_PATH + "/" + imageUri);
                // Creates a new URI clip object. The system uses the anonymous getContentResolver() object to
                // get MIME types from provider. The clip object's label is "URI", and its data is
                // the Uri previously created.
                ClipData clip = ClipData.newUri(getContentResolver(), "URI", copyUri);
                // Set the clipboard's primary clip.
                clipboard.setPrimaryClip(clip);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}