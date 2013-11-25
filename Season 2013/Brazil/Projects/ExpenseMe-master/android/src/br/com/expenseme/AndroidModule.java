package br.com.expenseme;

import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;


/**
 * Module for all Android related provisions
 */

public class AndroidModule {

    Context provideAppContext()
    {
        return ExpensemeApplication.getInstance().getApplicationContext();
    }

    SharedPreferences provideDefaultSharedPreferences(final Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    PackageInfo providePackageInfo(Context context)
    {
        try
        {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    TelephonyManager provideTelephonyManager(Context context)
    {
        return getSystemService(context, Context.TELEPHONY_SERVICE);
    }

    @SuppressWarnings("unchecked")
    public <T> T getSystemService(Context context, String serviceConstant)
    {
        return (T)context.getSystemService(serviceConstant);
    }

    InputMethodManager provideInputMethodManager(final Context context)
    {
        return (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    ApplicationInfo provideApplicationInfo(final Context context) {
        return context.getApplicationInfo();
    }

    AccountManager provideAccountManager(final Context context) {
        return AccountManager.get(context);
    }

    ClassLoader provideClassLoader(final Context context) {
        return context.getClassLoader();
    }

    NotificationManager provideNotificationManager(final Context context) {
        return (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

}
