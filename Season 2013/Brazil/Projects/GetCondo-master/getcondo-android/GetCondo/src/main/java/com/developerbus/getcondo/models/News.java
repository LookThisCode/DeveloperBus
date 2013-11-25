package com.developerbus.getcondo.models;
import com.google.gson.annotations.SerializedName;

/**
 * Created by bruno on 11/21/13.
 */
public class News {

    @SerializedName("title")
    private String mTitle;

    @SerializedName("description")
    private String mDescription;

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
