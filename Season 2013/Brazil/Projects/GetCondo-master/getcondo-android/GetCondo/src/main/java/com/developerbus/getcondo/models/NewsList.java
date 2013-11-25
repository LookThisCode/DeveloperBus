package com.developerbus.getcondo.models;
import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * Created by bruno on 11/22/13.
 */
public class NewsList {

    @SerializedName("result")
    private List<News> mResult;

    public List<News> getResult() {
        return mResult;
    }

    public void setResult(List<News> mResult) {
        this.mResult = mResult;
    }
}
