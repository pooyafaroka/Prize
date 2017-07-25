package com.irancell.prize.Views.Activity;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.irancell.prize.Presenters.IRefreshViewBySensor;
import com.irancell.prize.Presenters.Presenter;
import com.irancell.prize.R;
import com.irancell.prize.Models.Harwares.CameraDevice;

import static com.irancell.prize.Models.Harwares.CameraDevice.getCameraInstance;

public class MainActivity extends Activity implements IRefreshViewBySensor {

    private TextView tvAxisX;
    private TextView tvAxisY;
    private TextView tvAxisZ;
    private TextView tvHeading;
    private ImageView ivCompass;

    private Camera mCamera;
    private CameraDevice mPreview;
    private MainActivity mContext;
    private Presenter.Sensors sensors;
    private ImageView ivflag;
    private int mWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

        tvAxisX = (TextView) findViewById(R.id.tvAxisX);
        tvAxisY = (TextView) findViewById(R.id.tvAxisY);
        tvAxisZ = (TextView) findViewById(R.id.tvAxisZ);
        tvHeading = (TextView) findViewById(R.id.tvHeading);
        ivCompass = (ImageView) findViewById(R.id.ivCompass);
        FrameLayout flFlag = (FrameLayout) findViewById(R.id.flFlag);

        //Create camera preview
        mCamera = getCameraInstance();
        mPreview = new CameraDevice(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        sensors = (new Presenter()).new Sensors();
        sensors.Inital(mContext);

        ivflag = new ImageView(mContext);
        ivflag.setImageResource(R.drawable.ic_compass);
        flFlag.addView(ivflag, 0, new LinearLayout.LayoutParams(40, 40));
        mWidth = this.getResources().getDisplayMetrics().widthPixels;
        int mHeight = this.getResources().getDisplayMetrics().heightPixels;
        ivflag.setTranslationX(mWidth / 2);
        ivflag.setTranslationY(100);

    }

    float dCurrent = -1000;

    @Override
    public void RefreshViewBySensor(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];

        tvAxisX.setText(String.valueOf(x));
        tvAxisY.setText(String.valueOf(y));
        tvAxisZ.setText(String.valueOf(z));

        float degree = Math.round(sensorEvent.values[0]);
        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");

        float n = 21.9f;
        if(dCurrent == -1000)
        {
            dCurrent = degree;
        }
        else if(dCurrent > degree)
        {
            float diff = (mWidth / 2) + n * (dCurrent - degree);
            ivflag.setTranslationX(diff);
        }
        else if(dCurrent == degree)
        {

        }
        else
        {
            float diff = (mWidth / 2) + n * (dCurrent - degree);
            ivflag.setTranslationX(diff);
        }


    }

    @Override
    public void RefreshViewByCompass(RotateAnimation rotateAnimation) {
        ivCompass.startAnimation(rotateAnimation);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensors.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensors.onResume();
    }
}
