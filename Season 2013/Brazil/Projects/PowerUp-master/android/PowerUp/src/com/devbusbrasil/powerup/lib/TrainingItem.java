package com.devbusbrasil.powerup.lib;

public class TrainingItem {
	
	private String mId;
	private String mName;
	private String mText;
	private String mImageUrl;
	private int mRepeate;
	private String mSerie;
	private double mWait;
	private double mWeight;
	private String mEquipment;
	private double mDuration;
	
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
	public String getText() {
		return mText;
	}
	public void setText(String pText) {
		this.mText = pText;
	}
	public String getImageUrl() {
		return mImageUrl;
	}
	public void setImageUrl(String pImageUrl) {
		this.mImageUrl = pImageUrl;
	}
	public int getRepeate() {
		return mRepeate;
	}
	public void setRepeate(int pRepeate) {
		this.mRepeate = pRepeate;
	}
	public String getSerie() {
		return mSerie;
	}
	public void setSerie(String pSerie) {
		this.mSerie = pSerie;
	}
	public double getWait() {
		return mWait;
	}
	public void setWait(double pWait) {
		this.mWait = pWait;
	}
	public double getWeight() {
		return mWeight;
	}
	public void setWeight(double pWeight) {
		this.mWeight = pWeight;
	}
	public String getEquipment() {
		return mEquipment;
	}
	public void setEquipment(String pEquipment) {
		this.mEquipment = pEquipment;
	}
	public double getDuration() {
		return mDuration;
	}
	public void setDuration(double pDuration) {
		this.mDuration = pDuration;
	}
	
	public TrainingItem() {
		
	}
	
	public TrainingItem(String pId, String pName, String pText, String pImageUrl, int pRepeate, String pSerie, double pWait, double pWeight, String pEquipment, double pDuration) {
		this.mId = pId;
		this.mName = pName;
		this.mText = pText;
		this.mImageUrl = pImageUrl;
		this.mRepeate = pRepeate;
		this.mSerie = pSerie;
		this.mWait = pWait;
		this.mWeight = pWeight;
		this.mEquipment = pEquipment;
		this.mDuration = pDuration;
	}

}
