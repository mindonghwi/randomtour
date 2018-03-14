package com.minyongcross.myapplication;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by 민동휘 on 2017-10-17.
 */

public class C_HANDLEXMLMAKECATEGORY {
    private ArrayList<String> arContentID = new ArrayList<String>();
    private ArrayList<String> arContentType = new ArrayList<String>();


    private String urlString = null;
    private XmlPullParserFactory xmlPullParserObject;
    public volatile boolean parsingComplete = true;

    private ArrayList<String> arAddrList = new ArrayList<String>();
    private ArrayList<String> arTitleList = new ArrayList<String>();

    public C_HANDLEXMLMAKECATEGORY(String url){
        this.urlString = url;
    }

    public ArrayList<String> getContentID() {
        return arContentID;
    }

    public ArrayList<String> getContentType() {
        return arContentType;
    }

    public ArrayList<String> getArAddrList(){return arAddrList;}
    public ArrayList<String> getArTitleList(){return arTitleList;}

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
                        if (name.equals("addr1")){
                            arAddrList.add(text);
                        }else if (name.equals("title")){
                            arTitleList.add(text);
                        }else if(name.equals("contentid")){
                           arContentID.add(text);
                        }else if(name.equals("contenttypeid")){
                            arContentType.add(text);
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
