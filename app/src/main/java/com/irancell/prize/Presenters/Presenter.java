package com.irancell.prize.Presenters;

import android.content.Context;
import android.hardware.SensorEvent;
import android.view.animation.RotateAnimation;

import com.irancell.prize.Models.Harwares.Accelerometer;
import com.irancell.prize.Views.Activity.MainActivity;

/**
 * Created by Pooya on 7/25/2017.
 */

public class Presenter {


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


