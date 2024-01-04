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

public class CalcVectorActivity extends AppCompatActivity {
    private final static String DEGREE_CHAR="°";
    
    private EditText mSrcLatDeg;
    private EditText mSrcLatMin;
    private RadioButton mSrcNorthRadioButton;
    private RadioButton mSrcSouthRadioButton;

    private EditText mSrcLonDeg;
    private EditText mSrcLonMin;
    private RadioButton mSrcEastRadioButton;
    private RadioButton mSrcWestRadioButton;
    
    private EditText mDstLatDeg;
    private EditText mDstLatMin;
    private RadioButton mDstNorthRadioButton;
    private RadioButton mDstSouthRadioButton;

    private EditText mDstLonDeg;
    private EditText mDstLonMin;
    private RadioButton mDstEastRadioButton;
    private RadioButton mDstWestRadioButton;

    private Button calcVectorButton;
    private TextView mCalcVector;

    private Button gotoCalcDestButton;

    private Button.OnClickListener calcVectorListener = new Button.OnClickListener() {
        @Override
        public void onClick(@NonNull View v) {
            try {
                Coordinate src = new Coordinate(
                        new Ordinate(assembleOrdinate(mSrcLatDeg, mSrcLatMin, mSrcSouthRadioButton), OrdinateType.LAT),
                        new Ordinate(assembleOrdinate(mSrcLonDeg, mSrcLonMin, mSrcWestRadioButton), OrdinateType.LON));
                Coordinate dst = new Coordinate(
                        new Ordinate(assembleOrdinate(mDstLatDeg, mDstLatMin, mDstSouthRadioButton), OrdinateType.LAT),
                        new Ordinate(assembleOrdinate(mDstLonDeg, mDstLonMin, mDstWestRadioButton), OrdinateType.LON));
                Vector courseVector = Vector.getVector(src,dst);
                System.out.println("src="+src+", dst="+dst+", v="+courseVector);
                mCalcVector.setText(courseVector.toString());
            } catch (Exception e) {
                mCalcVector.setText("Error: "+e.getMessage());
            }
        }
    };
    private final AppCompatActivity context = this;
    private Button.OnClickListener gotoCalcDestListener = new Button.OnClickListener() {
        @Override
        public void onClick(@NonNull View v) {
            startActivity(new Intent(context, CalcDestActivity.class));
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
        setContentView(R.layout.activity_calc_vector);
        mSrcLatDeg = findViewById(R.id.SrcLatDeg2);
        mSrcLatMin = findViewById(R.id.SrcLatMin2);
        mSrcNorthRadioButton = findViewById(R.id.SrcNorthRadioButton2);
        mSrcSouthRadioButton = findViewById(R.id.SrcSouthRadioButton2);
        mSrcLonDeg = findViewById(R.id.SrcLonDeg2);
        mSrcLonMin = findViewById(R.id.SrcLonMin2);
        mSrcEastRadioButton = findViewById(R.id.SrcEastRadioButton2);
        mSrcWestRadioButton = findViewById(R.id.SrcWestRadioButton2);
        mDstLatDeg = findViewById(R.id.DstLatDeg2);
        mDstLatMin = findViewById(R.id.DstLatMin2);
        mDstNorthRadioButton = findViewById(R.id.DstNorthRadioButton2);
        mDstSouthRadioButton = findViewById(R.id.DstSouthRadioButton2);
        mDstLonDeg = findViewById(R.id.DstLonDeg2);
        mDstLonMin = findViewById(R.id.DstLonMin2);
        mDstEastRadioButton = findViewById(R.id.DstEastRadioButton2);
        mDstWestRadioButton = findViewById(R.id.DstWestRadioButton2);
        mCalcVector = findViewById(R.id.ResultVectorValue);
        calcVectorButton = findViewById(R.id.CalcButton2);
        calcVectorButton.setOnClickListener(calcVectorListener);
        gotoCalcDestButton = findViewById(R.id.gotoCalcDestButton);
        gotoCalcDestButton.setOnClickListener(gotoCalcDestListener);
    }
}
