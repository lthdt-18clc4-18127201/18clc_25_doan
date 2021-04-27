package com.example.mygallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<Integer> impImg =  new ArrayList<>(Arrays.asList(R.drawable.mountain, R.drawable.khtn,
            R.drawable.download, R.drawable.i1, R.drawable.i2,
            R.drawable.i3, R.drawable.i4, R.drawable.i5, R.drawable.bf4, R.drawable.bouken, R.drawable.scene,
            R.drawable.wows1, R.drawable.wows2, R.drawable.mhw1, R.drawable.mhw2 , R.drawable.wows3));
    GridView gridView;
    ImageAdaptor imageAdaptor;
    TextView gallery_number;
    List<String> images;
    private static final int MY_READ_PERMISSION_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gallery_number = findViewById(R.id.gallery_number);
        gridView = findViewById(R.id.mainView);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_READ_PERMISSION_CODE);
        }else{
            loadImages();
        }
    }
    private void loadImages(){

        images = ImageGallery.listofallImages(this);
        images.addAll(ImageGallery.listofallVideo(this));


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
    }
}