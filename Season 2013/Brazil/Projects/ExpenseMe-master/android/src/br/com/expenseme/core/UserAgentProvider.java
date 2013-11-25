package br.com.expenseme.core;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.telephony.TelephonyManager;


public class UserAgentProvider {
    protected ApplicationInfo appInfo;
    protected PackageInfo info;
    protected TelephonyManager telephonyManager;
    protected ClassLoader classLoader;

    protected String userAgent;

    private static final String APP_NAME = "ExpenseMe";

    public String get() {
        // if (userAgent == null) {
        // synchronized (UserAgentProvider.class) {
        // if (userAgent == null) {
        // userAgent = String.format("%s/%s (Android %s; %s %s / %s %s; %s)",
        // APP_NAME,
        // info.versionName,
        // Build.VERSION.RELEASE,
        // Strings.capitalize(Build.MANUFACTURER),
        // Strings.capitalize(Build.DEVICE),
        // Strings.capitalize(Build.BRAND),
        // Strings.capitalize(Build.MODEL),
        // Strings.capitalize(telephonyManager == null ? "not-found" : telephonyManager.getSimOperatorName())
        // );
        //
        // final ArrayList<String> params = new ArrayList<String>();
        // params.add("preload=" + ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1)); // Determine if this app was a preloaded app
        // params.add("locale=" + Locale.getDefault());
        //
        //
        // // http://stackoverflow.com/questions/2641111/where-is-android-os-systemproperties
        // try {
        // final Class SystemProperties = classLoader.loadClass("android.os.SystemProperties");
        // final Method get = SystemProperties.getMethod("get", String.class);
        // params.add("clientidbase=" + get.invoke(SystemProperties, "ro.com.google.clientidbase"));
        // } catch (Exception ignored) {
        // Ln.d(ignored);
        // }
        //
        //
        // if (params.size() > 0)
        // userAgent += "[" + Strings.join(";", params) + "]";
        //
        // }
        // }
        // }

        return userAgent;
    }
}
