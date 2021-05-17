package com.example.mygallery;

import java.util.List;

public class Album {
    private String name;
    private int numOfItems;
    private int thumbnail;
    private List<String> albumImages;
    public Album(String name, int numOfItems, int thumbnail) {
        this.name = name;
        this.numOfItems = numOfItems;
        this.thumbnail= thumbnail;
    }
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public int getNumOfItems(){return numOfItems;}
    public void setNumOfItems(int numOfItems){this.numOfItems = numOfItems;}
    public int getThumbnail(){return thumbnail;}
    public void setThumbnail(int thumbnail){this.thumbnail = thumbnail;}


}
