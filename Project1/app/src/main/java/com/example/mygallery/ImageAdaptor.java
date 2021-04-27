package com.example.mygallery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import android.widget.ListAdapter;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageAdaptor extends BaseAdapter {
    private Context mContext;
    protected List<String> mThumbIds;
    protected PhotoListener photoListener;
    public ImageAdaptor(Context mContext, List<String> mThumbIds, PhotoListener photoListener) {
        this.mThumbIds = mThumbIds;
        this.mContext = mContext;
        this.photoListener = photoListener;
    }

    @Override
    public int getCount() {
        return mThumbIds.size();
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)  {
        ImageView imageView = (ImageView) convertView;

        if(imageView == null){
            String image = mThumbIds.get(position);
            imageView = new ImageView(mContext);
            Glide.with(mContext)
                    .load(mThumbIds.get(position))
                    .into(imageView);
            imageView.setLayoutParams(new GridView.LayoutParams(350,450));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    photoListener.onPhotoClick(image);
                }
            });
        }

        return imageView;
    }
    public interface PhotoListener{
        void onPhotoClick(String path);
    }
}
