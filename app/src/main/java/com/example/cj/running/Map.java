package com.example.cj.running;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cj.tigerrun.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class Map extends AppCompatActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    public static final String TAG = "Map";
    public static final String GEOFENCE_ID = "MyGeo";
    private static final LatLng CLEMSON = new LatLng(34.67595302874027, -82.83682249577026);

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    int Seconds, Minutes, MilliSeconds ;
    Handler handler;


    //Geofence data changed to texas update on return
    private static final LatLng TEXAS = new LatLng(29.5323806, -98.48824230000002);

    GoogleApiClient googleApiClient = null;

    private Button startLocationMonitoring;
    private Button startGeofenceMonitoring;
    private Button stopGeofenceMonitoring;
    private GoogleMap map;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

/*
        startLocationMonitoring = (Button) findViewById(R.id.startLocationMonitoring);
        startLocationMonitoring.setOnClickListener((v){
            startLocationMonitoring();
        });

        startGeofenceMonitoring = (Button) findViewById(R.id.startGeofenceMonitoring);
        startGeofenceMonitoring.setOnClickListener((v){
                startGeofenceMonitoring();
        });

        stopGeofenceMonitoring = (Button) findViewById(R.id.stopGeofenceMonitoring);
        stopGeofenceMonitoring.setOnClickListener((v){
                stopGeofenceMonitoring();
        });
*/
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        Log.d(TAG, "connected");
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.d(TAG, "suspened");
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.d(TAG, "Failed - " + connectionResult.getErrorMessage());
                    }
                })
                .build();

        startGeofenceMonitoring();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.location_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onStart(){
        Log.d(TAG, "on start");
        super.onStart();
//        googleApiClient.connect();
        googleApiClient.reconnect();
    }

    @Override
    protected void onStop(){
        super.onStop();
        googleApiClient.disconnect();
    }

/*    private void startLocationMonitoring() {

        try{
            LocationRequest locationRequest = LocationRequest.create()
                    .setInterval(10000)
                    .setFastestInterval(5000)
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,
                    locationRequest, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Log.d(TAG, "Location updated");
                        }
                    });
        }catch(SecurityException e){
            Log.d(TAG, "Security exception");
        }
    }
*/
    private void startGeofenceMonitoring(){
        try{
            Geofence geofence = new Geofence.Builder()
                    .setRequestId(GEOFENCE_ID)
                    .setCircularRegion(29.5323806, -98.48824230000002, 20)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                    .build();
            GeofencingRequest geofencingRequest = new GeofencingRequest.Builder()
                    .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                    .addGeofence(geofence)
                    .build();

            Intent intent = new Intent(this, GeofenceService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            if(!googleApiClient.isConnected()) {

            }else{
                LocationServices.GeofencingApi.addGeofences(googleApiClient, geofencingRequest, pendingIntent)
                        .setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {

                            }
                        });
            }
        } catch(SecurityException e){

        }

    }

    private void stopGeofenceMonitoring(){
        ArrayList<String> geofenceIds = new ArrayList<String>();
        geofenceIds.add(GEOFENCE_ID);
        LocationServices.GeofencingApi.removeGeofences(googleApiClient, geofenceIds);
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            map = googleMap;
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(CLEMSON, 15));

            Circle circle = map.addCircle(new CircleOptions()
                    .center(CLEMSON)
                    .radius(20)
                    .fillColor(Color.argb(
                            200,255,69,0
                    )));

            //startLocationMonitoring();
            map.setMyLocationEnabled(true);
            map.setOnMyLocationButtonClickListener(this);
            map.setOnMyLocationClickListener(this);
        }catch(SecurityException e){

        }

    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);
            TextView t = (TextView) findViewById(R.id.stopw);

            t.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };
}
