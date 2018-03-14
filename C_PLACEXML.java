package com.minyongcross.myapplication;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 민동휘 on 2017-10-28.
 */

public class C_PLACEXML {
    //mapx, mapy, overview, title, addr1,firstimage2


    private double m_dMapX;
    private double m_dMapY;
    private String m_sTitle;
    private String m_sFisrtImage;
    private String m_sOverView;


    private String urlString = null;
    private XmlPullParserFactory xmlPullParserObject;
    public volatile boolean parsingComplete = true;


    public C_PLACEXML(String url){
        this.urlString = url;
    }

    public String getM_sTitle() {
        return m_sTitle;
    }

    public double getM_dMapY() {
        return m_dMapY;
    }

    public double getM_dMapX() {
        return m_dMapX;
    }

    public String getM_sFisrtImage() {
        return m_sFisrtImage;
    }

    public String getM_sOverView() {
        return m_sOverView;
    }


    public void parseXMLAndStoreIt(XmlPullParser myParser){
        int event;
        String text = null;
        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT){
                String name = myParser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        //mapx, mapy, overview, title, addr1,firstimage2
                        if (name.equals("mapx")){
                            m_dMapX = Double.parseDouble(text);
                        }else if (name.equals("mapy")){
                            m_dMapY = Double.parseDouble(text);
                        }else if(name.equals("overview")){
                            m_sOverView = text;
                        }else if(name.equals("title")){
                            m_sTitle = text;
                        }else if(name.equals("firstimage2")){
                            m_sFisrtImage = text;
                        }else
                            break;
                }
                event= myParser.next();
            }
            parsingComplete = false;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void fetchXML(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection connect = (HttpURLConnection)url.openConnection();
                    connect.setReadTimeout(10000);
                    connect.setConnectTimeout(15000);
                    connect.setRequestMethod("GET");
                    connect.setDoInput(true);
                    connect.connect();

                    InputStream stream = connect.getInputStream();
                    xmlPullParserObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myParser = xmlPullParserObject.newPullParser();
                    myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
                    myParser.setInput(stream,null);
                    parseXMLAndStoreIt(myParser);
                    stream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

}
