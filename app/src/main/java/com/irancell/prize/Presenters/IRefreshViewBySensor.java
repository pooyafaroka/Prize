package com.irancell.prize.Presenters;

import android.hardware.SensorEvent;
import android.view.animation.RotateAnimation;

/**
 * Created by Pooya on 7/25/2017.
 */
public interface IRefreshViewBySensor {

    void RefreshViewBySensor(SensorEvent sensorEvent);
    void RefreshViewByCompass(RotateAnimation rotateAnimation);
}
