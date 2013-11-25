package ngvl.android.imfine.gcm;

import ngvl.android.imfine.model.Message;
import ngvl.android.imfine.persistence.MessageProvider;
import ngvl.android.imfine.persistence.MessagesDB;
import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {
	private static final String TAG = "GCMIntentService";
	
	GcmHelper helper;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    	helper = new GcmHelper(getApplicationContext());
    	
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  

        	if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                helper.sendNotification("Send error: " + extras.toString());
                
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
                helper.sendNotification("Deleted messages on server: " +
                        extras.toString());

            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
            	
                Message message = new Message();
                message.sender = extras.getString("sender");
                message.message = extras.getString("message");
                message.time = Long.valueOf(extras.getString("time"));
                message.photo = extras.getString("photo");
                message.read = false;
                
                helper.sendNotification(message);
                
                ContentValues cv = MessagesDB.contentValuesByMessage(message);
                
                Uri uri = getContentResolver().insert(MessageProvider.CONTENT_URI, cv);
                long id = Long.parseLong(uri.getLastPathSegment());
                message.id = id;
                
                Log.i(TAG, "Received: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }


}