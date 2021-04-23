package com.example.mygallery;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

public class ImageGallery {
    public static ArrayList<String> listofallVideo(Context context){
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listofallVideo = new ArrayList<>();
        String absolutePathofImages;

        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        String orderby = MediaStore.Video.Media.DATE_TAKEN;
        cursor = context.getContentResolver().query(uri,projection,null, null, orderby+" DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        //column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        while (cursor.moveToNext()){
            absolutePathofImages = cursor.getString(column_index_data);
            listofallVideo.add(absolutePathofImages);

        }
        return listofallVideo;
    }
    public static ArrayList<String> listofallImages(Context context){
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listofallImages = new ArrayList<>();
        String absolutePathofImages;

        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        String orderby = MediaStore.Video.Media.DATE_TAKEN;
        cursor = context.getContentResolver().query(uri,projection,null, null, orderby+" DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        //column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        while (cursor.moveToNext()){
            absolutePathofImages = cursor.getString(column_index_data);
            listofallImages.add(absolutePathofImages);

        }
        return listofallImages;
    }
}
