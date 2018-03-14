package com.minyongcross.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;

/**
 * Created by 민동휘 on 2017-10-19.
 */

public class C_TOURCOURSE extends AppCompatActivity {
    private String strURL[];
    private ArrayList<String> arURL;
    private C_XMLHANDLEPLACE[] cXmlhandleplace;
    private double dMyMapX;
    private double dMyMapY;

    private double dDistance[];
    private double dDistance1[];
    private int arSortingIndex[] = new int[9];

    //mapx, mapy, overview, title, addr1,firstimage2
    private String arStrTitle[];
    private String arStrMapx[];
    private String arStrMapy[];
    private String arStrOverview[];
    private String arStrFirstImage[];

    private int nCourseCount;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.texttest);

        init();

    }


    private void init(){

        Intent intent = getIntent();
        nCourseCount = intent.getIntExtra("CourseCount",0);

        ArrayList<String> arrayList = new ArrayList<String>();
        strURL = new String[nCourseCount];
        arURL = new ArrayList<String>();
        TextView txtv = (TextView)findViewById(R.id.txtex1);

        for (int i = 0;i<nCourseCount;i++){
            //strURL[i] = intent.getStringExtra("TourPlace"+i);
            arURL.add(intent.getStringExtra("TourPlace"+i));

        }
        txtv.setText(arURL.get(0)+ "\n"+ arURL.get(1));


        dMyMapX = intent.getDoubleExtra("MyMapX",132.0);
        dMyMapY = intent.getDoubleExtra("MyMapY",32.0);

        dDistance = new double[nCourseCount];



        cXmlhandleplace = new C_XMLHANDLEPLACE[nCourseCount];
        for (int i = 0; i<nCourseCount;i++){
            //cXmlhandleplace[i] = new C_XMLHANDLEPLACE(strURL[i]);

            cXmlhandleplace[i] = new C_XMLHANDLEPLACE(arURL.get(i));
            cXmlhandleplace[i].fetchXML();
            while (cXmlhandleplace[i].parsingComplete) {}
        }

        sortDintance();

        arStrTitle = new String[nCourseCount];
        arStrFirstImage = new String[nCourseCount];
        arStrMapx= new String[nCourseCount];
        arStrMapy= new String[nCourseCount];
        arStrOverview= new String[nCourseCount];

        for (int i = 0 ; i< nCourseCount ; i++){
            arStrFirstImage[i] = cXmlhandleplace[i].getArFirstImage().toString();
            arStrMapx[i] = cXmlhandleplace[i].getArMapX().toString();
            arStrMapy[i]= cXmlhandleplace[i].getArMapY().toString();
            arStrOverview[i]= cXmlhandleplace[i].getArOverView().toString();
            arStrTitle[i]= cXmlhandleplace[i].getArTitle().toString();
        }


    }

    private void sortDintance(){
        for (int i = 0; i<dDistance.length;i++){
            dDistance[i] = Math.sqrt(
                    Math.pow(Double.parseDouble(String.valueOf(arStrMapx[i])) - dMyMapX,2)
                            + Math.pow(Double.parseDouble(String.valueOf(arStrMapy)) - dMyMapY,2)
            );
        }
        dDistance1 = dDistance;

        for (double digit : dDistance1){
            for (int i=0;i<dDistance1.length-1;i++){
                if (dDistance1[i] > dDistance1[i+1]){
                    double temp = dDistance1[i];
                    dDistance1[i] = dDistance1[i+1];
                    dDistance1[i+1] = temp;
                }
            }
        }

        for (int i = 0; i< arSortingIndex.length;i++){
            for (int j = i; j< arSortingIndex.length;j++){
                if (dDistance[j] == dDistance1[i]){
                    arSortingIndex[i] = j;
                }
            }
        }

    }

}
