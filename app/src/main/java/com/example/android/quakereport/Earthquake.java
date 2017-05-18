package com.example.android.quakereport;

import static com.example.android.quakereport.R.id.date;

/**
 * An {@Link Earthquake} object that has many different information on the earthquake
 */

public class Earthquake {
    private double mMagnitude;
    private String mLocation;
    private long mTimeInMilliseconds;
    private String mWebUrl;

    public Earthquake(double magnitude, String location, long timeInMilliseconds, String webURL){
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
        mWebUrl = webURL;
    }

    public double getMagnitude(){return mMagnitude;}
    public String getLocation(){return mLocation;}
    public long getTimeInMilliseconds(){return mTimeInMilliseconds;}
    public String getWebURL(){return mWebUrl;}

}
