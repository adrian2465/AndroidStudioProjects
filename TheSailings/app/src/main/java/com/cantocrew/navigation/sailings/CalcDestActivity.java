package com.cantocrew.navigation.sailings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class CalcDestActivity extends AppCompatActivity {
    private final static String DEGREE_CHAR="°";
    private EditText mSrcLatDeg;
    private EditText mSrcLatMin;
    private RadioButton mSrcNorthRadioButton;
    private RadioButton mSrcSouthRadioButton;

    private EditText mSrcLonDeg;
    private EditText mSrcLonMin;
    private RadioButton mSrcEastRadioButton;
    private RadioButton mSrcWestRadioButton;

    private EditText mCourseDeg;
    private EditText mDist;

    private Button calcDestCoordButton;
    private TextView mCalcDestCoord;

    private Button gotoCalcVectorButton;

    private Button.OnClickListener calcDestCoordListener = new Button.OnClickListener() {
        @Override
        public void onClick(@NonNull View v) {
            try {
                Coordinate src = new Coordinate(
                        new Ordinate(assembleOrdinate(mSrcLatDeg, mSrcLatMin, mSrcSouthRadioButton), OrdinateType.LAT),
                        new Ordinate(assembleOrdinate(mSrcLonDeg, mSrcLonMin, mSrcWestRadioButton), OrdinateType.LON));
                Vector courseVector = new Vector(doubleFromEditText(mCourseDeg), doubleFromEditText(mDist));
                mCalcDestCoord.setText(Vector.getCoordinate(src, courseVector).toString());
            } catch (Exception e) {
                mCalcDestCoord.setText("Error: "+e.getMessage());
            }
        }
    };
    private final AppCompatActivity context = this;
    private Button.OnClickListener gotoCalcVectorListener = new Button.OnClickListener() {
        @Override
        public void onClick(@NonNull View v) {
            startActivity(new Intent(context, CalcVectorActivity.class));
        }
    };

    private double doubleFromEditText(EditText mCourseDeg) {
        return Double.parseDouble(mCourseDeg.getText().toString());
    }

    private double assembleOrdinate(EditText mSrcLatDeg, EditText mSrcLatMin, RadioButton negatingRadioButton) {
        return (negatingRadioButton.isChecked()?-1:1)*(doubleFromEditText(mSrcLatDeg) + doubleFromEditText(mSrcLatMin)/60d);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_dest);
        mSrcLatDeg = findViewById(R.id.SrcLatDeg);
        mSrcLatMin = findViewById(R.id.SrcLatMin);
        mSrcNorthRadioButton = findViewById(R.id.SrcNorthRadioButton);
        mSrcSouthRadioButton = findViewById(R.id.SrcSouthRadioButton);
        mSrcLonDeg = findViewById(R.id.SrcLonDeg);
        mSrcLonMin = findViewById(R.id.SrcLonMin);
        mSrcEastRadioButton = findViewById(R.id.SrcEastRadioButton);
        mSrcWestRadioButton = findViewById(R.id.SrcWestRadioButton);
        mCourseDeg = findViewById(R.id.CourseDeg);
        mDist = findViewById(R.id.Dist);
        mCalcDestCoord = findViewById(R.id.LatLonResultValue);
        calcDestCoordButton = findViewById(R.id.CalcButton);
        calcDestCoordButton.setOnClickListener(calcDestCoordListener);
        gotoCalcVectorButton = findViewById(R.id.gotoCalcVectorButton);
        gotoCalcVectorButton.setOnClickListener(gotoCalcVectorListener);
    }
}
