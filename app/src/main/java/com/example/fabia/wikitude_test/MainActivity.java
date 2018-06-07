package com.example.fabia.wikitude_test;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.wikitude.architect.ArchitectJavaScriptInterfaceListener;
import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements ArchitectViewHolderInterface{


    private ArchitectView architectView;
    private LocationProvider locationProvider;
    private long lastCalibrationToastShownTimeMillis = System.currentTimeMillis();
    protected ArchitectView.SensorAccuracyChangeListener sensorAccuracyListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){

            SensorManager sensorManager =
                    (SensorManager) getSystemService(SENSOR_SERVICE);

            Sensor gyroscopeSensor =
                    sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

            SensorEventListener gyroscopeSensorListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    // More code goes here
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            };

            sensorManager.registerListener(gyroscopeSensorListener,
                    gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);



            architectViewCall();
            if(ArchitectView.isDeviceSupported(this)){
                System.out.println("No soporta el dispocitivo");
            }else{
                System.out.println("SI soporta el dispocitivo");
            }


        }
        else{
            Toast.makeText(this, "Need camera for an AR experience", Toast.LENGTH_LONG);


        }



    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        architectView.onPostCreate();
        ARCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();

        architectView.onResume();

        locationProvider.onResume();
        if (this.sensorAccuracyListener!=null) {
            this.architectView.registerSensorAccuracyChangeListener( this.sensorAccuracyListener );
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        architectView.onPause();
        locationProvider.onPause();
        if (this.sensorAccuracyListener!=null) {
            this.architectView.registerSensorAccuracyChangeListener( this.sensorAccuracyListener );
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        architectView.onDestroy();

    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (this.architectView != null) {
            this.architectView.onLowMemory();
        }
    }


    protected void architectViewCall(){


        this.architectView = (ArchitectView)this.findViewById( R.id.architectView );
        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        config.setFeatures(ArchitectStartupConfiguration.Features.Geo);
        config.setLicenseKey("oWjZeBxLExDsXbbef5ylW6vZePQkJDE62FKnOp31hsz+EfEx6k4y8XJo3ir+3B9Vu3X8DAa60VH5gW/y5SjFsLZ4TWoHLcyLL/VFfmGtZNAPpN7jOk/xJ8rmTnVjE3W0iCbBJiD2WDqidCjSz5xRhWirT23wICtyPTCC05qyViRTYWx0ZWRfXwUhvhR3yQlpKY5VdWG6M+BLgaOMYQKgyMEMgJY51vVHJLaRW+KH9v7EhIONDB6xZXmIY87GCFPNhbPB5x8uHrm+beLEjBQvOHcwTg82m7mAg0TCWpw4Unh6VK8cLIruFHE1POkwGzUYevkYli1qYdjOi/wnpe/0S2RnAw36G5OQsVbgi/J7cxLoA8jGPr57IoKkBiP7nC+/BECQxtW8DryPFByZ6T/dyFK9Q0VBi2NGe2Ib4cOUC01Yt/kfqiDli5837Qn3Ii1pssMIbwK1zxHj0AO0ylfxiZ4l8pvb1RqieqilzSg2M7szwPSboI4WRLTqORGS9mPsW15A8MKpboAIuCI/g+oXAqtmuG0bw/iDvF6i5IevNDLRLsx22ipZtIqxZqQxxUOjmNo9YIjCpLp+UnhUHD2Zz9tzMFf9xwSuztwcLq89SCT3YZ55TvaycMztr9iouellYj6N/GB1C4231qvpMiPPZvf/sAaxpDueJP1QTjUkZEmbaAIRMOnoUMMciaRgxkOsJ+pVHFGwPnUvE9X7h0j7xQn8+BkDSsRVForVWPPd6MJahngefEyYR0wqpMUIVe5N");
        this.architectView.onCreate( config );

        locationProvider = new LocationProvider(this, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location!=null && MainActivity.this.architectView != null ) {
                    // check if location has altitude at certain accuracy level & call right architect method (the one with altitude information)
                    if ( location.hasAltitude() && location.hasAccuracy() && location.getAccuracy()<7) {
                        System.out.println("Location1: lat:"+location.getLatitude()+" , long:"+location.getLongitude()+" , altitud:"+location.getAltitude());
                        MainActivity.this.architectView.setLocation( location.getLatitude(), location.getLongitude(), location.getAltitude(), location.getAccuracy() );
                    } else {
                        System.out.println("Location2: lat:"+location.getLatitude()+" , long:"+location.getLongitude()+" , altitud:"+location.getAltitude());
                        MainActivity.this.architectView.setLocation( location.getLatitude(), location.getLongitude(), location.hasAccuracy() ? location.getAccuracy() : 1000 );
                    }


                }else{
                    System.out.println("No entro !!");
                }
            }

            @Override public void onStatusChanged(String s, int i, Bundle bundle) {}
            @Override public void onProviderEnabled(String s) {}
            @Override public void onProviderDisabled(String s) {}
        });

        this.sensorAccuracyListener = this.getSensorAccuracyListener();
        //this.architectView.registerSensorAccuracyChangeListener(new SensorAccuracyChangeListenerAdapter());


    }

    protected void ARCreate(){
        try{

            this.architectView.load( "file:///android_asset/04/index.html" );


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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
