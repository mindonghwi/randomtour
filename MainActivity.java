package com.minyongcross.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText arAddrTxt1;
    private EditText arAddrTxt2;
    private EditText arAddrTxt3;
    private EditText arAddrTxt4;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arAddrList;

    private EditText eMapXText;
    private EditText eMapYText;
    private EditText eMapDisText;


    private String url1 = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/locationBasedList";
    private String strServiceKey = "?ServiceKey="+"tA3Bvd%2Fqrx%2Fhw2TA2I2Q5eXvsnFHGjX03lreQhcDZor%2BqXNPq3Kt%2FS0o6k4Uu554fbJiUWaRphE1uRzWi3OULQ%3D%3D";
    private String url2 = "&contentTypeId=";
    private String sMapXPos = "&mapX=";
    private String sMapYPos = "&mapY=";
    private String sDis = "&radius=";
    private String url3 = "&listYN=Y&MobileOS=AND&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=100&pageNo=";
    private HandleXml obj;

    Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eMapDisText = (EditText)findViewById(R.id.txtDisc);
        eMapYText = (EditText)findViewById(R.id.txtYPos1);
        eMapXText = (EditText)findViewById(R.id.txtXPos1);

        listView = (ListView)findViewById(R.id.listView1);
        arAddrList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arAddrList);

        Intent intent = getIntent();
        String strDis = intent.getStringExtra("DisPassWord");

        eMapDisText.setText(strDis);




        button1 = (Button) findViewById(R.id.btnSearchData);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String finalUrl = url1 + strServiceKey + url2+sMapXPos + eMapXText.getText() + sMapYPos + eMapYText.getText() + sDis + eMapDisText.getText()  + url3;
                obj = new HandleXml(finalUrl);
                obj.fetchXML();

                while (obj.parsingComplete);

                arAddrList.addAll(obj.getArAddrList());

            }
        });
        listView.setAdapter(adapter);

    }

    @Override
    protected void onStart(){
        super.onStart();
        getDelegate().onStart();

    }
}
