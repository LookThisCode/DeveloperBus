/**
 * File: CMUtils.java
 * CreationDate: 20/03/2013
 * Author: "M. en C. Javier Silva Perez (JSP)"
 * Description: 
 * 	Class that contains common functions across the application as well as static
 * 	values that will be used by other classes
 */
package com.devbus.sweethistory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

/**
 * Class that contains common functions across the application as well as static values that will be used by other
 * classes
 *
 * @author "M. en C. Javier Silva Perez (JSP)"
 * @version 1.0
 * @since 20/03/2013
 */
public class CMUtils {
    public static final String FONT_REFLEX_BLACK = "fonts/ReflexBlack.ttf";
    public static final String FONT_VERDANA = "fonts/Verdana.ttf";
    public static final String FONT_ARIAL_BLACK = "fonts/ArialBlack.ttf";
    public static final String FONT_GOTHIC = "fonts/gothic_0.ttf";
    public static final SecureRandom SECURE_RANDOM = new SecureRandom();
    public static final String IMG = "IMG_";
    public static final String JPG = ".jpg";
    public static final String DATE_FORMAT = "yyyyMMdd_HHmmss";
    public static final String DIRECTORY_NAME = "buenfin";
    /**
     * Unique ID of the Android device
     */
    public static String ANDROID_ID;
    /**
     * Serial number of the SIM, null if unavailable
     */
    public static String SIM_SERIAL_NUMBER;

