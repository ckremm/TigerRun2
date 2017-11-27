package com.example.cj.running;

/**
 * Created by CJ on 11/20/2017.
 */

import com.google.android.gms.maps.model.LatLng;
import java.util.HashMap;

public class Landmarks {

    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = 12 * 60 * 60 * 1000;
    public static final float GEOFENCE_RADIUS_IN_METERS = 20;

    public static final HashMap<String, LatLng> CLEMSON = new HashMap<String, LatLng>();
    static {
        // Cooper.
        CLEMSON.put("Front of Cooper Library", new LatLng(34.676941222505114, -82.83632896931152));

        // Lever.
        CLEMSON.put("Lever", new LatLng(34.67731179212703, -82.83167265446167));

        // Watt Center.
        CLEMSON.put("Watt Family Center", new LatLng(34.67595302874027, -82.83682249577026));
    }

}
