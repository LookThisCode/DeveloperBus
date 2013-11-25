package br.com.expenseme.ui;

import android.app.Dialog;
import android.content.IntentSender;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import br.com.expenseme.R;
import br.com.expenseme.util.GetAddressTask;
import br.com.expenseme.util.GetAddressTask.GetAddressTaskCallback;
import br.com.expenseme.util.LocationUtils;
import br.com.expenseme.util.Util;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class VenueMapFragment
        extends Fragment
        implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        GetAddressTaskCallback {

    private static final String TAG = "VenueMapFragment";
    private static View view;
    private LocationClient mLocationClient;

    // Default to Sao Paulo
    private Pair<Double, Double> latlng = new Pair<Double, Double>(-23.548943, -46.638818);
    private GoogleMap map;
    private Marker currentLocationMarker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLocationClient = new LocationClient(getActivity(), this, this);

        if (view != null) {
            ViewGroup parent = (ViewGroup)view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.layout_venues, container, false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }

        return view;
    }

    private void getLocation() {
        // If Google Play Services is available
        if (Util.isServicesConnected((SherlockFragmentActivity)getActivity())) {
            // Get the current location
            Location currentLocation = mLocationClient.getLastLocation();

            latlng = LocationUtils.getLatLng(getActivity(), currentLocation);
        }
    }

    private void getAddress() {
        // In Gingerbread and later, use Geocoder.isPresent() to see if a geocoder is available.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD && !Geocoder.isPresent()) {
            // No geocoder is present. Issue an error message
            Toast.makeText(getActivity(), "Geocoder não disponível", Toast.LENGTH_LONG).show();
            return;
        }

        if (Util.isServicesConnected((SherlockFragmentActivity)getActivity())) {
            // Get the current location
            Location currentLocation = mLocationClient.getLastLocation();

            // Start the background task
            new GetAddressTask(getActivity(), this).execute(currentLocation);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        mLocationClient.connect();
    }

    private void showMap() {
        Fragment mapFragment = getFragmentManager().findFragmentById(R.id.map);
        map = ((SupportMapFragment)mapFragment).getMap();
        map.clear();
        LatLng currLocation = new LatLng(latlng.first, latlng.second);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currLocation, 15));

        currentLocationMarker = map.addMarker(new MarkerOptions()
                .title("Localização atual")
                .position(currLocation));
    }

    @Override
    public void onStop() {
        // After disconnect() is called, the client is considered "dead".
        mLocationClient.disconnect();

        super.onStop();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                    getActivity(),
                    LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */

            } catch (IntentSender.SendIntentException e) {

                // Log the error
                e.printStackTrace();
            }
        } else {
            // If no resolution is available, display a dialog to the user with the error.
            showErrorDialog(connectionResult.getErrorCode());
        }
    }

    private void showErrorDialog(int errorCode) {
        // Get the error dialog from Google Play services
        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
            errorCode,
            getActivity(),
            LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

        // If Google Play services can provide an error dialog
        if (errorDialog != null) {

            // Create a new DialogFragment in which to show the error dialog
            ErrorDialogFragment errorFragment = new ErrorDialogFragment();

            // Set the dialog in the DialogFragment
            errorFragment.setDialog(errorDialog);

            // Show the error dialog in the DialogFragment
            errorFragment.show(getActivity().getSupportFragmentManager(), LocationUtils.APPTAG);
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d(TAG, "connected");

        getLocation();
        showMap();
        getAddress();
    }

    @Override
    public void onDisconnected() {
        Log.d(TAG, "disconnected");
    }

    @Override
    public void onGotAddress(String address) {
        if (currentLocationMarker != null)
            currentLocationMarker.remove();

        currentLocationMarker = map.addMarker(new MarkerOptions()
                .title("Localização atual")
                .snippet(address)
                .position(new LatLng(latlng.first, latlng.second)));
    }
}
