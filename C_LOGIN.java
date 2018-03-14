package com.minyongcross.myapplication;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.myapplication.R;


/**
 * Created by 민동휘 on 2017-10-16.
 */

public class C_LOGIN extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;

    private Double dLatitude;
    private Double dLongitude;
    private Button btnLogin;
    public Double getdLatitude() {
        return dLatitude;
    }

    public Double getdLongitude() {
        return dLongitude;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);

        btnLogin = (Button)findViewById(R.id.btnLogin);

        ImageView mainvideo = (ImageView) findViewById(R.id.gif_image);
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(mainvideo);
        Glide.with(this).load(R.drawable.mainvideo).into(gifImage);

        Location location = new Location(LOCATION_SERVICE);
        location.setLatitude(32.5846);
        location.setLongitude(128.548135);

        dLatitude = location.getLatitude();
        dLongitude = location.getLongitude();


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //textView.append("\n"+location.getLatitude() + " "+ location.getLongitude());
                dLatitude = location.getLatitude();
                dLongitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                },10);
                return;
            }else {
                locationManager.requestLocationUpdates("gps", 5, 1, locationListener);

            }
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(getApplicationContext(),C_TRANSFORTATION.class);
                Intent intent = new Intent(getApplicationContext(),C_DIRECTION.class);
                intent.putExtra("dLatitude", dLatitude);
                intent.putExtra("dLongitude", dLongitude);
                startActivity(intent);

            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                }
                return;

        }
    }

}
