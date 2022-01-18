package com.rbc.yelp.services.models;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("alias")
    private String alias;
    @SerializedName("title")
    private String title;

    public String getAlias() {
        return alias;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Category{" +
                "alias='" + alias + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
