package com.example.project_20023030s;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

public class Bonus_animation extends AppCompatActivity {

    ImageView imageView1,imageView2,imageView3;
    public void initObjects() {
        //Get image view and buttons from XML
        imageView1 =  findViewById(R.id.imageView11);
        imageView2 =  findViewById(R.id.imageView12);
        imageView3 =  findViewById(R.id.imageView13);
        imageView3.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
        imageView1.setVisibility(View.INVISIBLE);

    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_animation);
        initObjects();
        Intent i=getIntent();
        int totalScore=i.getIntExtra("key1",0);
        SystemClock.sleep(2000);
        try {
            if(totalScore>=10000 && totalScore<13000) {
                //if totalScore greater or equal to 10000 and less than 13000,set the bronze medal to be visible
                imageView3.setVisibility(View.VISIBLE);
                AnimationBronze();
                Toast.makeText(getApplicationContext(),
                        "You Get Bronze Medal!",
                        Toast.LENGTH_LONG).show();
            }
            else if(totalScore>=13000 && totalScore<17000){
                //if totalScore greater or equal to 13000 and less than 17000,set the silver medal to be visible
                imageView2.setVisibility(View.VISIBLE);
                AnimationSilver();
                Toast.makeText(getApplicationContext(),
                        "You Get Silver Medal!",
                        Toast.LENGTH_LONG).show();
            }
            else if(totalScore>=17000){
                //if totalScore greater or equal to 17000,set the gold medal to be visible
                imageView1.setVisibility(View.VISIBLE);
                AnimationGold();
                Toast.makeText(getApplicationContext(),
                        "You Get Gold Medal!",
                        Toast.LENGTH_LONG).show();
            }
            else {
                //if totalScore less than 10000,show failure toast
                Toast.makeText(getApplicationContext(),
                        "You loss!",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //if totalScore>=17000,show gold medal animation
    private void AnimationGold() {
        Animation a = new TranslateAnimation(0f, 500f, 0f, 1500f);
        //Run for 2 seconds = 2000 milliseconds
        a.setDuration(2000);
        a.setStartOffset(500);
        a.setFillAfter(true);
        //Show above animation in image view
        a.setInterpolator(new BounceInterpolator());
        imageView1.startAnimation(a);
    }
    //if 13000=<totalScore<17000,show silver medal animation
    private void AnimationSilver() {

        Animation a = new TranslateAnimation(0f, 000f, 0f, 1500f);
        //Run for 2 seconds = 2000 milliseconds
        a.setDuration(2000);
        a.setStartOffset(500);
        a.setFillAfter(true);
        //Show above animation in image view
        a.setInterpolator(new BounceInterpolator());
        imageView2.startAnimation(a);
    }
    //if 10000<=totalScore<13000,show bronze medal animation
    private void AnimationBronze() {

        Animation a = new TranslateAnimation(0f, -500f, 0f, 1500f);
        //Run for 2 seconds = 2000 milliseconds
        a.setDuration(2000);
        a.setStartOffset(500);
        a.setFillAfter(true);
        //Show above animation in image view
        a.setInterpolator(new BounceInterpolator());
        imageView3.startAnimation(a);
    }
}