package com.cantocrew.whenstartmotor;

import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class Logger {
    TextView logTextView=null;
    protected Logger(TextView tv) {
        logTextView=tv;
        logTextView.setMovementMethod(new ScrollingMovementMethod());
    }
    public void logMessage(String m) {
        logTextView.append("== "+Formatters.formatTime(System.currentTimeMillis())+"\n"+ m+"\n");
    }
}
