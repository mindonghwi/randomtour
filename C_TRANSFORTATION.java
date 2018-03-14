package com.minyongcross.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.R;

/**
 * Created by 민동휘 on 2017-10-17.
 */

public class C_TRANSFORTATION extends AppCompatActivity {
    private String stUserTransfortation;
    private ImageButton btnWalking;
    private ImageButton btnBicycle;
    private ImageButton btnBus;

    private double dLatitude;
    private double dLongitude;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen2);

        btnWalking = (ImageButton)findViewById(R.id.btnIdWalking);
        btnBicycle = (ImageButton)findViewById(R.id.btnIdBicycle);
        btnBus = (ImageButton)findViewById(R.id.btnIdBus);

        Intent Subintent = getIntent();
        dLatitude = Subintent.getDoubleExtra("dLatitude",0.0);
        dLongitude = Subintent.getDoubleExtra("dLongitude",0.0);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnIdWalking:
                        stUserTransfortation = "2000";
                        break;
                    case R.id.btnIdBicycle:
                        stUserTransfortation = "4000";
                        break;
                    case R.id.btnIdBus:
                        stUserTransfortation = "8000";
                        break;
                }

                Intent intent = new Intent(getApplicationContext(),C_SELECTTOURPLACE.class);
                intent.putExtra("DisPassWord",stUserTransfortation);

                intent.putExtra("dLatitude", dLatitude);
                intent.putExtra("dLongitude", dLongitude);

                startActivity(intent);
            }
        };

        btnWalking.setOnClickListener(listener);
        btnBicycle.setOnClickListener(listener);
        btnBus.setOnClickListener(listener);

    }




}
