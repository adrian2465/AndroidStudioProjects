package com.cantocrew.whenstartmotor;

public class Calculator {
    public static final long ONE_SECOND = 1000L;
    public static final long ONE_MINUTE = ONE_SECOND * 60L;
    public static final long ONE_HOUR = ONE_MINUTE * 60;

    private double totalDistance = 0;
    private double sailSpeed = 0.0;
    private double motoringSpeed = 5.0;
    private long startTime = System.currentTimeMillis();
    private long desiredEta = startTime;
    private long arrivalTimeMs;

    private double distanceSailing;
    private long startMotorTime;
    private boolean allSail;
    private boolean viable;

    public Calculator() {
        execute();
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public double getSailSpeed() {
        return sailSpeed;
    }

    public double getMotoringSpeed() {
        return motoringSpeed;
    }

    public long getStartTime() {
        return startTime;
    }

    private final static double ms2hr(long ms) {
        return (double) ms / ONE_HOUR;
    }

    private final static long hr2ms(double hr) {
        return (long) (hr * ONE_HOUR);
    }

    private void execute() {
        double desiredTransitTimeHours = ms2hr(desiredEta - startTime);
        if (totalDistance / motoringSpeed <= desiredTransitTimeHours) {
            if (motoringSpeed > sailSpeed)
                distanceSailing = sailSpeed * (motoringSpeed * desiredTransitTimeHours - totalDistance) / (motoringSpeed - sailSpeed);
            else
                distanceSailing = totalDistance;
            if (distanceSailing < totalDistance) {
                startMotorTime = startTime + hr2ms(distanceSailing / sailSpeed);
                allSail = false;
            } else {
                distanceSailing = totalDistance;
                allSail = true;
            }
            arrivalTimeMs = startTime + hr2ms(distanceSailing / sailSpeed + (totalDistance - distanceSailing) / motoringSpeed);
            if (allSail) startMotorTime = arrivalTimeMs;
            viable = true;
        } else {
            viable = false;
            arrivalTimeMs = startTime + hr2ms((totalDistance - distanceSailing) / motoringSpeed);
            allSail = false;
            startMotorTime = startTime;
            distanceSailing = 0;
        }
    }

    public long getStartMotorTimeMs() {
        return startMotorTime;
    }

    public long getArrivalTimeMs() {
        return arrivalTimeMs;
    }

    public double getDistanceSailing() {
        return distanceSailing;
    }

    public double getDistanceMotoring() { return totalDistance - distanceSailing; }

    public boolean isAllSail() {
        return allSail;
    }

    public double getHoursSailing() {
        return ms2hr(Math.max(startMotorTime - startTime, 0));
    }

    public double getHoursMotoring() {
        return ms2hr(Math.max(arrivalTimeMs - startMotorTime, 0));
    }

    public double getSailToMotorHoursRatio() {
        double total = getHoursSailing() + getHoursMotoring();
        if (total > 0) return getHoursSailing() / total;
        else return -1;
    }

    public double getSailToMotorDistRatio() {
        double total = getHoursSailing() + getHoursMotoring();
        if (total > 0) return getHoursSailing() / total;
        else return -1;
    }

    public void setTotalDistance(double d) {
        totalDistance = d;
        execute();
    }

    public void setSailSpeed(double s) {
        sailSpeed = s;
        execute();
    }

    public void setMotoringSpeed(double s) {
        motoringSpeed = s;
        execute();
    }

    public void setStartTime(long t) {
        startTime = t;
        execute();
    }

    public void setDesiredEta(long t) {
        desiredEta = t;
        execute();
    }

    public long getDesiredEta() {
        return desiredEta;
    }

    public boolean isViable() {
        return viable;
    }
}
