package com.developerbus.getcondo.models;
import com.google.gson.annotations.SerializedName;

/**
 * Created by bruno on 11/21/13.
 */
public class Issue {

    @SerializedName("author")
    private String mAuthorName;

    @SerializedName("author_apartment")
    private String mAuthorApartment;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("picture_url")
    private String mPictureUrl;

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getAuthorName() {
        return mAuthorName;
    }

    public void setAuthorName(String mAuthorName) {
        this.mAuthorName = mAuthorName;
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }

    public void setPictureUrl(String mPictureUrl) {
        this.mPictureUrl = mPictureUrl;
    }

    public String getAuthorApartment() {
        return mAuthorApartment;
    }

    public void setAuthorApartment(String mAuthorApartment) {
        this.mAuthorApartment = mAuthorApartment;
    }

}
