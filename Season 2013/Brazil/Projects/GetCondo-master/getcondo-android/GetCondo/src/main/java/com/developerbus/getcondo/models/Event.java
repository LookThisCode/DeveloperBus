package com.developerbus.getcondo.models;
import com.google.gson.annotations.SerializedName;

/**
 * Created by bruno on 11/21/13.
 */
public class Event {

    @SerializedName("resident")
    private String mResidentName;

    @SerializedName("place")
    private String mPlace;

    @SerializedName("time")
    private String mTime;


    public String getResidentName() {
        return mResidentName;
    }

    public void setResidentName(String mResidentName) {
        this.mResidentName = mResidentName;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String mPlace) {
        this.mPlace = mPlace;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }
}
