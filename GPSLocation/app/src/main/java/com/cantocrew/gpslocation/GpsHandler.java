package com.cantocrew.gpslocation;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cantocrew.navigation.sailings.*;

import java.util.ArrayList;

public class GpsHandler extends AppCompatActivity implements LocationListener {
    // private static final int TIME_BETWEEN_FIXES = 1000; // MS DEBUG
    // private static final int DEFAULT_POLLING_DISTANCE = 0; // meters DEBUG
    private static final int ONE_SECOND = 1000;
    private static final int ONE_MINUTE = ONE_SECOND * 60;
    private static final int ONE_HOUR = ONE_MINUTE * 60;
    private static final double KTS2MPH=1.15078;
    private static final int TIME_BETWEEN_FIXES = 2*ONE_SECOND ;
    private static final int DEFAULT_POLLING_DISTANCE = 1; // meters
    private static final int DEFAULT_POLLING_INTERVAL = TIME_BETWEEN_FIXES / 2; // milliseconds.
    private int polling_interval = DEFAULT_POLLING_INTERVAL;
    private int polling_distance = DEFAULT_POLLING_DISTANCE;
    private LocationManager locationManager;
    private ArrayList<LocationChangedListener> locationChangedListeners = new ArrayList<>();
    private Location prevLocation = null;
    private double speedKts=0.0, headingTrue =0.0;
    public GpsHandler(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (prevLocation != null) {
            Coordinate src = new Coordinate(new Ordinate(prevLocation.getLatitude(), OrdinateType.LAT), new Ordinate(prevLocation.getLongitude(), OrdinateType.LON));
            Coordinate dst = new Coordinate(new Ordinate(location.getLatitude(),     OrdinateType.LAT), new Ordinate(location.getLongitude(),     OrdinateType.LON));
            Vector v = Vector.getVector(src, dst);
            // Manual speed calculation. Not extremely accurate for close points.
//            double delta_time_hrs = (location.getTime() - prevLocation.getTime()) / ( ONE_HOUR * 1.0) ;
//            speedKts = v.distance / delta_time_hrs; // Manual speed calculation.
            speedKts = Formatters.metersPerSecondToKnots(location.getSpeed());
            headingTrue = v.direction; 
//            headingTrue = location.getBearing();
        }
        for (LocationChangedListener l : locationChangedListeners) l.locationChanged(location);
        prevLocation=location;
    }

    int prevStatus = -1;
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        System.out.println("onStatusChanged("+provider+","+status+","+extras);
        String statusStr;
        boolean rc=false;
        if (prevStatus == status) return; // Ignore if no real status change.
        prevStatus = status;
        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
                rc=false;
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                rc=false;
                break;
            case LocationProvider.AVAILABLE:
                rc=true;
                break;
            default:
                rc=false;
        }
        for (LocationChangedListener l : locationChangedListeners) l.gpsStatusChanged(rc);
    }

    public double getSpeedKts() {
        return speedKts;
    }
    public double getSpeedMph() {
        return  speedKts * KTS2MPH;
    }
    public double getHeadingTrue() {
        return headingTrue;
    }
    @SuppressLint("MissingPermission")
    public void registerWithGps() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, polling_interval, polling_distance, this);
    }
    public void unregisterFromGps() {
        locationManager.removeUpdates(this);
    }
    @Override
    public void onProviderEnabled(String provider) {
        System.out.println("onProviderEnabled("+provider+")");
        for (LocationChangedListener l : locationChangedListeners) l.gpsStatusChanged(true);
    }

    @Override
    public void onProviderDisabled(String provider) {
        System.out.println("onProviderDisabled("+provider+")");
        for (LocationChangedListener l : locationChangedListeners) l.gpsStatusChanged(false);
    }

    public void gpsListener(LocationChangedListener lcl) {
        locationChangedListeners.add(lcl);
    }

    @SuppressLint("MissingPermission")
    public Location getLastKnownLocation() {
        return locationManager.getLastKnownLocation("gps");
    }

    /**
     * Checks whether two providers are the same
     */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }
}
