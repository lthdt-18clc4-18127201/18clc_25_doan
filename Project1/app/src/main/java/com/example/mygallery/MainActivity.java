package com.example.mygallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Activity mActivity;
    GridView gridView;
    ImageAdaptor imageAdaptor;
    static TextView gallery_number;
    List<String> images;
    private static final int MY_READ_PERMISSION_CODE = 101;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    Button AlbumButton, SlideShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = MainActivity.this;

        gallery_number = findViewById(R.id.gallery_number);
        gridView = findViewById(R.id.mainView);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_READ_PERMISSION_CODE);
        }else{
            loadImages();
        }
        ImageView imageTakePhoto =findViewById(R.id.takePhotoButton);
        imageTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(getApplicationContext(),TakePhoto.class);
                    startActivity(cameraIntent);
                }
                restartActivity(mActivity);

            }
        });

        AlbumButton = findViewById(R.id.AlbumSelect);
        AlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AlbumMain.class);
                startActivity(intent);
            }
        });

        SlideShow = findViewById(R.id.SlideShow);
        SlideShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SlideShow.class);
                startActivity(intent);
            }
        });
    }

    public static void restartActivity(Activity activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate();
        } else {
            activity.finish();
            activity.startActivity(activity.getIntent());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.i_reset: {
                restartActivity(mActivity);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadImages(){

        images = ImageGallery.listofallImages(this);
        images.addAll(ImageGallery.listofallVideo(this));

        Intent intent = new Intent(getApplicationContext(), FullScreenImg.class);
        intent.putExtra("list", (Serializable) images);
        imageAdaptor = new ImageAdaptor(this, images, new ImageAdaptor.PhotoListener() {
            @Override
            public void onPhotoClick(String path) {
                //Toast.makeText(MainActivity.this, ""+path, Toast.LENGTH_SHORT).show();
                intent.putExtra("id",path);
                startActivity(intent);
            }
        });
        gridView.setAdapter(imageAdaptor);
        gallery_number.setText("Photos ("+images.size()+")");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_READ_PERMISSION_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Read external storage permission granted", Toast.LENGTH_SHORT).show();
                loadImages();
            }
            else {
                Toast.makeText(this, "Read external storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(getApplicationContext(),TakePhoto.class);

                startActivity(cameraIntent);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

}