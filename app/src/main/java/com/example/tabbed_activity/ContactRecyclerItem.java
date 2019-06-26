package com.example.tabbed_activity;

import android.graphics.drawable.Drawable;

public class ContactRecyclerItem {
    private Drawable iconDrawable ;
    private String nameStr ;
    private String phoneStr ;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setName(String name) {
        nameStr = name ;
    }
    public void setPhone(String desc) {
        phoneStr = desc ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getName() {
        return this.nameStr ;
    }
    public String getPhone() {
        return this.phoneStr ;
    }
}