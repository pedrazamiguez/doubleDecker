package uk.co.andrespedraza.android.doubledecker.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.Collections;
import java.util.List;

import uk.co.andrespedraza.android.doubledecker.R;
import uk.co.andrespedraza.android.doubledecker.network.domain.StopPoint;
import uk.co.andrespedraza.android.doubledecker.network.domain.StopPointsResponse;
import uk.co.andrespedraza.android.doubledecker.network.request.ListBusStopsRequest;
import uk.co.andrespedraza.android.doubledecker.service.FetchAddressIntentService;
import uk.co.andrespedraza.android.doubledecker.util.Constants;
import uk.co.andrespedraza.android.doubledecker.util.StopPointDistanceComparator;
import uk.co.andrespedraza.android.doubledecker.util.TfLUtils;

public class NearbyActivity extends DoubleDeckerActivity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    // Constants for location service.
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 60 * 1000; // 1 minute
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS;
    // Tag for logging.
    protected static final String TAG = "NearbyActivity";
    protected static final String ADDRESS_REQUESTED_KEY = "address-request-pending";
    protected static final String LOCATION_ADDRESS_KEY = "location-address";
    protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    protected final static String LOCATION_KEY = "location-key";

    // Constant for location permission dialog.
    protected static final int PERMISSION_REQUEST_LOCATION = 172;

    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected Location mCurrentLocation;
    protected RecyclerView mBusStopsRecyclerView;
    protected BusStopsAdapter mBusStopsAdapter;
    protected boolean mAddressRequested;
    protected Boolean mRequestingLocationUpdates;
    protected String mAddressOutput;
    private AddressResultReceiver mResultReceiver;
    private TextView mNearbyTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        mResultReceiver = new AddressResultReceiver(new Handler());

        mNearbyTitle = (TextView) findViewById(R.id.nearby_location);
        mNearbyTitle.setSelected(true);
        mBusStopsRecyclerView = (RecyclerView) findViewById(R.id.stop_points_recycler_view);
        mBusStopsRecyclerView.setHasFixedSize(true);
        mBusStopsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set defaults, then update using values stored in the Bundle.
        mAddressRequested = false;
        mAddressOutput = "";
        mRequestingLocationUpdates = true;
        updateValuesFromBundle(savedInstanceState);

        buildGoogleApiClient();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
        fetchAddressHandler();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        Log.i(TAG, "Updating values from bundle");
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(ADDRESS_REQUESTED_KEY)) {
                mAddressRequested = savedInstanceState.getBoolean(ADDRESS_REQUESTED_KEY);
            }
            if (savedInstanceState.keySet().contains(LOCATION_ADDRESS_KEY)) {
                mAddressOutput = savedInstanceState.getString(LOCATION_ADDRESS_KEY);
                displayAddressOutput();
            }
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        REQUESTING_LOCATION_UPDATES_KEY);
            }
            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }
            fetchAddressHandler();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        } catch (SecurityException e) {
            showSnackbar(R.id.activity_nearby, R.string.no_permission_granted, R.color.colorPrimary);
        }
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    public void fetchAddressHandler() {
        if (null == mGoogleApiClient) {
            buildGoogleApiClient();
        }
        if (mGoogleApiClient.isConnected() && mCurrentLocation != null) {
            startIntentService();
            fetchBusStopsNearby();
        }
        mAddressRequested = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        if (PackageManager.PERMISSION_GRANTED == permissionCheck) {
            findMostRecentLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_REQUEST_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    findMostRecentLocation();
                } else {
                    showSnackbar(R.id.activity_nearby, R.string.no_permission_granted, R.color.colorPrimary);
                }
            }
        }
    }

    protected void findMostRecentLocation() {
        try {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mCurrentLocation != null) {
                if (!Geocoder.isPresent()) {
                    showSnackbar(R.id.activity_nearby, R.string.no_geocoder_available, R.color.colorPrimary);
                    return;
                }
                if (mAddressRequested) {
                    startIntentService();
                }
            }

            if (mCurrentLocation == null) {
                mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                fetchAddressHandler();
            }

            if (mRequestingLocationUpdates) {
                startLocationUpdates();
            }
        } catch (SecurityException e) {
            showSnackbar(R.id.activity_nearby, R.string.no_permission_granted, R.color.colorPrimary);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        fetchAddressHandler();
    }

    protected void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mCurrentLocation);
        startService(intent);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    protected void displayAddressOutput() {
        if (null != mAddressOutput) {
            mAddressOutput = mAddressOutput.replace("\n", " ").trim();
        }
        mNearbyTitle.setText(mAddressOutput);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(ADDRESS_REQUESTED_KEY, mAddressRequested);
        savedInstanceState.putString(LOCATION_ADDRESS_KEY, mAddressOutput);
        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void fetchBusStopsNearby() {
        getSpiceManager().execute(
                new ListBusStopsRequest(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()),
                ListBusStopsRequest.CACHE_KEY,
                DurationInMillis.ALWAYS_EXPIRED,
                new RequestListener<StopPointsResponse>() {
                    @Override
                    public void onRequestFailure(SpiceException spiceException) {
                        showSnackbar(R.id.activity_nearby, R.string.network_error, R.color.colorPrimary);
                    }

                    @Override
                    public void onRequestSuccess(StopPointsResponse stopPointsResponse) {
                        if (null != stopPointsResponse && null != stopPointsResponse.getStopPoints() && !stopPointsResponse.getStopPoints().isEmpty()) {

                            // Remove stop points without lines.
                            stopPointsResponse.setStopPoints(TfLUtils.removeStopPointsWithoutLines(stopPointsResponse.getStopPoints()));

                            // Set custom order.
                            Collections.sort(stopPointsResponse.getStopPoints(), new StopPointDistanceComparator(mCurrentLocation));

                            if (null == mBusStopsAdapter) {
                                mBusStopsAdapter = new BusStopsAdapter(stopPointsResponse);
                                mBusStopsRecyclerView.setAdapter(mBusStopsAdapter);
                            } else {
                                mBusStopsAdapter.mStopPoints = stopPointsResponse.getStopPoints();
                            }

                            mBusStopsAdapter.notifyDataSetChanged();
                        }
                    }
                }
        );
    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            displayAddressOutput();
            mAddressRequested = false;
        }
    }

    public class BusStopsHolder extends RecyclerView.ViewHolder {

        private StopPoint mStopPoint;
        private TextView mStopPointNameTextView;
        private TextView mStopPointLetterTextView;
        private TextView mStopPointLinesTextView;
        private TextView mStopPointDistanceTextView;

        public BusStopsHolder(View itemView) {
            super(itemView);

            mStopPointNameTextView = (TextView) itemView.findViewById(R.id.stop_point_name);
            mStopPointLetterTextView = (TextView) itemView.findViewById(R.id.stop_point_letter);
            mStopPointLinesTextView = (TextView) itemView.findViewById(R.id.stop_point_lines);
            mStopPointDistanceTextView = (TextView) itemView.findViewById(R.id.stop_point_distance);

            itemView.setOnClickListener((View v) -> {
                Intent intent = new Intent(NearbyActivity.this, BusStopActivity.class);
                intent.putExtra(BusStopActivity.INTENT_EXTRA_BUS_POINT, mStopPoint);
                startActivity(intent);
            });
        }

        public void bindBusStop(StopPoint stopPoint) {
            mStopPoint = stopPoint;
            mStopPointNameTextView.setText(mStopPoint.getCommonName());
            mStopPointLetterTextView.setText(mStopPoint.getStopLetter());
            mStopPointLinesTextView.setText(mStopPoint.generateLinesList());
            mStopPointDistanceTextView.setText(mStopPoint.getRoundedCalculatedDistance());
        }

    }

    private class BusStopsAdapter extends RecyclerView.Adapter<BusStopsHolder> {

        private List<StopPoint> mStopPoints;

        public BusStopsAdapter(StopPointsResponse stopPointsResponse) {
            this.mStopPoints = stopPointsResponse.getStopPoints();
        }

        @Override
        public BusStopsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(NearbyActivity.this);
            View mView = mLayoutInflater.inflate(R.layout.list_item_stop_point, parent, false);
            return new BusStopsHolder(mView);
        }

        @Override
        public void onBindViewHolder(BusStopsHolder holder, int position) {
            holder.bindBusStop(this.mStopPoints.get(position));
        }

        @Override
        public int getItemCount() {
            return this.mStopPoints.size();
        }
    }
}
