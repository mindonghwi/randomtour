package com.minyongcross.myapplication;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by 민동휘 on 2017-10-22.
 */

public class C_XMLHANDLEPLACE {

//mapx, mapy, overview, title, addr1,firstimage2

    private ArrayList<String> arMapX = new ArrayList<String>();
    private ArrayList<String> arMapY = new ArrayList<String>();
    private ArrayList<String> arOverView = new ArrayList<String>();
    private ArrayList<String> arTitle = new ArrayList<String>();
    private ArrayList<String> arFirstImage = new ArrayList<String>();


    private String urlString = null;
    private XmlPullParserFactory xmlPullParserObject;
    public volatile boolean parsingComplete = true;


    public C_XMLHANDLEPLACE(String url){
        this.urlString = url;
    }

    public ArrayList<String> getArFirstImage() {
        return arFirstImage;
    }
    public ArrayList<String> getArMapX() {
        return arMapX;
    }
    public ArrayList<String> getArMapY() {
        return arMapY;
    }
    public ArrayList<String> getArOverView() {
        return arOverView;
    }
    public ArrayList<String> getArTitle() {
        return arTitle;
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
                            arMapX.add(text);
                        }else if (name.equals("mapy")){
                            arMapY.add(text);
                        }else if(name.equals("overview")){
                            arOverView.add(text);
                        }else if(name.equals("title")){
                            arTitle.add(text);
                        }else if(name.equals("firstimage2")){
                            arFirstImage.add(text);
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
