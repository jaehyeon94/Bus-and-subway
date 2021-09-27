package com.example.a1.whereami;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class GpsInfo implements LocationListener{
    Context context;
    GpsObject gpsObject = new GpsObject();
    Location location;
    boolean isNetworkEnabled = false;
    boolean isGPSEnabled = false;

    public GpsInfo(Context context) {
        this.context = context;
    }

    @SuppressLint("MissingPermission")
    public GpsObject getLocation(){
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,100,1,this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,100,1,this);

        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location!=null)
            onLocationChanged(location);

        return gpsObject;
    }

    @Override
    public void onLocationChanged(Location location) {
        gpsObject.setLat(location.getLatitude());
        gpsObject.setLng(location.getLongitude());
        Log.e("위도경도", String.valueOf(gpsObject.getLat() + " , " + gpsObject.getLng()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
