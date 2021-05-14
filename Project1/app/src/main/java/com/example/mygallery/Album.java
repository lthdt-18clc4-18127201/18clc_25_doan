package com.example.mygallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class Album extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        Intent i = getIntent();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_album,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.i_add: {
                Intent i = getIntent();
                String position = i.getExtras().getString("id");
                Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,Image_detail. class);
                intent.putExtra("id",position);
                startActivity(intent);
            }return true;
            default:
                return super.onOptionsItemSelected(item);
        }
}}