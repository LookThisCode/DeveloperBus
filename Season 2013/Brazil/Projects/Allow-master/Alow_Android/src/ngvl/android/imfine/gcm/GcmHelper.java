package ngvl.android.imfine.gcm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import ngvl.android.imfine.R;
import ngvl.android.imfine.model.Message;
import ngvl.android.imfine.persistence.Preferences;
import ngvl.android.imfine.ui.ViewMessageActivity;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings.Secure;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class GcmHelper {

    public static final int NOTIFICATION_ID = 1;
    
	public static final String SENDER_ID = "799877891168";
//	private static final String BASE_URL = "http://10.0.2.2"; // Android Emulator
	private static final String BASE_URL = "http://192.168.56.1"; // Genymotion Emulator	
	
//	private static final String REGISTER_URL = BASE_URL	+ ":8080/C2DMWebServer/doRegister";
	private static final String REGISTER_URL = "http://alowdevbus.appspot.com/doRegister";
	
	public static final String EXTRA_MESSAGE = "message";
	
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	static final String TAG = "GCMDemo";

	GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	SharedPreferences prefs;
	String regid;    
    
    NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    Context context;
    
    public GcmHelper(Context ctx) {
    	context = ctx;
		gcm = GoogleCloudMessaging.getInstance(context);
		regid = Preferences.getRegistrationId(context);
	}
    
    public String getRegid() {
		return regid;
	}
    
    public void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
	        .setSmallIcon(R.drawable.logo_small)
	        .setContentTitle(context.getString(R.string.notification_title))
	        .setStyle(new NotificationCompat.BigTextStyle()
	        .bigText(msg))
	        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
    
    public void sendNotification(final Message msg) {
    	if (TextUtils.isEmpty(msg.photo)){
    		doSendNotification(msg, null);
    		
    	} else {
    		ImageLoader.getInstance().loadImage(msg.photo, new SimpleImageLoadingListener() {
    		    @Override
    		    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
    		    	doSendNotification(msg, loadedImage);
    		    }
    		});
    	}
    }    
    
    private void doSendNotification(Message msg, Bitmap bmp){
        mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        int requestCode = (int)(Math.random() * Integer.MAX_VALUE);
        
        Intent it = new Intent(context, ViewMessageActivity.class);
        it.putExtra("message", msg);
        it.putExtra("push", true);
        it.setData(Uri.parse("allow://" + requestCode));
        
        PendingIntent contentIntent = PendingIntent.getActivity(context, requestCode, it, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
	        .setSmallIcon(R.drawable.logo_small)
	        .setAutoCancel(true)
	        .setLargeIcon(bmp)
	        .setContentTitle(context.getString(R.string.notification_title))
	        .setContentText(context.getString(R.string.notification_message, msg.sender))
	        .setContentText(msg.message);

        mBuilder.setContentIntent(contentIntent);
        
        mNotificationManager.cancel(NOTIFICATION_ID);
        mNotificationManager.notify(requestCode, mBuilder.build());
    }
    
    public boolean checkPlayServices(Activity activity) {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(context);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Toast.makeText(activity, "This device is not supported.", Toast.LENGTH_SHORT).show();
				activity.finish();
			}
			return false;
		}
		return true;
	}
    
	public void registerInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regid = gcm.register(SENDER_ID);
					msg = "Device registered, registration ID=" + regid;

					sendRegistrationIdToBackend();

					Preferences.storeRegistrationId(context, regid);
					
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				Log.d("NGVL", msg);
//				Toast.makeText(context, msg + "\n", Toast.LENGTH_SHORT).show();
			}
			
		}.execute(null, null, null);
	}

	public void sendRegistrationIdToBackend() {
		final String deviceId = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(REGISTER_URL);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("deviceid", deviceId));
			nameValuePairs.add(new BasicNameValuePair("registrationid",	regid));
			nameValuePairs.add(new BasicNameValuePair("googleuserid", Preferences.getGPlusUserId(context)));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = client.execute(post, responseHandler);
			Log.d("NGVL", responseBody);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}	    
}
