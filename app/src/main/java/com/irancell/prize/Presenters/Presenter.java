package com.irancell.prize.Presenters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.SensorEvent;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.view.animation.RotateAnimation;

import com.irancell.prize.Models.Harwares.Accelerometer;
import com.irancell.prize.Models.Harwares.GPSDevice;
import com.irancell.prize.Views.Activity.MainActivity;
import com.irancell.prize.Views.Activity.SimpleCamera;

import static com.irancell.prize.Views.Activity.SimpleCamera.PERMISSIONS_REQUEST_LOCATION;

/**
 * Created by Pooya on 7/25/2017.
 */

public class Presenter {

    public class LocationListener implements IRefreshViewByLocation
    {
        private IRefreshViewByLocation mContext;

        public void requestLocationUpdates(Activity mContext) {
            this.mContext = (IRefreshViewByLocation) mContext;
            LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            boolean permissionGranted = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            if(permissionGranted) {
                GPSDevice locationListener = new GPSDevice(this);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
            } else {
                ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);
            }
        }

        @Override
        public void onChangeLocation(double lat, double lng) {
            mContext.onChangeLocation(lat, lng);
        }
    }

    public class Sensors implements IRefreshPresenterBySensor
    {
        private MainActivity mainActivity;
        private Accelerometer accelerometer;
        private IRefreshViewBySensor iRefreshViewBySensor;

        public void Inital(Context mContext) {
            accelerometer = new Accelerometer(mContext, this);
            mainActivity = (MainActivity)mContext;
            iRefreshViewBySensor = mainActivity;
        }

        public void onPause() {
            accelerometer.onPause();
        }

        public void onResume() {
            accelerometer.onResum();
        }

        @Override
        public void RefreshCompass(RotateAnimation rotateAnimation) {
            iRefreshViewBySensor.RefreshViewByCompass(rotateAnimation);
        }

        @Override
        public void RefreshSensorValues(SensorEvent sensorEvent) {
            iRefreshViewBySensor.RefreshViewBySensor(sensorEvent);
        }
    }


}


