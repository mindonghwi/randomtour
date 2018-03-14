package com.minyongcross.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.example.myapplication.R;

/**
 * Created by 민동휘 on 2017-11-02.
 */

public class C_DIRECTION extends AppCompatActivity{

    private Integer images[] = {R.drawable.a_guide00,R.drawable.a_guide02,R.drawable.a_guide03,R.drawable.a_guide04,R.drawable.a_guide05,R.drawable.a_guide06};
    private int nCurrImage=0;
    private double dLatitude;
    private double dLongitude;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directions);
        Intent getIntent = getIntent();
        dLatitude = getIntent.getDoubleExtra("dLatitude",0.0);
        dLongitude = getIntent.getDoubleExtra("dLongitude",0.0);

        init();

    }

    private void init(){
        final ImageSwitcher imageSwitcher = (ImageSwitcher)findViewById(R.id.imagesw);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(C_DIRECTION.this);
                return imageView;
            }
        });

        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right));

        setInitialImage();

        final ImageButton btnRotateButton = (ImageButton)findViewById(R.id.btnImageSwichter);
        btnRotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nCurrImage++;
                if (nCurrImage == 6){
                    Intent intent = new Intent(getApplicationContext(),C_TRANSFORTATION.class);

                    intent.putExtra("dLatitude", dLatitude);
                    intent.putExtra("dLongitude", dLongitude);
                    startActivity(intent);
                }else{
                    setCurrentImage();
                }

            }
        });

    }
    private void setInitialImage() {
        setCurrentImage();
    }
    private void setCurrentImage() {
        final  ImageSwitcher imageSwitcher = (ImageSwitcher)findViewById(R.id.imagesw);
        imageSwitcher.setImageResource(images[nCurrImage]);

    }
}
