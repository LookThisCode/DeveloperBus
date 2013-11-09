/**
 * File: MyAlarmService
 * CreationDate: 09/11/13
 * Author: "M. en C. Javier Silva Perez (JSP)"
 * Description: 
 *
 */
package com.devbus.sweethistory;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * @author "M. en C. Javier Silva Perez (JSP)"
 * @version 1.0
 * @since 09/11/13
 */
public class MyAlarmService extends Service {


	@Override

	public void onCreate() {
		Toast.makeText(this, "MyAlarmService.onCreate()", Toast.LENGTH_LONG).show();

	}


	@Override

	public IBinder onBind(Intent intent) {
		Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG).show();
		return null;

	}


	@Override

	public void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show();

	}

	@Override

	public boolean onUnbind(Intent intent) {
		Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG).show();
		return super.onUnbind(intent);
	}
}
