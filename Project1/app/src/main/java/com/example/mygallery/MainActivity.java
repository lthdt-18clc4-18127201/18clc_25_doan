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
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

    GridView gridView;
    ImageAdaptor imageAdaptor;
    static TextView gallery_number;
    ArrayList<String> images;
    private static final int MY_READ_PERMISSION_CODE = 101;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    Button AlbumButton, SlideShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gallery_number = findViewById(R.id.gallery_number);
        gridView = findViewById(R.id.mainView);

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
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                    File filepath = Environment.getExternalStorageDirectory();
                    File imagesFolder = new File(filepath.getAbsolutePath()+ "MyAppImages");
                    imagesFolder.mkdirs();
                    File image = new File(imagesFolder, System.currentTimeMillis()+".jpg");
                    Uri uriSavedImage=FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID, image);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        AlbumButton = findViewById(R.id.AlbumSelect);
        AlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Album.class);
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

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_READ_PERMISSION_CODE);
        }else{
            loadImages();
        }

    }


    private void loadImages(){

        if(images ==null){
        images = ImageGallery.listofallImages(this);
        images.addAll(ImageGallery.listofallVideo(this));}


        Intent intent = new Intent(getApplicationContext(), FullScreenImg.class);
        intent.putExtra("list", (Serializable) images);
        imageAdaptor = new ImageAdaptor(this, images, new ImageAdaptor.PhotoListener() {
            @Override
            public void onPhotoClick(String path) {
                Toast.makeText(MainActivity.this, ""+path, Toast.LENGTH_SHORT).show();
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
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                File filepath = Environment.getExternalStorageDirectory();
                File imagesFolder = new File(filepath.getAbsolutePath()+ "MyAppImages");
                imagesFolder.mkdirs();
                File image = new File(imagesFolder, System.currentTimeMillis()+".jpg");
                Uri uriSavedImage=FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID, image);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }

}