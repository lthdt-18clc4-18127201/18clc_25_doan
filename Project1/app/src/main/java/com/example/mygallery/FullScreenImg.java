package com.example.mygallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.WallpaperManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FullScreenImg extends AppCompatActivity {

    private static final Object CONTACTS = "content://com.example.contacts";
    private static final String COPY_PATH = "/copy";
    ImageView imageView;
    VideoView videoView;
    MediaController mediaControls;
    Uri imageUri;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_img);
        String position = null;
        imageView = findViewById(R.id.imageView);
        videoView = findViewById(R.id.videoView);
        Intent i = getIntent();
        position = i.getExtras().getString("id");

        String action = i.getAction();
        String type = i.getType();


        if (Intent.ACTION_VIEW.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                videoView.setVisibility(View.GONE);
                imageUri = i.getData();
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
                Uri imageUri = FileProvider.getUriForFile(
                        this,
                        "com.example.mygallery.FileProvider",
                        file);
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                deleteFile(file.toString());
                finish();
                return true;
        }
        switch (item.getItemId()) {
            case R.id.i_Share:
                Intent i = getIntent();
                String position = i.getExtras().getString("id");
                Glide.with(this)
                        .load(position)
                        .into(imageView);
                Bitmap icon = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                File f = new File(position);
                try {
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri imageUri = FileProvider.getUriForFile(
                        this,
                        "com.example.mygallery.FileProvider",
                        f);
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                this.grantUriPermission("android",imageUri,Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivity(Intent.createChooser(share, "Share Image"));
                return true;
            /*case R.id.i_edit:
                Intent Intent = new Intent(getBaseContext(), img_edit.class);
                Intent.putExtra("key2", imageUri.toString());
                startActivityForResult(Intent, 0);
                return true;*/}
        switch (item.getItemId()) {
            case R.id.i_copy:
                Intent i = getIntent();
                String position = i.getExtras().getString("id");
                Glide.with(this)
                        .load(position)
                        .into(imageView);
                Bitmap icon = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                File f = new File(position);
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                Uri copyUri = Uri.parse(CONTACTS + COPY_PATH + "/" + position);

                ClipData clip = ClipData.newUri(getContentResolver(), "URI", copyUri);
                clipboard.setPrimaryClip(clip);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}