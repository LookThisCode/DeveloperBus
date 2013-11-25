package com.devbusbrasil.powerup.lib;

public class QRCode {
	
	private static QRCode mInstance;
	public static QRCode GetInstance() {
		if (mInstance == null) {
			mInstance = new QRCode();
		}
		return mInstance;
	}
	
	private String mCode;

	public String getCode() {
		return mCode;
	}
	public void setCode(String pCode) {
		this.mCode = pCode;
	}
	
	public TrainingItem getTrainingItem() {
		return Training.GetInstance().findTrainingItem(mCode);
	}

	public QRCode() {
		
	}

}
