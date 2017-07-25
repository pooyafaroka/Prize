package com.irancell.prize.Views.Activity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.irancell.prize.Models.Harwares.Accelerometer;
import com.irancell.prize.Presenters.IRefreshCompass;
import com.irancell.prize.R;
import com.irancell.prize.Models.Harwares.CameraDevice;

import static com.irancell.prize.Models.Harwares.CameraDevice.getCameraInstance;

public class MainActivity extends Activity implements IRefreshCompass{

    private TextView tvAxisX;
    private TextView tvAxisY;
    private TextView tvAxisZ;
    private TextView tvHeading;
    private ImageView ivCompass;

    private Camera mCamera;
    private CameraDevice mPreview;
    private Accelerometer accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context mContext = MainActivity.this;

        tvAxisX = (TextView) findViewById(R.id.tvAxisX);
        tvAxisY = (TextView) findViewById(R.id.tvAxisY);
        tvAxisZ = (TextView) findViewById(R.id.tvAxisZ);
        tvHeading = (TextView) findViewById(R.id.tvHeading);
        ivCompass = (ImageView) findViewById(R.id.ivCompass);

        //Create camera preview
        mCamera = getCameraInstance();
        mPreview = new CameraDevice(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        accelerometer = new Accelerometer(mContext, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        accelerometer.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        accelerometer.onResum();
    }

    @Override
    public void RefreshCompass(RotateAnimation rotateAnimation) {
        ivCompass.startAnimation(rotateAnimation);
    }

    @Override
    public void RefreshSensorValues(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];

        tvAxisX.setText(String.valueOf(x));
        tvAxisY.setText(String.valueOf(y));
        tvAxisZ.setText(String.valueOf(z));

        float degree = Math.round(sensorEvent.values[0]);
        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");
    }
}
