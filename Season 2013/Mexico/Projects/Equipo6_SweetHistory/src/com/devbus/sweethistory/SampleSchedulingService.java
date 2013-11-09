package com.devbus.sweethistory;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * This {@code IntentService} does the app's actual work.
 * {@code SampleAlarmReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class SampleSchedulingService extends IntentService {
	public SampleSchedulingService() {
		super("SchedulingService");
	}

	// An ID used to post the notification.
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;

	@Override
	protected void onHandleIntent(Intent intent) {

		String message = intent.getStringExtra(SampleAlarmReceiver.EXTRA_MESSAGE);
		sendNotification(message);

		// Release the wake lock provided by the BroadcastReceiver.
		SampleAlarmReceiver.completeWakefulIntent(intent);
		// END_INCLUDE(service_onhandle)
	}

	// Post a notification indicating whether a doodle was found.
	private void sendNotification(String msg) {
		mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_launcher)
			.setContentTitle(getString(R.string.notificationTitle))
			.setStyle(new NotificationCompat.BigTextStyle().bigText(msg)).setContentText(msg);

		Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(1000);

		Uri notificationRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

		mBuilder.setContentIntent(contentIntent);
		Notification notification =  mBuilder.build();
		notification.sound = notificationRingtone;
		notification.audioStreamType = AudioManager.STREAM_ALARM;


		mNotificationManager.notify(NOTIFICATION_ID, notification);
	}
}
