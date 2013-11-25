package com.developerbus.getcondo.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bruno on 11/22/13.
 */
public class IssuesList {

    @SerializedName("result")
    private List<Issue> mResult;

    public List<Issue> getResult() {
        return mResult;
    }

    public void setResult(List<Issue> mResult) {
        this.mResult = mResult;
    }
}
