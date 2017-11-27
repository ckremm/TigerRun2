package com.example.cj.tigerrun;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

/**
 * Created by CJ on 11/20/2017.
 */

public class GeofenceService extends IntentService {

    public GeofenceService(){
        super("GeofenceService");
    }

    public void onHandleIntent(Intent intent){
        GeofencingEvent event = GeofencingEvent.fromIntent(intent);
        if(event.hasError()){

        }else{
            int transition = event.getGeofenceTransition();
            List<Geofence> geofences = event.getTriggeringGeofences();
            Geofence geofence = geofences.get(0);
            String requestId = geofence.getRequestId();
        }
    }

}
