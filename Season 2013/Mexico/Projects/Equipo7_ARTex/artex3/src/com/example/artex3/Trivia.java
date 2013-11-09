package com.example.artex3;

import com.google.android.gms.plus.PlusShare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;


public class Trivia extends Activity {
	/** Called when the activity is first created. */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trivia);
		Button btn1 = (Button) findViewById(R.id.button1);
		Button btn2 = (Button) findViewById(R.id.button2);
		Button btn3 = (Button) findViewById(R.id.button3);

		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog alertD = new AlertDialog.Builder(Trivia.this)
						.create();
				alertD.setTitle("Excelente!");
				alertD.setIcon(R.drawable.googleplus);
				alertD.setMessage("Comparte tu resultado");
				alertD.setButton2("Share g+",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								//Intent i = new Intent(getBaseContext(), Gplus.class);    
								//startActivity(i);
								Intent intent = new Intent();
								PendingIntent pIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);
								Notification noti = new Notification.Builder(getBaseContext())
								.setTicker("Artex Notificatión!")
								.setContentTitle("Compartir!")
								.setContentText("Contenido compartido.")
								.setSmallIcon(R.drawable.ic_launcher)
								.setContentIntent(pIntent).getNotification();
								noti.flags=Notification.FLAG_AUTO_CANCEL;
								NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
								notificationManager.notify(0, noti); 
								
														
							}
						});
				alertD.show();

			}
		});
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog alertD = new AlertDialog.Builder(Trivia.this)
						.create();
				alertD.setTitle("Excelente!");
				alertD.setIcon(R.drawable.googleplus);
				alertD.setMessage("Comparte tu resultado");
				alertD.setButton2("Share g+",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								//Intent i = new Intent(getBaseContext(), Gplus.class);    
								//startActivity(i);
								Intent intent = new Intent();
								PendingIntent pIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);
								Notification noti = new Notification.Builder(getBaseContext())
								.setTicker("Artex Notificatión!")
								.setContentTitle("Compartir!")
								.setContentText("Contenido compartido.")
								.setSmallIcon(R.drawable.ic_launcher)
								.setContentIntent(pIntent).getNotification();
								noti.flags=Notification.FLAG_AUTO_CANCEL;
								NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
								notificationManager.notify(0, noti); 
								
														
							}
						});
				alertD.show();

			}
		});
		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog alertD = new AlertDialog.Builder(Trivia.this)
						.create();
				alertD.setTitle("Excelente!");
				alertD.setIcon(R.drawable.googleplus);
				alertD.setMessage("Comparte tu resultado");
				alertD.setButton2("Share g+",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								//Intent i = new Intent(getBaseContext(), Gplus.class);    
								//startActivity(i);
								Intent intent = new Intent();
								PendingIntent pIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);
								Notification noti = new Notification.Builder(getBaseContext())
								.setTicker("Artex Notificatión!")
								.setContentTitle("Compartir!")
								.setContentText("Contenido compartido.")
								.setSmallIcon(R.drawable.ic_launcher)
								.setContentIntent(pIntent).getNotification();
								noti.flags=Notification.FLAG_AUTO_CANCEL;
								NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
								notificationManager.notify(0, noti); 
								
														
							}
						});
				alertD.show();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
