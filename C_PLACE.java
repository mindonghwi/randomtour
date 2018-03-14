package com.minyongcross.myapplication;

/**
 * Created by 민동휘 on 2017-10-28.
 */

public class C_PLACE {

    private C_PLACEXML cPlaceXML;
    private String m_URL;
    private double m_dMapX;
    private double m_dMapY;
    private String m_sTitle;
    private String m_sImageURLData;
    private String m_sOverView;
    private double m_dDistance;
    private int nNearDistance;


    public void init(String sURL){
        setURL(sURL);
        cPlaceXML = new C_PLACEXML(getURL());
        cPlaceXML.fetchXML();
        while (cPlaceXML.parsingComplete){
        }

        setM_dMapX(cPlaceXML.getM_dMapX());
        setM_dMapY(cPlaceXML.getM_dMapY());
        setM_sImageURLData(cPlaceXML.getM_sFisrtImage());
        setM_sOverView(cPlaceXML.getM_sOverView());
        setM_sTitle(cPlaceXML.getM_sTitle());
    }



    private void setURL(String sURL) {
        this.m_URL = sURL;
    }
    private String getURL() {
        return m_URL;
    }

    private void setM_dMapX(double m_dMapX) {
        this.m_dMapX = m_dMapX;
    }
    public double getM_dMapX() {
        return m_dMapX;
    }

    private void setM_dMapY(double m_dMapY) {
        this.m_dMapY = m_dMapY;
    }
    public double getM_dMapY() {
        return m_dMapY;
    }

    private void setM_sImageURLData(String m_sImageURLData) {
        this.m_sImageURLData = m_sImageURLData;
    }
    public String getM_sImageURLData() {
        return m_sImageURLData;
    }

    public String getM_sTitle() {
        return m_sTitle;
    }
    private void setM_sTitle(String m_sTitle) {
        this.m_sTitle = m_sTitle;
    }

    private void setM_sOverView(String m_sOverView) {
        this.m_sOverView = m_sOverView;
    }
    public String getM_sOverView() {
        return m_sOverView;
    }

    public double getM_dDistance() {
        return m_dDistance;
    }
    public void setM_dDistance(double m_dDistance) {
        this.m_dDistance = m_dDistance;
    }

    public void setnNearDistance(int nNearDistance) {
        this.nNearDistance = nNearDistance;
    }

    public int getnNearDistance() {
        return nNearDistance;
    }
}
