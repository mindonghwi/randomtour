package com.minyongcross.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by 민동휘 on 2017-11-02.
 */

public class C_LISTVIEWADAPTER extends BaseAdapter {

    private ArrayList<C_LISTVIEWITEM> m_arLViewItemList = new ArrayList<C_LISTVIEWITEM>();
    public C_LISTVIEWADAPTER(){

    }

    @Override
    public int getCount() {
        return m_arLViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return m_arLViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int nPos = position;
        final Context mContext = parent.getContext();

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_item,parent,false);
        }

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imagePlaceData);
        TextView txtTitle = (TextView)convertView.findViewById(R.id.txtTitle);
        TextView txtOverView = (TextView)convertView.findViewById(R.id.txtOverView);

        C_LISTVIEWITEM cListviewitem = m_arLViewItemList.get(nPos);

        intoImageView(iconImageView,bitmapFormUrl(cListviewitem.getM_ImageViewURL()));
        txtTitle.setText(cListviewitem.getM_sTitle());
        txtOverView.setText(cListviewitem.getM_sOverView());

        return convertView;
    }

    public void addItem(String sImageUrl, String sTitle, String sOverView){
        C_LISTVIEWITEM item = new C_LISTVIEWITEM();

        item.setM_ImageViewURL(sImageUrl);
        item.setM_sTitle(sTitle);
        item.setM_sOverView(sOverView);

        m_arLViewItemList.add(item);
    }


    public Bitmap bitmapFormUrl(final String sUrl){
        final Bitmap[] bitmaps = new Bitmap[1];
        Thread mThread = new Thread(){
            @Override
            public void run(){
                try {
                    URL url = new URL(sUrl);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmaps[0] = BitmapFactory.decodeStream(is);


                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        mThread.start();

        try {
            mThread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return bitmaps[0];
    }

    public void intoImageView(ImageView iv, Bitmap bitmap){
        if (bitmap!=null){
            iv.setImageBitmap(bitmap);
        }else {
            //오류
        }
    }
}
