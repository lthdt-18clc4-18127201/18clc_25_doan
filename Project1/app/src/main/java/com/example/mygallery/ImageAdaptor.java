package com.example.mygallery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageAdaptor extends BaseAdapter {
    public int[] mThumbIds = {R.drawable.mountain, R.drawable.khtn,
            R.drawable.download, R.drawable.i1, R.drawable.i2,
            R.drawable.i3, R.drawable.i4, R.drawable.i5, R.drawable.bf4, R.drawable.bouken, R.drawable.scene,
            R.drawable.wows1, R.drawable.wows2, R.drawable.mhw1, R.drawable.mhw2 , R.drawable.wows3};
    private Context mContext;

    public ImageAdaptor(Context mContext) {
        //this.mThumbIds = mThumbIds;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder
    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) convertView;

        if(imageView == null){
            imageView = new ImageView(mContext);
            imageView.setImageResource(mThumbIds[position]);
            imageView.setLayoutParams(new GridView.LayoutParams(350,450));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        return imageView;
    }
}
