package com.example.mygallery;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class    ImageGallery {
    public static List<String> listofallVideo(Context context){
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        List<String> listofallVideo = new ArrayList<>();
        String absolutePathofVideo;

        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        String orderby = MediaStore.Video.Media.DATE_TAKEN;
        cursor = context.getContentResolver().query(uri,projection,null, null, orderby+" DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        //column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        while (cursor.moveToNext()){
            absolutePathofVideo = cursor.getString(column_index_data);
            listofallVideo.add(absolutePathofVideo);

        }
        return listofallVideo;
    }
    public static List<String> listofallImages(Context context){
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        List<String> listofallImages = new ArrayList<>();
        String absolutePathofImages;

        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        String orderby = MediaStore.Images.Media.DATE_TAKEN;
        cursor = context.getContentResolver().query(uri,projection,null, null, orderby+" DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        while (cursor.moveToNext()){
            absolutePathofImages = cursor.getString(column_index_data);
            listofallImages.add(absolutePathofImages);

        }
        return listofallImages;
    }
}
