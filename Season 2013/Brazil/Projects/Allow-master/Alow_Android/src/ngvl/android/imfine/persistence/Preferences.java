package ngvl.android.imfine.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class Preferences {
	private static final String PREF_NAME = "Alow";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final String PROPERTY_GPLUS_USER_ID = "gplus_user_id";
	
	public static void storeRegistrationId(Context context, String regid) {
		final SharedPreferences prefs = getPreferences(context);
	    int appVersion = getAppVersion(context);

	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regid);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    editor.commit();
	}	
	
	private static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}
	
	public static void saveGPlusUserId(Context ctx, String userId){
		SharedPreferences pref = getPreferences(ctx);
		pref.edit().putString(PROPERTY_GPLUS_USER_ID, userId).commit();
	}
	
	public static String getGPlusUserId(Context ctx){
		SharedPreferences pref = getPreferences(ctx);
		return pref.getString(PROPERTY_GPLUS_USER_ID, "");
	}
	
	public static String getRegistrationId(Context context) {
		final SharedPreferences prefs = getPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			return "";
		}
		return registrationId;
	}
}
