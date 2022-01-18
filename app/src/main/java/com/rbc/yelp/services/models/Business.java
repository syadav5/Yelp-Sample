package com.rbc.yelp.services.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Business model from the Yelp v3 API.
 * Update this file to include any fields you feel are missing.
 * @see <a href=https://www.yelp.ca/developers/documentation/v3/business_search>Yelp API Business Search</a>
 */
 public class Business {
    @SerializedName("id")
     String id;
    @SerializedName("name")
     String name;
    @SerializedName("categories")
    private List<Category> categories;
    @SerializedName("rating")
    private Double rating;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("review_count")
    private int ratingCount;
    @SerializedName("is_closed")
    private boolean isClosed;
    @SerializedName("phone")
    private String phone;
    @SerializedName("display_phone")
    private String displayPhone;
    @SerializedName("location")
    private Location location;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public boolean isClosed() {
        return isClosed;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public Double getRating() { return rating; }

    public String getImageUrl() { return imageUrl; }

    public int getRatingCount() {
        return ratingCount;
    }

    public String getPhone() {
        return phone;
    }

    public String isDisplayPhone() {
        return displayPhone;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Business{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", categories=" + categories +
                ", rating=" + rating +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
