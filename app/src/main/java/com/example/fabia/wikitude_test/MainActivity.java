package com.example.fabia.wikitude_test;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ArchitectView architectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
            architectViewCall();
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
    }

    @Override
    protected void onPause() {
        super.onPause();

        architectView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        architectView.onDestroy();
    }


    protected void architectViewCall(){


        this.architectView = (ArchitectView)this.findViewById( R.id.architectView );
        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        config.setLicenseKey("R08D0eDc1N7IMwdZgBYzDGup+ts9fa6EUnxPfhOZ7pzCvLGoS/byd/xNy776d/bXQLRP/R7K6Jc405BUaIpYuHVfzfmdpDxIuT6w/yoQ0EFpXYpMGBMeLH9R11Mmo2jUbvcMnvlmOyT1DIgpf8VOLKMOkGZYrtIX57Ondr+4wAdTYWx0ZWRfX703Z0ItAdZWC3nvcaSHAxENOyNzVFpQtwBQeUHIW9JLjZnY3kvXIiYncy3tkHKqiposNX8arwUyBIdeFThiszk5CJCNbpLwU6n27msi1Vgor1AXCc5zz011OVu0mBR6Irb6CXVuVWUKPkrMXLL2YyRz/eADamLn0uUic3paG2hwVHfUEnSK7yJfojRUAavch5J8XBy5wENUbK8+k4CHJHcgpC+JBdjpY9Bb3Ymf808N5AKQ1jg37MM8llWKpfov+5vkl+0jUXX8zPGie2dlEuXjcJk+/826xSeFm2ubLDON3uHu0fojX8IaEuS0u4cY22T6OO2IIBvvbVfPbj1qf7ba4fJZmBgg7o1953D9oxXQIQLvul1rUmX5iKJwbG/Ivdk3HNcy+ayt0G6LcwAw3SGsE7Pzm51wStYiEPenamdhCkimfUrMCrEuMuWx+aUvpnl/17LMP8GkURHnBmxf8n4JeBFgEJVjJwYS7z2Rk8lvasTukXAuRTE=");
        this.architectView.onCreate( config );

    }

    protected void ARCreate(){
        try{

            this.architectView.load( "index.html" );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