    /**
     * Sets a determined font on a text view element
     *
     * @param context   Context in which the TextView can be found
     * @param font      Font to be set in the text view see available fonts as static attributes of this class
     * @param style     {@see Typeface}
     * @param textViews TextViews to which the font will be applied
     */
    public static void setTypeface(Context context, String font, int style, TextView... textViews) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(), font);
        for (TextView txt : textViews) {
            txt.setTypeface(tf, style);
        }
    }

    /**
     * Sets a determined font on a text view element
     *
     * @param context   Context in which the TextView can be found
     * @param font      Font to be set in the text view see available fonts as static attributes of this class
     * @param textViews TextViews to which the font will be applied
     */
    public static void setTypeface(Context context, String font, TextView... textViews) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(), font);
        for (TextView txt : textViews) {
            txt.setTypeface(tf);
        }
    }

    /**
     * Sets a determined font on a button element view
     *
     * @param context Context in which the TextView can be found
     * @param font    Font to be set in the text view see available fonts as static attributes of this class
     * @param buttons Buttons to which the font will be applied
     */
    public static void setTypeface(Context context, String font, Button... buttons) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(), font);
        for (Button txt : buttons) {
            txt.setTypeface(tf);
        }
    }

    /**
     * Sets a determined font on a text view element
     *
     * @param context Context in which the TextView can be found
     * @param font    Font to be set in the text view see available fonts as static attributes of this class
     * @param style   {@see Typeface}
     * @param buttons Buttons to which the font will be applied
     */
    public static void setTypeface(Context context, String font, int style, Button... buttons) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(), font);
        for (Button txt : buttons) {
            txt.setTypeface(tf, style);
        }
    }

    /**
     * Get the MD5 hash of an input
     *
     * @param input
     * @return The MD5 hash of the selected input
     */
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashText = number.toString(16);
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;
        } catch (NoSuchAlgorithmException e) {
            Log.e(MainActivity.TAG, e.getMessage(), e);
            return "";
        }

    }

    /**
     * Converts an hexadecimal string to a byte array
     *
     * @param s Hexadecimal string
     * @return A byte array with the parsed hexadecimal values
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * Sets a determined font on a text view element
     *
     * @param context Context in which the TextView can be found
     * @param font    Font to be set in the text view see available fonts as static attributes of this class
     * @param style   {@see Typeface}
     * @param group   Root layout in which TextView and Buttons will be searched to apply the font
     */
    public static void setTypeface(Context context, String font, int style, ViewGroup group) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(), font);
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView) ((TextView) v).setTypeface(tf, style);
            else if (v instanceof ViewGroup) setTypeface(context, font, style, (ViewGroup) v);
        }
    }

    /**
     * Sets a determined font on a text view element
     *
     * @param context Context in which the TextView can be found
     * @param font    Font to be set in the text view see available fonts as static attributes of this class
     * @param group   Root layout in which TextView and Buttons will be searched to apply the font
     */
    public static void setTypeface(Context context, String font, ViewGroup group) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(), font);
        int count = group.getChildCount();
        View v;
        for (int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if (v instanceof TextView) ((TextView) v).setTypeface(tf);
            else if (v instanceof ViewGroup) setTypeface(context, font, (ViewGroup) v);
        }
    }

    /**
     * Set an error message to a text view
     *
     * @param color   Set the color foreground for the span
     * @param message Message to be shown
     * @param txtView Text View to which the message will be added
     */
    public static void setError(int color, String message, TextView txtView) {
        ForegroundColorSpan fgcspan = new ForegroundColorSpan(color);
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);
        ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
        txtView.setError(ssbuilder);
    }



    /**
     * Reads the phone state in order to get SIM serial number and AndroidID, those values are saved in static
     * attributes in this class
     *
     * @param context
     */
    public static void readPhoneState(Context context) {

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        //Get SIM serial number
        SIM_SERIAL_NUMBER = telephonyManager.getSimSerialNumber();
        if (SIM_SERIAL_NUMBER == null) {
            SIM_SERIAL_NUMBER = "";
        }

	  /*
       * Settings.Secure.ANDROID_ID returns the unique DeviceID
	   * Works for Android 2.2 and above
	   */
        ANDROID_ID = getUniqueDeviceId(context);
    }

    /**
     * Gets an unique device Id depending on the sdk version
     *
     * @param context
     * @return
     */
    public static String getUniqueDeviceId(Context context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        }
    }

    /**
     * Disable all the childs of the selected root view
     *
     * @param rootView View to iterate in order to disable all its childs
     * @param alpha    Alpha to set to disabled elements
     */
    public static void disableView(ViewGroup rootView, float alpha) {
        int count = rootView.getChildCount();
        View v;
        //Go over the child list of the view and disable all
        for (int i = 0; i < count; i++) {
            v = rootView.getChildAt(i);
            if (v != null) {
                if (v instanceof ViewGroup) disableView((ViewGroup) v, alpha);
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) v.setAlpha(alpha);
                v.setEnabled(false);
            }
        }
    }

    /**
     * Remove the time of a date value
     *
     * @param date Date to remove the time part
     * @return A date with its time set to 00:00:00
     */
    public static Date removeTime(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MILLISECOND, 0);
        return gc.getTime();
    }

    /**
     * Change the default locale of the application for this activity
     *
     * @param locale To set in the configuration
     */
    public static void setDefaultLocale(Context context, String locale) {
        Locale locJa = new Locale(locale);
        Locale.setDefault(locJa);

        Configuration config = new Configuration();
        config.locale = locJa;

        if (context != null) {
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }
    }




    /**
     * This method fetches data from a given url
     *
     * @param strUrl Url from which the data will be fetched
     * @return A String representing the resource obtained in the connection
     * @throws IOException If something went wrong with the connection
     */
    public static String getDataFromUrl(String strUrl) throws IOException {
        InputStream iStream;
        HttpURLConnection urlConnection;
        URL url = new URL(strUrl);

        // Creating an http connection to communicate with url
        urlConnection = (HttpURLConnection) url.openConnection();

        // Connecting to url
        urlConnection.connect();

        // Reading data from url
        iStream = urlConnection.getInputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        iStream.close();
        urlConnection.disconnect();
        return sb.toString();
    }

    /**
     * Change opacity of a given view, with the alpha level given as parameter.
     * This method considers the environment version.
     *
     * @param view       View that will change its opacity
     * @param alphaLevel Alpha level representing the opacity, where 0.0 is completely transparent, and 1.0 is completely opaque
     */
    public static void setAlphaForAllVersions(View view, float alphaLevel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            view.setAlpha(alphaLevel);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(alphaLevel, alphaLevel);
            alpha.setDuration(0); // Make animation instant
            alpha.setFillAfter(true); // Tell it to persist after the animation ends
            view.startAnimation(alpha);
        }
    }

    /**
     * Decodes a bitmap to its corresponding representation in bytes, in the format sent as parameter
     *
     * @param bitmap Bitmap to decode
     * @param format Format with which this image is going to be decoded
     * @return A byte array containing the bytes of the decoded bitmap
     */
    public static byte[] bitmapToByteArray(Bitmap bitmap, Bitmap.CompressFormat format) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(format, 100, stream);
        return stream.toByteArray();
    }

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * Generate a value suitable for use in {@link #(int)}.
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    /**
     * Method to remove accents from an uppercase string
     *
     * @param str String to remove accents from
     * @return The new string without accents
     */
    public static String removeAccentsUppercase(String str) {
        str = str.replaceAll("[Ã�Ã€Ã„Ã‚]", "A");
        str = str.replaceAll("[Ã‰ÃˆÃ‹ÃŠ]", "E");
        str = str.replaceAll("[Ã�ÃŒÃŽÃ�]", "I");
        str = str.replaceAll("[Ã“Ã’Ã–Ã”]", "O");
        str = str.replaceAll("[ÃšÃ™ÃœÃ›]", "U");
        return str;
    }

    public static String cleanString(String value) {
//Remove spaces and upper case input
        String res = value.trim();


// The char 180 (Â´) is not replaced by the regex, so first replace it by '-'
        res = res.replace((char) 180, '-');

//Normalize the string will separate all of the accent marks from the characters.
        res = Normalizer.normalize(res, Normalizer.Form.NFD);
//Using a regular expression remove every non alphanumeric character (don't include lower Case characters
// because the first line change them to upper case)
        res = res.replace(" ", "%20");
        res = res.replaceAll("([^a-zA-Z0-9%])", "");
        return res;
    }
}
