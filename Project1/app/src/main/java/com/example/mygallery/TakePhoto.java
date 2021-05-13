package com.example.mygallery;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

public class TakePhoto extends Activity {
    private String mCurrentPhotoPath;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//capture imgage
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                BuildConfig.APPLICATION_ID + ".FileProvider", photoFile);*/
        Uri imageUri = FileProvider.getUriForFile(
                this,
                "com.example.mygallery.FileProvider",
                photoFile);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, 1);
    }
    @Override
    protected  void onDestroy() {
        super.onDestroy();
        finish();

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpeg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode==1){
            // Bundle e = intent.getExtras();

            Bitmap e1 = null;
            try {
                e1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            String b=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String photoUriStr= MediaStore.Images.Media.insertImage(getContentResolver(),e1,"IMG_" + b ,null);

            try {
                InputStream in = null;
                in = getContentResolver().openInputStream(Uri.parse(photoUriStr));
                ExifInterface  exifInterface =new ExifInterface(in);
                exifInterface.setAttribute(ExifInterface.TAG_DATETIME,"2013:06:21 00:00:07");
                exifInterface.saveAttributes();
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" +  Environment.getExternalStorageDirectory())));
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("content://" +  Environment.getExternalStorageDirectory())));
            } catch (IOException e) {
                e.printStackTrace();
            }

            finish();

        }}
}
