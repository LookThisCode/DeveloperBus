package com.devbusbrasil.powerup.lib;

import com.google.android.gms.plus.PlusClient;

public class User {
	
	private static User mInstance;
	public static User GetInstance() {
		if (mInstance == null) {
			mInstance = new User();
		}
		return mInstance;
	}
	
	private String mId;
	private String mName;
	private String mImageUrl;
	private PlusClient mPlusClient;
	private String mLevel;
	
	public String getId() {
		return mId;
	}
	public void setId(String pId) {
		this.mId = pId;
	}
	public String getName() {
		return mName;
	}
	public void setName(String pName) {
		this.mName = pName;
	}
	public String getImageUrl() {
		return mImageUrl;
	}
	public void setImageUrl(String pImageUrl) {
		this.mImageUrl = pImageUrl;
	}
	public PlusClient getPlusClient() {
		return mPlusClient;
	}
	public void setPlusClient(PlusClient pPlusClient) {
		this.mPlusClient = pPlusClient;
	}
	public String getLevel() {
		return mLevel;
	}
	public void setLevel(String pLevel) {
		this.mLevel = pLevel;
	}
	
	public User() {
		
	}

}
