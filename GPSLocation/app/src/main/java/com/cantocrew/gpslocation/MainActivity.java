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
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements LocationChangedListener, SensorEventListener {
    private static final int TEN_MINUTES = 1000 * 60 * 10;
    private static final long TEN_SECONDS = 10000L;
    private static final long ONE_SECOND = 1000L;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");

    private final static String gpxFormat = "<trkpt lat=\"%1$f\" lon=\"%2$f\"><time>%3$s</time></trkpt>\n";
    private final static String txtFormat = "%s: lat=%s lon=%s  (%f,%f)\n";


    private TextView latView, lonView, speedKtsView, speedMphView, headingView, bearingView, timeView;
    private Switch gpsState;
    private LocationManager locationManager;
    private GpsHandler gpsHandler;
    private long locationTimestamp = System.currentTimeMillis();
    private String locationStr = "Position Unknown";
    private TextView[] AllViews;
    private Context context;
    private boolean isGpsEnabled = true;
    private double bearing=0.0;
    private SensorManager mSensorManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        gpsHandler = new GpsHandler(locationManager);
        context = getBaseContext();
        AllViews = new TextView[]{
                latView = findViewById(R.id.lat),
                lonView = findViewById(R.id.lon),
                speedKtsView = findViewById(R.id.speedKts),
                speedMphView = findViewById(R.id.speedMph),
                headingView = findViewById(R.id.heading),
                bearingView = findViewById(R.id.bearing),
                timeView = findViewById(R.id.timeOfFix),
                gpsState = findViewById(R.id.gpsstate)
        };
        gpsState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               gpsHandler.registerWithGps();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateViews();
            }
        });
        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            latView.setText(R.string.PermsError);
        }
        gpsState.setClickable(false);

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
        gpsState.setChecked(isLocationEnabled(context));
        if (gpsHandler.getLastKnownLocation() == null) {
            setColor(Color.RED);
            return;
        }
        setColor(Color.BLACK);
        gpsState.setChecked(isLocationEnabled(context));
        latView.setText(Formatters.formatNS(gpsHandler.getLastKnownLocation().getLatitude()));
        lonView.setText(Formatters.formatEW(gpsHandler.getLastKnownLocation().getLongitude()));
        speedKtsView.setText(Formatters.formatSpeedKts(gpsHandler.getSpeedKts()));
        speedMphView.setText(Formatters.formatSpeedMph(gpsHandler.getSpeedMph()));
        headingView.setText(Formatters.formatHeadingTrue(gpsHandler.getHeadingTrue()));
        timeView.setText(simpleDateFormat.format(new Date(gpsHandler.getLastKnownLocation().getTime())));
        updateBearing();
        return;
    }

    private void updateBearing() {
        bearingView.setText(Formatters.formatBearingMag(bearing));
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // get the angle around the z-axis rotated
        bearing = Math.round(event.values[0]);
        updateBearing();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
