package com.developerbus.getcondo.models;
import com.google.gson.annotations.SerializedName;

/**
 * Created by bruno on 11/21/13.
 */
public class Session {

    @SerializedName("token")
    private String mToken;

    @SerializedName("name")
    private String mName;

    @SerializedName("condo_id")
    private String mCondoId;

    @SerializedName("email")
    private String mEmail;



    @SerializedName("apartment")
    private String mApartment;

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getCondoId() {
        return mCondoId;
    }

    public void setCondoId(String mCondoId) {
        this.mCondoId = mCondoId;
    }

    public String getApartment() {
        return mApartment;
    }

    public void setApartment(String mApartment) {
        this.mApartment = mApartment;
    }




}
