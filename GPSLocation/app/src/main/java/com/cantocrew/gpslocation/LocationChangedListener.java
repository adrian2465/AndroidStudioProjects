package com.cantocrew.gpslocation;

import android.location.Location;

public interface LocationChangedListener {
    public void locationChanged(Location location);
    public void gpsStatusChanged(boolean status);
}
