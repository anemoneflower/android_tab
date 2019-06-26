package com.example.tabbed_activity;

import android.graphics.drawable.Drawable;

public class AlbumRecyclerItem {
    private Drawable Photo ;
    private String descStr ;

    public void setPhoto(Drawable photo) {
        Photo = photo ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }

    public Drawable getPhoto() {
        return this.Photo;
    }
    public String getDesc() {
        return this.descStr ;
    }
}
