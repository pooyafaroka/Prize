package com.irancell.prize.Models.Harwares;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.irancell.prize.Presenters.Presenter;
import com.irancell.prize.Views.Activity.SimpleCamera;

/**
 * Created by Pooya on 7/3/2017.
 */

public class GPSDevice implements LocationListener {

    private final Presenter.LocationListener mLocationListener;

    public GPSDevice(Presenter.LocationListener mLocationListener) {
        this.mLocationListener = mLocationListener;
    }

    @Override
    public void onLocationChanged(Location loc) {
        mLocationListener.onChangeLocation(loc.getLatitude(), loc.getLongitude());
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

}

