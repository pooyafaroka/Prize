package com.irancell.prize.Views.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beyondar.android.fragment.BeyondarFragmentSupport;
import com.beyondar.android.world.GeoObject;
import com.beyondar.android.world.World;
import com.irancell.prize.Presenters.IRefreshViewByLocation;
import com.irancell.prize.Presenters.Presenter;
import com.irancell.prize.R;

/**
 * Created by Pooya on 7/26/2017.
 */

public class SimpleCamera extends FragmentActivity implements IRefreshViewByLocation, SeekBar.OnSeekBarChangeListener {
    private BeyondarFragmentSupport mBeyondarFragment;
    private TextView tvDistance;
    private World mWorld;
    public static final int PERMISSIONS_REQUEST_LOCATION = 99;
    private SeekBar mSeekBarPullCloserDistance;
    private SeekBar mSeekBarPushAwayDistance;
    private SeekBar mSeekBarMaxDistanceToRender;
    private SeekBar mSeekBarDistanceFactor;
    private TextView tvProgress;
    private double lat_obj_1;
    private double lng_obj_1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_camera);

        Context context = SimpleCamera.this;

        //Define Components
        tvDistance = (TextView) findViewById(R.id.tvDistance);
        tvProgress = (TextView) findViewById(R.id.tvProgress);
        mBeyondarFragment = (BeyondarFragmentSupport) getSupportFragmentManager().findFragmentById(R.id.beyondarFragment);
        mSeekBarPullCloserDistance = (SeekBar) findViewById(R.id.mSeekBarPullCloserDistance);
        mSeekBarPushAwayDistance = (SeekBar) findViewById(R.id.mSeekBarPushAwayDistance);
        mSeekBarMaxDistanceToRender = (SeekBar) findViewById(R.id.mSeekBarMaxDistanceToRender);
        mSeekBarDistanceFactor = (SeekBar) findViewById(R.id.mSeekBarDistanceFactor);

        mSeekBarPullCloserDistance.setMax(1000);
        mSeekBarPushAwayDistance.setMax(1000);
        mSeekBarMaxDistanceToRender.setMax(20000); // 20 km
        mSeekBarDistanceFactor.setMax(50000);


        mSeekBarPullCloserDistance.setOnSeekBarChangeListener(this);
        mSeekBarPushAwayDistance.setOnSeekBarChangeListener(this);
        mSeekBarMaxDistanceToRender.setOnSeekBarChangeListener(this);
        mSeekBarDistanceFactor.setOnSeekBarChangeListener(this);

        mSeekBarPushAwayDistance.setProgress(115);
        mSeekBarMaxDistanceToRender.setProgress(20000);
        mSeekBarDistanceFactor.setProgress(50000);

        Presenter.LocationListener locationListener = (new Presenter()).new LocationListener();
        locationListener.requestLocationUpdates(this);


        mWorld = new World(context);
        mWorld.setGeoPosition(35.780416d, 51.461392d);

        GeoObject go1 = new GeoObject(1l);
        lat_obj_1 = 35.780826d;
        lng_obj_1 = 51.460690d;
        go1.setGeoPosition(lat_obj_1, lng_obj_1);
        go1.setImageResource(R.drawable.ic_compass);
        go1.setName("Creature 1");

        mWorld.addBeyondarObject(go1);

        mBeyondarFragment.setWorld(mWorld);
        updateTextValues();
    }

    private void updateTextValues() {
        tvDistance.setText("dst factor=" + mBeyondarFragment.getDistanceFactor() + " max dst render="
                + mBeyondarFragment.getMaxDistanceToRender() + "\npull closer="
                + mBeyondarFragment.getPullCloserDistance() + " push away="
                + mBeyondarFragment.getPushAwayDistance());
    }

    @Override
    public void onChangeLocation(double lat, double lng) {
        mWorld.setGeoPosition(lat, lng);

        Location loc1 = new Location("");
        loc1.setLatitude(lat_obj_1);
        loc1.setLongitude(lng_obj_1);

        Location loc2 = new Location("");
        loc2.setLatitude(lat);
        loc2.setLongitude(lng);

        float distanceInMeters = loc1.distanceTo(loc2);
        tvProgress.setText(String.valueOf(distanceInMeters));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == mSeekBarPullCloserDistance) {
            mBeyondarFragment.setPullCloserDistance(progress);
        } else if (seekBar == mSeekBarPushAwayDistance) {
            mBeyondarFragment.setPushAwayDistance(progress);
        } else if (seekBar == mSeekBarMaxDistanceToRender) {
            mBeyondarFragment.setMaxDistanceToRender(progress);
        } else if (seekBar == mSeekBarDistanceFactor) {
            mBeyondarFragment.setDistanceFactor(progress);
        }
        updateTextValues();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    }
                    else {
                        Presenter.LocationListener locationListener = (new Presenter()).new LocationListener();
                        locationListener.requestLocationUpdates(this);
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
