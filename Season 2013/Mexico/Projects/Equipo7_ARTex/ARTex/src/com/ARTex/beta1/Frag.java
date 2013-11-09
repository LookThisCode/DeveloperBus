package com.ARTex.beta1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qualcomm.QCARUnityPlayer.QCARUnityPlayer;

public class Frag extends android.support.v4.app.Fragment{
	QCARUnityPlayer mPlayer;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		mPlayer = new QCARUnityPlayer(getActivity());
		int glesMode = mPlayer.getSettings().getInt("gles_mode", 1);
		mPlayer.init(glesMode, true);
		
		View view = mPlayer.getView();
		
		view.requestFocus();
		return view;
	}
	
	public void setPlay(boolean b){
		if(b)
			mPlayer.resume();
		else
			mPlayer.pause();
	}

}
