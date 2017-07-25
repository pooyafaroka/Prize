package com.irancell.prize.Models.Harwares;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.irancell.prize.Presenters.IRefreshCompass;
import com.irancell.prize.Views.Activity.MainActivity;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by Pooya on 7/25/2017.
 */

public class Accelerometer implements SensorEventListener {
    private final Context mContext;
    private MainActivity mainActivity;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private SensorManager mSensorManager;
    private IRefreshCompass iRefreshCompass;
    private float currentDegree = 0f;

    public Accelerometer(Context mContext)
    {
        this.mContext = mContext;
        mSensorManager = (SensorManager) this.mContext.getSystemService(SENSOR_SERVICE);
    }

    public Accelerometer(Context mContext, MainActivity mainActivity) {
        this.mContext = mContext;
        mSensorManager = (SensorManager) this.mContext.getSystemService(SENSOR_SERVICE);
        this.mainActivity = mainActivity;
        iRefreshCompass = mainActivity;
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float degree = Math.round(sensorEvent.values[0]);
        iRefreshCompass.RefreshSensorValues(sensorEvent);
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        ra.setDuration(210);
        ra.setFillAfter(true);
        iRefreshCompass.RefreshCompass(ra);
        currentDegree = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onPause() {
        mSensorManager.unregisterListener(this);
    }

    public void onResum() {
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }
}
