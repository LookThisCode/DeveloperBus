package br.com.expenseme.util;

import java.io.File;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import br.com.expenseme.ui.ErrorDialogFragment;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class Util {
    private static final String TAG = "Util";

    public static boolean isIntentAvailable(final Context context, final String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        final List<ResolveInfo> list = packageManager.queryIntentActivities(
            intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static void addImageToDeviceGallery(final Context context,
                                               final String imagePath) {
        final Intent mediaScanIntent = new Intent(
                "android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        final File f = new File(imagePath);
        final Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    public static int dpToPx(final Context context, final float dp) {
        // Took from
        // http://stackoverflow.com/questions/8309354/formula-px-to-dp-dp-to-px-android
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)((dp * scale) + 0.5f);
    }

    /**
     * Verify that Google Play services is available before making a request.
     * 
     * @return true if Google Play services is available, otherwise false
     */
    public static boolean isServicesConnected(SherlockFragmentActivity context) {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);

        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode)
            return true;

        // Google Play services was not available for some reason
        // Display an error dialog
        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, context, 0);
        if (dialog != null) {
            ErrorDialogFragment errorFragment = new ErrorDialogFragment();
            errorFragment.setDialog(dialog);
            errorFragment.show(context.getSupportFragmentManager(), LocationUtils.APPTAG);
        }
        return false;
    }
}
