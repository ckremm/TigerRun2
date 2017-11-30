package com.example.cj.running;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.cj.running.Landmarks.CLEMSON;

/**
 * Created by CJ on 11/29/2017.
 */

public class tHelper {

    public ArrayList<trackHelper> t;

    public tHelper(){
        t = new ArrayList<trackHelper>();

        t.add(new trackHelper("Doug's Track", " ", "1", null, 3));
        t.add(new trackHelper("AJ's Track", " ", "2", null, 2));
        t.add(new trackHelper("CJ's Track", " ", "3", null, 3));
        t.add(new trackHelper("Lever Run", "Starts at Lever and wraps around the pond. Small hills, excellent views!",
                "4", new ArrayList<String>(Arrays.asList("Lever", "McAdams Hall", "Watt Family Center", "Front of Cooper Library", "Amphitheater", "Tillman Hall")),
                2));
        t.add(new trackHelper("Library Run", " ", "5", null, 2));
    }

}
