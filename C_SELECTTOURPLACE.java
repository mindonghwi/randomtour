package com.minyongcross.myapplication;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by 민동휘 on 2017-10-17.
 */

public class C_SELECTTOURPLACE extends AppCompatActivity {
    private String url1 = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/locationBasedList";
    private String strServiceKey = "?ServiceKey=" + "tA3Bvd%2Fqrx%2Fhw2TA2I2Q5eXvsnFHGjX03lreQhcDZor%2BqXNPq3Kt%2FS0o6k4Uu554fbJiUWaRphE1uRzWi3OULQ%3D%3D";
    private String url2 = "&contentTypeId=";
    private String sMapXPos = "&mapX=";
    private String sMapYPos = "&mapY=";
    private String sDis = "&radius=";
    private String url3 = "&listYN=Y&MobileOS=AND&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=100&pageNo=";
    private C_HANDLEXMLMAKECATEGORY cHandleXmlCategory;

    private ArrayList<String> arContentsId;
    private ArrayList<String> arContentsTyped;


    private Double dLatitude = 126.981106;
    private Double dLongitude = 37.568477;
    private int nSelectCount;
    private boolean arbSelectedCard[];
    private String finalUrl;
    private String strDis;
    private int nCardIndex;

    private int arSelectedItem[];

    private String arstrURL[];
    private ImageButton btnCard[];
    private ImageButton btnNext;
    private View.OnClickListener listener;
    private int nCourseCount;
    private ArrayList<String> arlstrURL = new ArrayList<String>();
    private Location m_lLocation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selecttour);

        Intent intent = getIntent();
        strDis = intent.getStringExtra("DisPassWord");
        dLatitude = intent.getDoubleExtra("dLatitude",126.981106);
        dLongitude = intent.getDoubleExtra("dLongitude",37.568477);


        init();


        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnTourSelect1:
                        nSelectCount=0;
                        break;
                    case R.id.btnTourSelect2:
                        nSelectCount=1;
                        break;
                    case R.id.btnTourSelect3:
                        nSelectCount=2;
                        break;
                    case R.id.btnTourSelect4:
                        nSelectCount=3;
                        break;
                    case R.id.btnTourSelect5:
                        nSelectCount=4;
                        break;
                    case R.id.btnTourSelect6:
                        nSelectCount=5;
                        break;
                    case R.id.btnTourSelect7:
                        nSelectCount=6;
                        break;
                    case R.id.btnTourSelect8:
                        nSelectCount=7;
                        break;
                    case R.id.btnTourSelect9:
                        nSelectCount=8;
                        break;
                    case R.id.btnGoToCourse:
                        Intent NextIntent = new Intent(getApplicationContext(),C_MAKECOURSE.class);
                        for (int i=0;i<9;i++){
                            if (arbSelectedCard[i]) {
                                //아이템별 xml다시 불러오기
                                //http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?ServiceKey=tA3Bvd%2Fqrx%2Fhw2TA2I2Q5eXvsnFHGjX03lreQhcDZor%2BqXNPq3Kt%2FS0o6k4Uu554fbJiUWaRphE1uRzWi3OULQ%3D%3D
                                //&contentTypeId=38&contentId=984586&MobileOS=AND&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y
                                arstrURL[i] = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon"
                                        + strServiceKey + url2 + arContentsTyped.get(arSelectedItem[i]).toString() + "&contentId=" + arContentsId.get(arSelectedItem[i]).toString()
                                        + "&MobileOS=AND&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";
                                arlstrURL.add(arstrURL[i]);
                                //mapx, mapy, overview, title, addr1,firstimage2
                                //NextIntent.putExtra("TourPlace" + i, arstrURL[i]);
                                nCourseCount++;
                            }
                        }
                        NextIntent.putStringArrayListExtra("arStrList",arlstrURL);
                        NextIntent.putExtra("CourseCount",nCourseCount);
                        NextIntent.putExtra("MyMapX",dLongitude);
                        NextIntent.putExtra("MyMapY",dLatitude);
                        startActivity(NextIntent);
                        break;

                }

                arbSelectedCard[nSelectCount] = !arbSelectedCard[nSelectCount];
                if (btnCard[nSelectCount].getImageAlpha() <255 && v.getId() != R.id.btnGoToCourse)
                    btnCard[nSelectCount].setImageAlpha(btnCard[nSelectCount].getImageAlpha() + 30);
                else if(v.getId() != R.id.btnGoToCourse)
                    btnCard[nSelectCount].setImageAlpha(btnCard[nSelectCount].getImageAlpha() - 30);
            }
        };

        btnSetting();

    }




    private void init(){
        finalUrl = url1 + strServiceKey + url2 + sMapXPos + dLongitude.toString()  + sMapYPos + dLatitude.toString()+ sDis + strDis + url3;
        cHandleXmlCategory = new C_HANDLEXMLMAKECATEGORY(finalUrl);
        cHandleXmlCategory.fetchXML();
        arContentsId = new ArrayList<String>();
        arContentsTyped = new ArrayList<String>();

        while (cHandleXmlCategory.parsingComplete){

        }

        arContentsId.addAll(cHandleXmlCategory.getContentID());
        arContentsTyped.addAll(cHandleXmlCategory.getContentType());
        nSelectCount = 0;

        arbSelectedCard = new boolean[9];
        for (int i = 0; i<  arbSelectedCard.length;i++){
            arbSelectedCard[i] = false;
        }
        nCardIndex = 0;

        nCourseCount = 0;

        arSelectedItem = new int[9];
        Random random = new Random();
        int nInputIndex = 0;
        int nCount = 0;
        for (int i = 0; i< 9; i++){
            nInputIndex = random.nextInt(arContentsId.size());
            for (nCount = 0; nCount < i ; nCount++) {
                while (nInputIndex == arSelectedItem[nCount]) {
                    nInputIndex = random.nextInt(arContentsId.size());
                }
            }
            arSelectedItem[i] = nInputIndex;
        }

        arstrURL = new String[9];
        btnCard = new ImageButton[9];

        btnNext = (ImageButton)findViewById(R.id.btnGoToCourse);
        btnCard[0] = (ImageButton)findViewById(R.id.btnTourSelect1);
        btnCard[1] = (ImageButton)findViewById(R.id.btnTourSelect2);
        btnCard[2] = (ImageButton)findViewById(R.id.btnTourSelect3);
        btnCard[3] = (ImageButton)findViewById(R.id.btnTourSelect4);
        btnCard[4] = (ImageButton)findViewById(R.id.btnTourSelect5);
        btnCard[5] = (ImageButton)findViewById(R.id.btnTourSelect6);
        btnCard[6] = (ImageButton)findViewById(R.id.btnTourSelect7);
        btnCard[7] = (ImageButton)findViewById(R.id.btnTourSelect8);
        btnCard[8] = (ImageButton)findViewById(R.id.btnTourSelect9);


    }

    private void btnSetting(){

        btnNext.setOnClickListener(listener);
        for (int i=0;i<btnCard.length;i++){
            btnCard[i].setOnClickListener(listener);
        }
    }



}
