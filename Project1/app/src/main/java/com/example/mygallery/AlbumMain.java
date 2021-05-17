package com.example.mygallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AlbumMain extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlbumAdapter albumAdapter;
    private List<Album> albumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initCollapsingToolBar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        albumList = new ArrayList<>();
        albumAdapter = new AlbumAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,dpToPx(10),true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(albumAdapter);

        prepareAlbums();

        try {
            Glide.with(this).load(R.drawable.bf4).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prepareAlbums() {
        Album a = new Album("Main Album", 13, R.drawable.bouken);
        albumList.add(a);
        albumAdapter.notifyDataSetChanged();
    }

    private void initCollapsingToolBar() {
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1)
                    scrollRange = appBarLayout.getTotalScrollRange();
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Cardview");
                    isShow = true;
                }else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration{
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge){
            this.spanCount = spanCount;
            this. spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets (Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    private int dpToPx(int dp){
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    /*File dir = new File(path, "/appname/media/app images/");
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }

        File file = new File(dir, filename + ".jpg");
        String imagePath =  file.getAbsolutePath();
        //scan the image so show up in album
        MediaScannerConnection.scanFile(this,
                new String[] { imagePath }, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        if(Config.LOG_DEBUG_ENABLED) {
                            Log.d(Config.LOGTAG, "scanned : " + path);
                        }
                    }
                });
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_album,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.i_add: {
                Intent intent = new Intent(this,AfterClickActivity.class);
                startActivity(intent);
            }return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}
