package com.example.mygallery;

import android.os.Bundle;

public class Wallpaper {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gallery galeria = (Gallery) findViewById(R.id.gallery);
        galeria.setAdapter(new ImageAdapter(this));
        galeria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ImageView laimagen = (ImageView) findViewById(wallpaper);
                laimagen.setImageResource(mFullSizeIds[position]);
            }
        });
    }
}
