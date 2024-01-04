package com.cantocrew.whenstartmotor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public final static int SPECIAL_GREEN=0xFF007733;
    public final static String ABOUT_MESSAGE_KEY ="com.cantocrew.whenstartmotor.ABOUT_MESSAGE_KEY";
    public final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    public final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    public final static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    private TextView mDepartureTime, mDepartureDate,mTotalDistance,mDesiredArrivalTime,mDesiredArrivalDate,mSlowSpeed, mFastSpeed;
    private TextView mStartMotorDate, mStartMotorTime, mActualETADate, mActualETATime, mHoursStats, mDistStats;
    private Button calculateButton;
    private TextView logTextView;
    private Logger log=null;
    private Calculator calc=new Calculator();
    private final AppCompatActivity context = this;
    private Button.OnClickListener calculateListener;
    private List<TextView> allFields;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculateButton = findViewById(R.id.Calculate);
        calculateListener = new Button.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                readFieldValues();
                paintFieldValues();
            }
        };
        calculateButton.setOnClickListener(calculateListener);
        allFields=Arrays.asList (
                mDepartureDate = findViewById(R.id.departureDate),
                mDepartureTime = findViewById(R.id.departureTime),
                mSlowSpeed = findViewById(R.id.slowSpeed),
                mFastSpeed = findViewById(R.id.maxSpeed),
                mDesiredArrivalDate = findViewById(R.id.desiredArrivalDate),
                mDesiredArrivalTime = findViewById(R.id.desiredArrivalTime),
                mTotalDistance = findViewById(R.id.totalDistance),
                mStartMotorDate = findViewById(R.id.startMotorDate),
                mStartMotorTime = findViewById(R.id.startMotorTime),
                mActualETADate = findViewById(R.id.actualETADate),
                mActualETATime = findViewById(R.id.actualETATime),
                mHoursStats = findViewById(R.id.sailToMotorHrsRatio),
                mDistStats = findViewById(R.id.sailToMotorDistRatio)
        );
        initializeFromPersistedStorage();
        paintFieldValues();
    }

    private void initializeFromPersistedStorage() {
        // TODO
    }
    private void writeToPersistedStorage() {
        // TODO
    }

    public void aboutButton(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        intent.putExtra(ABOUT_MESSAGE_KEY, "");
        startActivity(intent);
    }

    private void setFieldColors(int color) {
        for (TextView t : allFields)
            t.setTextColor(color);
    }

    private void readFieldValues() {
            calc.setStartTime(readTimestamp(mDepartureDate,mDepartureTime));
            calc.setDesiredEta(readTimestamp(mDesiredArrivalDate,mDesiredArrivalTime));
            calc.setTotalDistance(readDouble(mTotalDistance));
            calc.setMotoringSpeed(readDouble(mFastSpeed));
            calc.setSailSpeed(readDouble(mSlowSpeed));
    }

    private void paintFieldValues() {
        setFieldColors(Color.BLACK);
        paintTimestamp(mDepartureDate,mDepartureTime,calc.getStartTime());
        paintDistance(mTotalDistance,calc.getTotalDistance());
        paintTimestamp(mDesiredArrivalDate,mDesiredArrivalTime,calc.getDesiredEta());
        paintSpeed(mSlowSpeed,calc.getSailSpeed());
        paintSpeed(mFastSpeed,calc.getMotoringSpeed());
        paintTimestamp(mActualETADate,mActualETATime,calc.getArrivalTimeMs());
        paintRatio(mHoursStats,calc.getHoursSailing(), calc.getHoursMotoring(),"hrs");
        paintRatio(mDistStats,calc.getDistanceSailing(), calc.getDistanceMotoring(),"nm");
        if (calc.isAllSail()) {
            mStartMotorDate.setText("All Sail"); mStartMotorTime.setText("");
            colorTimestamp(mStartMotorDate,mStartMotorTime,SPECIAL_GREEN);
            colorTimestamp(mDesiredArrivalTime,mDesiredArrivalDate,SPECIAL_GREEN);
            colorTimestamp(mActualETADate,mActualETATime,SPECIAL_GREEN);
        } else {
            paintTimestamp(mStartMotorDate, mStartMotorTime, calc.getStartMotorTimeMs());
            if (calc.isViable()) {
                colorTimestamp(mDesiredArrivalTime,mDesiredArrivalDate,Color.BLUE);
                colorTimestamp(mActualETADate,mActualETATime,Color.BLUE);
            }
            else {
                colorTimestamp(mDesiredArrivalTime,mDesiredArrivalDate,Color.RED);
                colorTimestamp(mActualETADate,mActualETATime,Color.RED);
            }
        }
        writeToPersistedStorage();
    }


    private final static String reformatDate(CharSequence dateStr) {
        return dateStr.toString().replace('-','/').replace(' ','/').replace('.','/');
    }
    private final static String reformatTime(CharSequence timeStr) {
        return timeStr.toString().replace('.',':').replace(' ',':').replace('-',':');
    }

    private final static long readTimestamp(TextView dateTV, TextView timeTV) {
        try {
            return dateTimeFormat.parse(reformatDate(dateTV.getText()) + " " + reformatTime(timeTV.getText())).getTime();
        } catch (ParseException pe) {
            colorTimestamp(dateTV,timeTV,Color.RED);
            pe.printStackTrace();
            return 0;
        }
    }
    private final static double readDouble(TextView speedTV) {
        try {
            return Double.parseDouble(speedTV.getText().toString());
        } catch (NumberFormatException pe) {
            speedTV.setTextColor(Color.RED);
            pe.printStackTrace();
            return 0;
        }
    }
    private final static void paintTimestamp(TextView dateTV, TextView timeTV, long ms) {
        dateTV.setText(dateFormat.format(new Date(ms)));
        timeTV.setText(timeFormat.format(new Date(ms)));
    }
    private final static void paintRatio(TextView ratioTV,double sail, double motor, String units) {
        double denominator = sail+motor;
        int ratio = (denominator>0)?(int)((sail*100.0)/denominator):-1;
        ratioTV.setText(String.format("%3.1f%s / %3.1f%s (%s%% sail)",sail,units,motor,units,ratio>=0?(""+ratio):"---"));
    }
    private final static void colorTimestamp(TextView dateTV, TextView timeTV, int color) {
        dateTV.setTextColor(color);
        timeTV.setTextColor(color);
    }
    private final static void paintDistance(TextView distTV, double d) {
        distTV.setText(String.format("%6.1f",d));
    }
    private final static void paintSpeed(TextView distTV, double d) {
        distTV.setText(String.format("%4.1f",d));
    }
}
