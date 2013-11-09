package mx.developerbus.foodbus.gplus;

import java.util.List;

import mx.developerbus.foodbus.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.PlusClient.OnPeopleLoadedListener;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.Person.PlacesLived;
import com.google.android.gms.plus.model.people.PersonBuffer;

public class UserGPlus 
{

	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
	
	private PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;
	private String userGmail;
	private String name;
	private String avatarUrl;
	private String [] urlsAvatarsFriends;
	private String about;
	private String city;
	private Person user;
	private Activity activity;
	private boolean available;
	
	private ProgressDialog dialog;
	private Intent intent;
	
	
	public UserGPlus(Activity activity)
	{
		this.activity = activity;
		this.available = false;
		mPlusClient = new PlusClient.Builder(activity, generatedConnectionCallbacks(), generatedConnectionFailed()).
				setActions("http://schemas.google.com/AddActivity", "http://schemas.google.com/BuyActivity").build();
		
	}
	
	public boolean start(Intent intent)
	{
		dialog = new ProgressDialog(activity);
		dialog.setMessage("Signing in ....");
		dialog.setProgress(0);
        dialog.setMax(100);
        dialog.setCancelable(false);
		dialog.show();
		this.intent = intent;
		if (!mPlusClient.isConnected()) 
		{
			Log.i("plus","start");
			mPlusClient.connect();
		}
		return true;
	}

	private void loadValues()
	{
//		if(mPlusClient.isConnected())
//		{
			Log.i("plus","cargandoValues");
			userGmail = mPlusClient.getAccountName();
			user = mPlusClient.getCurrentPerson();
			name = user.getDisplayName();
			avatarUrl = user.getImage().getUrl();
			Log.i("plus","urlAvatar: "+avatarUrl);
			Log.i("plus",name +" name");
			mPlusClient.loadVisiblePeople(new OnPeopleLoadedListener() 
			{
				@Override
				public void onPeopleLoaded(ConnectionResult status,PersonBuffer personBuffer, String nextPageToken) 
				{
					if(status.getErrorCode() == ConnectionResult.SUCCESS)
					{
						urlsAvatarsFriends = new String[personBuffer.getCount()];
						for(int f=0;f<personBuffer.getCount();f++)
						{
							urlsAvatarsFriends[f] = personBuffer.get(f).getImage().getUrl();
						}
					}
				}
			}, null);
			about = user.getAboutMe();
			List<PlacesLived> list = user.getPlacesLived();
			if(list != null)
				city = list.get(0).getValue();
//		}
	}
	
	private ConnectionCallbacks generatedConnectionCallbacks()
	{
		ConnectionCallbacks callBacks = new ConnectionCallbacks() 
		{
			@Override
			public void onDisconnected() {
			}
			
			@Override
			public void onConnected(Bundle connectionHint) {
				Log.i("plus","conectando");
				loadValues();
				setAvailable(true);
				if(dialog.isShowing())
				{
					dialog.dismiss();
					if(intent!=null)
						activity.startActivity(intent);
				}
			}
		};
		return callBacks;
	}
	
	private OnConnectionFailedListener generatedConnectionFailed()
	{
		OnConnectionFailedListener failedListener = new OnConnectionFailedListener() {
			
			@Override
			public void onConnectionFailed(ConnectionResult result) {
				 if (result.hasResolution()) 
				 {
					 try {
						 result.startResolutionForResult(activity, REQUEST_CODE_RESOLVE_ERR);
						 Log.i("plus","try");
					 } catch (SendIntentException e) {
						 mPlusClient.connect();
						 Log.i("plus","catch");
					 }
				 }
				 loadValues();
				 mConnectionResult = result;
				 if(dialog.isShowing())
				 {
					 dialog.dismiss();
					 if(intent!=null)
							activity.startActivity(intent);
				 }
			}
		};
		return failedListener;
	}
	
	public void setPost(String msg)
	{
		String action = "/?view=true";
        Uri callToActionUrl = Uri.parse(activity.getString(R.string.plus_example_deep_link_url) + action);
        String callToActionDeepLinkId = activity.getString(R.string.plus_example_deep_link_id) + action;
        // Create an interactive post builder.
        PlusShare.Builder builder = new PlusShare.Builder(activity, mPlusClient);
        // Set call-to-action metadata.
        builder.addCallToAction("VIEW_ITEM", callToActionUrl, callToActionDeepLinkId);
        // Set the target url (for desktop use).
        builder.setContentUrl(Uri.parse(activity.getString(R.string.plus_example_deep_link_url)));
        // Set the target deep-link ID (for mobile use).
        builder.setContentDeepLinkId(activity.getString(R.string.plus_example_deep_link_id),
                null, null, null);
        // Set the pre-filled message.
        builder.setText(msg);
        activity.startActivityForResult(builder.getIntent(),0);
    }
	
	public void setItemMenu(String description, String price, Uri uriFoto, String sFoto)
	{
		PlusShare.Builder share = new PlusShare.Builder(activity, mPlusClient);
	      share.setText("#Producto "+description+"\n#Price "+price);
	      share.addStream(uriFoto);
	      share.setType(sFoto);
	      activity.startActivityForResult(share.getIntent(), 3);
	}
	
	
	
	public PlusClient getmPlusClient() {
		return mPlusClient;
	}

	public ConnectionResult getmConnectionResult() {
		return mConnectionResult;
	}

	public String getUserGmail() {
		
		return userGmail;
	}

	public String getAvatarUrl() 
	{
		return avatarUrl;
	}
	
	public String getName() {
		Log.i("plus","getName: "+name);
		return name;
	}

	public String[] getUrlsAvatarsFriends() {
		return urlsAvatarsFriends;
	}

	public String getAbout() {
		return about;
	}

	public String getCity() {
		Log.i("plus","get: "+city);
		return city;
	}
	
	public synchronized boolean isAvailable() {
		return available;
	}

	private synchronized void setAvailable(boolean available) {
		this.available = available;
	}
}

