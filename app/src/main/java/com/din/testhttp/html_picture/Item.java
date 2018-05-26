package com.din.testhttp.html_picture;

/**
 * Created by dinzhenyan on 2018/4/1.
 */

public class Item {
    private String title;
    private String picture;

    public Item(String title, String picture) {
        this.title = title;
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
