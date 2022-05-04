package com.example.myloginapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
@Entity(tableName = "chanel")
public class Chanel implements Serializable {

    @PrimaryKey
    @NonNull
    @SerializedName("_id")
    private String Id;
    @SerializedName("title")
    private String Title;

    @SerializedName("status")
    private boolean status;

    @SerializedName("rate")
    private Integer rate;

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

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

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
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
