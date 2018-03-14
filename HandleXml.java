package com.minyongcross.myapplication;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by 민동휘 on 2017-10-10.
 */

public class HandleXml {
    private String Addr1 = "Addr1";
    private String Addr2 = "Addr2";
    private String Addr3 = "Addr3";
    private String Addr4 = "Addr4";
    private String urlString = null;
    private XmlPullParserFactory xmlPullParserObject;
    public volatile boolean parsingComplete = true;

    private ArrayList<String> arAddrList = new ArrayList<String>();
    private ArrayList<String> arTitleList = new ArrayList<String>();

    public HandleXml(String url){
        this.urlString = url;
    }

    public String getAddr1() {
        return Addr1;
    }

    public String getAddr2() {
        return Addr2;
    }

    public String getAddr3() {
        return Addr3;
    }

    public String getAddr4() {
        return Addr4;
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
                            Addr1 = text;
                            arAddrList.add(text);
                        }else if (name.equals("title")){
                            Addr2 = text;
                            arTitleList.add(text);
                        }else if(name.equals("mapx")){
                            Addr3 = text;
                        }else if(name.equals("mapy")){
                            Addr4 = text;
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
