package com.minyongcross.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication.R;

import java.util.ArrayList;

/**
 * Created by 민동휘 on 2017-10-27.
 */

public class C_MAKECOURSE extends AppCompatActivity {

    private ArrayList<String> arStrURL;
    private int nCourseCount;
    private ArrayAdapter<Double> adapter;
    private ArrayAdapter<String> adapter1;

    public static C_PLACE[] m_arPlace;
    private double m_dCurrentPosX;
    private double m_dCurrentPosY;

    public void setM_dCurrentPosX(double m_dCurrentPosX) {
        this.m_dCurrentPosX = m_dCurrentPosX;
    }
    public void setM_dCurrentPosY(double m_dCurrentPosY) {
        this.m_dCurrentPosY = m_dCurrentPosY;
    }



    private ArrayList<Double> ardList = new ArrayList<Double>();
    private ArrayList<String> arsList = new ArrayList<String>();

    private C_LISTVIEWADAPTER cListviewadapter = new C_LISTVIEWADAPTER();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lymaketourcourse);

        Intent intent = getIntent();
        arStrURL = new ArrayList<String>();
        nCourseCount = intent.getIntExtra("CourseCount",0);

        setM_dCurrentPosX(intent.getDoubleExtra("MyMapX",0.0));
        setM_dCurrentPosY(intent.getDoubleExtra("MyMapY",0.0));

        ListView listView = (ListView)findViewById(R.id.tourCourselist1);

        m_arPlace = new C_PLACE[nCourseCount];

        listView.setAdapter(cListviewadapter);


        arStrURL.addAll(intent.getStringArrayListExtra("arStrList"));
        for (int i = 0; i< nCourseCount ; i++){
            double dDistanse;
            m_arPlace[i] = new C_PLACE();
            m_arPlace[i].init(arStrURL.get(i));

            dDistanse = Math.sqrt((m_arPlace[i].getM_dMapX() - m_dCurrentPosX)*(m_arPlace[i].getM_dMapX() - m_dCurrentPosX)
            + (m_arPlace[i].getM_dMapY()-m_dCurrentPosY)*(m_arPlace[i].getM_dMapY()-m_dCurrentPosY));
            m_arPlace[i].setM_dDistance(dDistanse);

            ardList.add(m_arPlace[i].getM_dDistance());
            arsList.add(m_arPlace[i].getM_sOverView());

        }

        //거리찾기
        for (int i = 0; i< nCourseCount;i++){
            int nBiggerDistance = 0;
            for (int j=0; j<nCourseCount;j++){
                if (m_arPlace[i].getM_dDistance() > m_arPlace[j].getM_dDistance() ){
                    nBiggerDistance++;
                }
            }
            m_arPlace[i].setnNearDistance(nBiggerDistance);
        }


        for (int i = 0; i< nCourseCount ; i++) {
            cListviewadapter.addItem(m_arPlace[i].getM_sImageURLData(),
                    m_arPlace[i].getM_sTitle(),m_arPlace[i].getM_sOverView());
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }




}
