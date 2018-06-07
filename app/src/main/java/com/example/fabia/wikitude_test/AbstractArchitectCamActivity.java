package com.example.fabia.wikitude_test;

import android.app.Activity;
import android.location.LocationListener;

import com.wikitude.architect.ArchitectJavaScriptInterfaceListener;
import com.wikitude.architect.ArchitectView;

/**
 * Created by fabia on 30/05/2018.
 */

public abstract class AbstractArchitectCamActivity implements ArchitectViewHolderInterface {


    protected ArchitectView.SensorAccuracyChangeListener sensorAccuracyListener;

    @Override
    public String getARchitectWorldPath() {
        return null;
    }

    @Override
    public ArchitectJavaScriptInterfaceListener getArchitectJavaScriptInterfaceListener() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    public String getWikitudeSDKLicenseKey() {
        return null;
    }

    @Override
    public int getArchitectViewId() {
        return 0;
    }

    @Override
    public ILocationProvider getLocationProvider(LocationListener locationListener) {
        return null;
    }

    @Override
    public ArchitectView.SensorAccuracyChangeListener getSensorAccuracyListener() {
        return null;
    }

    @Override
    public float getInitialCullingDistanceMeters() {
        return 0;
    }

    @Override
    public ArchitectView.ArchitectWorldLoadedListener getWorldLoadedListener() {
        return null;
    }
}
