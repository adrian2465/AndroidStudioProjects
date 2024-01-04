package com.cantocrew.gpsresults;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.security.Security;

public class MainActivity extends AppCompatActivity {

    private TextView mTextLocationResult;
    private TextView mTextLocationTimestamp;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_doit:
                    mTextLocationResult.setText(R.string.title_doit);
                    return true;
            }
            return false;
        }
    };
    private final static int LOCATION_REFRESH_TIME = 5000; // Refresh time in milliseconds.
    private final static int LOCATION_REFRESH_DISTANCE = 10; // Refresh distance in meters.

    LocationManager mLocationManager=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextLocationResult = (TextView) findViewById(R.id.location_result);
        mTextLocationTimestamp = (TextView) findViewById(R.id.location_timestamp);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_doit);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        try {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE, mLocationListener);
        } catch (SecurityException se) {
            se.printStackTrace();
        }
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
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
    };

}
