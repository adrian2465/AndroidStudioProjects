package com.cantocrew.gpslocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements LocationChangedListener, SensorEventListener {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");

    private TextView latView, lonView, speedKtsView, speedMphView, headingView, bearingView, timeView;
    private LocationManager locationManager;
    private GpsHandler gpsHandler;
    private TextView[] AllViews;
    private double bearing=0.0;
    private SensorManager mSensorManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        gpsHandler = new GpsHandler(locationManager);
        AllViews = new TextView[]{
                latView = findViewById(R.id.lat),
                lonView = findViewById(R.id.lon),
                speedKtsView = findViewById(R.id.speedKts),
                speedMphView = findViewById(R.id.speedMph),
                headingView = findViewById(R.id.heading),
                bearingView = findViewById(R.id.bearing),
                timeView = findViewById(R.id.timeOfFix)
        };

        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            latView.setText(R.string.PermsError);
        }

        gpsHandler.gpsListener(this);
        updateViews();
    }
    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStart()");
        gpsHandler.registerWithGps();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }
    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop()");
        gpsHandler.unregisterFromGps();
        mSensorManager.unregisterListener(this);
    }

    public void locationChanged(Location loc) {
        if (loc == null) return;
        updateViews();
    }

    public void gpsStatusChanged(boolean status) {
        System.out.println("GPS " + (status ? "available" : "un-available"));
        updateViews();
    }


    private void setColor(int color) {
        for (TextView view : AllViews) view.setTextColor(color);
    }

    private void updateViews() {
        if (gpsHandler.getLastKnownLocation() == null) {
            setColor(Color.RED);
            return;
        }
        setColor(Color.BLACK);
        latView.setText(Formatters.formatNS(gpsHandler.getLastKnownLocation().getLatitude()));
        lonView.setText(Formatters.formatEW(gpsHandler.getLastKnownLocation().getLongitude()));
        speedKtsView.setText(Formatters.formatSpeedKts(gpsHandler.getSpeedKts()));
        speedMphView.setText(Formatters.formatSpeedMph(gpsHandler.getSpeedMph()));
        headingView.setText("H="+Formatters.formatHeadingTrue(gpsHandler.getHeadingTrue()));
        timeView.setText(simpleDateFormat.format(new Date(gpsHandler.getLastKnownLocation().getTime())));
        bearingView.setText("CMP="+Formatters.formatBearingMag(bearing));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // get the angle around the z-axis rotated
        bearing = Math.round(event.values[0]);
        bearingView.setText("Compass="+Formatters.formatBearingMag(bearing));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Required in order to not be abstract.
    }
}
