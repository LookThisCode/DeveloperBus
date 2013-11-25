package com.developerbus.getcondo.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bruno on 11/22/13.
 */
public class EventsList {

    @SerializedName("result")
    private List<Event> mResult;

    public List<Event> getResult() {
        return mResult;
    }

    public void setResult(List<Event> mResult) {
        this.mResult = mResult;
    }
}
