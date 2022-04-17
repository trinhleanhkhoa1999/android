package com.example.myloginapp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Chanel implements Serializable {

    @SerializedName("_id")
    private String Id;
    @SerializedName("title")
    private String Title;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCobtent() {
        return Content;
    }

    public void setCobtent(String cobtent) {
        Content = cobtent;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    @SerializedName("content")
    private String Content;

    @SerializedName("url")
    private String Url;

    @SerializedName("image")
    private String Image;

}
