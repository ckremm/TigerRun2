package com.example.cj.running;

import java.util.ArrayList;

/**
 * Created by CJ on 11/29/2017.
 */

public class trackHelper {
    private String name, desc, trackID;
    private ArrayList<String> lmarks;
    private int dif;

    public trackHelper(String name, String desc, String id, ArrayList<String> l, int dif){
        this.setName(name);
        this.setDesc(desc);
        this.setTrackID(id);
        this.setLmarks(l);
        this.setDif(dif);
    }

    public String getName(){ return name; }

    public String getDesc(){ return desc; }

    public String getTrackID(){ return trackID; }

    public ArrayList<String> getLmarks(){ return lmarks; }

    public int getDif(){ return dif; }

    public void setName(String name){this.name = name;}

    public void setDesc(String desc){this.desc = desc;}

    public void setTrackID(String id){this.trackID = id;}

    public void setLmarks (ArrayList<String> l){this.lmarks = l;}

    public void setDif (int dif){this.dif = dif;}


}
