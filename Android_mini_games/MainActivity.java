package com.example.project_20023030s;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements View.OnTouchListener {
    //Components from XML
    private TextView textViewGesture;
    private TextView textViewScore1, textViewScore2;
    private RelativeLayout relativeLayout;
    private ImageView[] imageViews = new ImageView[36];
    AlertDialog.Builder builder;

    //Images of gems
    private int[] gems = {R.drawable.g02, R.drawable.g06, R.drawable.g03, R.drawable.g09, R.drawable.g06, R.drawable.g07,
            R.drawable.g05, R.drawable.g04, R.drawable.g07, R.drawable.g08, R.drawable.g01, R.drawable.g02,
            R.drawable.g09, R.drawable.g01, R.drawable.g08, R.drawable.g05, R.drawable.g03, R.drawable.g04,
            R.drawable.g08, R.drawable.g01, R.drawable.g07, R.drawable.g01, R.drawable.g09, R.drawable.g05,
            R.drawable.g04, R.drawable.g06, R.drawable.g02, R.drawable.g07, R.drawable.g06, R.drawable.g04,
            R.drawable.g03, R.drawable.g05, R.drawable.g09, R.drawable.g03, R.drawable.g02, R.drawable.g08,};
    //positions of gems on the screen
    private int[][] gemPositions = {
            {450, 600}, {600, 600}, {750, 600}, {900, 600}, {1050, 600}, {1200, 600},
            {450, 750}, {600, 750}, {750, 750}, {900, 750}, {1050, 750}, {1200, 750},
            {450, 900}, {600, 900}, {750, 900}, {900, 900}, {1050, 900}, {1200, 900},
            {450, 1050}, {600, 1050}, {750, 1050}, {900, 1050}, {1050, 1050}, {1200, 1050},
            {450, 1200}, {600, 1200}, {750, 1200}, {900, 1200}, {1050, 1200}, {1200, 1200},
            {450, 1350}, {600, 1350}, {750, 1350}, {900, 1350}, {1050, 1350}, {1200, 1350},
    };

    //Dimensions of gem image
    private int gemWidth = 135;
    private int gemHeight = 135;

    //Scores (values) of each gem
    private int[] gemScore = {100, 200, 400, -200, 200, 300,
            600, 1000, 300, 500, -100, 100,
            -200, -100, 500, 600, 400, 1000,
            500, -100, 300, -100, -200, 600,
            1000, 200, 100, 300, 200, 1000,
            400, 600, -200, 400, 100, 500,};
    //current position of touch (mouse)
    private int x, y;
    //Score of player
    private int totalScore = 0;
    private int partScore = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent i = new Intent(MainActivity.this,
                            Bonus_animation.class);
                    i.putExtra("key1", totalScore);

                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //Get components from XML
        textViewGesture = findViewById(R.id.textViewGesture);
        textViewScore1 = findViewById(R.id.textViewScore1);
        relativeLayout = findViewById(R.id.relativeLayout);
        textViewScore2 = findViewById(R.id.textViewScore2);
        //get ready to handle touch event
        relativeLayout.setOnTouchListener(this);
        //show initial score
        textViewScore1.setText("partScore: " + partScore);
        textViewScore2.setText("totalScore: " + totalScore);
        //put gems on the screen
        buildGems();
    }

    private void buildGems() {
        //Go through each gem
        for (int i = 0; i < gems.length; i++) {
            //Create an ImageView to show the gem
            imageViews[i] = new ImageView(this);
            imageViews[i].setImageResource(gems[i]);

            //set the positions and dimensions of gem in the ConstraintLayout(screen)
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(gemWidth, gemHeight);
            params.leftMargin = gemPositions[i][0];
            params.topMargin = gemPositions[i][1];

            //Add this onto the screen
            relativeLayout.addView(imageViews[i], params);

        }
    }

    public boolean onTouch(View v, MotionEvent e) {
        try {
            int eventAction = e.getAction();
            x = (int) e.getX();
            y = (int) e.getY();
            switch (eventAction) {
                case MotionEvent.ACTION_DOWN:
                    //check gem
                    checkGem();
                    break;
                case MotionEvent.ACTION_UP:
                    //match gem
                    matchGem();
                    break;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }
    private void checkGem(){
        //Go through each gem
        try {
            for (int i=0;i<gems.length;i++){
                //If we have clicked on a gem
                if(x >=gemPositions[i][0] && x<= gemPositions[i][0] +gemWidth &&
                        y>= gemPositions[i][1] && y<= gemPositions[i][1] + gemHeight) {
                    if( totalScore >=0 && imageViews[i].getVisibility() == View.VISIBLE){
                        //Update score in text view
                        textViewScore2.setText("totalScore: "+ totalScore);
                        break;
                    }else if(totalScore >=0 && imageViews[i].getVisibility() != View.VISIBLE ){
                        //Check if player clicks blank
                        //Toast message "There are no gems where you clicked!"
                        Toast.makeText(getApplicationContext(),
                                "There are no gems where you clicked!",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    else{
                        Toast.makeText(getApplicationContext(),
                                "You loss!",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                    //Get out of method immediately
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void matchGem(){
        //Go through each gem
        try {
            for (int i=0;i<gems.length;i++){
                //If we have clicked on a gem
                if(x >=gemPositions[i][0] && x<= gemPositions[i][0] +gemWidth &&
                        y>= gemPositions[i][1] && y<= gemPositions[i][1] + gemHeight) {
                    if(imageViews[i].getVisibility() != View.VISIBLE){
                        //Check if player clicks blank
                        //will do nothing
                        break;
                    }//Add double  score if the gem score is the same as partScore
                    else if( partScore == gemScore[i]){
                        //Update score in text view
                        partScore += gemScore[i];
                        //if the gem score is the same as partScore,players get twice as gem score
                        totalScore += gemScore[i]*2;
                        textViewScore2.setText("totalScore: "+ totalScore);
                        textViewScore1.setText("partScore: "+ partScore);
                        //At the same time the gem will appear again, if the match is successful
                        imageViews[i].setVisibility(View.VISIBLE);
                        ImageView image=imageViews[i];
                        Animation2(image);
                        break;
                    }
                    //Add normal score and hide gems if scores don't match
                    else if(partScore <=1100 && partScore != gemScore[i]){
                        partScore += gemScore[i];
                        totalScore += gemScore[i];
                        textViewScore2.setText("totalScore: "+ totalScore);
                        textViewScore1.setText("partScore: "+ partScore);
                        //Set the visibility of the image to GONE
                        imageViews[i].setVisibility(View.GONE);
                        Animation1(imageViews[i]);
                        break;
                    }
                    //If partScore exceeds the upper limit of 1100, partScore will return to zero
                    else if(partScore >1100){
                        partScore =0;
                        textViewScore1.setText("partScore: "+ partScore);
                        //Set the visibility of the image to INVISIBLE
                        imageViews[i].setVisibility(View.INVISIBLE);
                        Animation1(imageViews[i]);
                        break;
                    }
                    else{
                        partScore += gemScore[i];
                        textViewScore1.setText("partScore: "+ partScore);
                        imageViews[i].setVisibility(View.INVISIBLE);

                    }
                    //Get out of method immediately
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void Animation1(ImageView image) {
        //Animation for  the gem score is the not same as partScore
        Animation a = new TranslateAnimation(Animation.ABSOLUTE, Animation.ABSOLUTE, Animation.ABSOLUTE, Animation.ABSOLUTE+1500f);
        //Run for 2 seconds = 2000 milliseconds
        a.setDuration(2000);
        a.setStartOffset(500);
        //the gem score is the same as partScore,return to original position
        a.setFillAfter(true);
        a.setInterpolator(new BounceInterpolator());
        //Show above animation in image view
        image.startAnimation(a);
    }
    private void Animation2(ImageView image) {
        //Animation for  the gem score is the same as partScore
        Animation b = new TranslateAnimation(0f, 0f, 0f, 1500f);
        //Run for 2 seconds = 2000 milliseconds
        b.setDuration(2000);
        b.setStartOffset(500);
        //the gem score is not same as partScore,no need return to original position
        b.setFillAfter(false);
        //Show above animation in image view
        b.setInterpolator(new BounceInterpolator());
        image.startAnimation(b);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e){
    //Display toast message if user clicks back button
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Toast.makeText(getApplicationContext(),
                    "You pressed back",Toast.LENGTH_LONG).show();
            showQuitDialog();
            return true;
        }
        else
            return super.onKeyDown(keyCode,e);
    }
    private void showQuitDialog(){
        //Before we can show pop-up dialog box,
        //we must set up some things with a builder
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to close this app?");
        builder.setCancelable(false);
        //Action to perform if user wants to quit
        builder.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,int which){
                finish();
                Toast.makeText(getApplicationContext(),
                        "You clicked Yes",Toast.LENGTH_LONG).show();
            }
        });
        //Action to perform if user does not want to quit
        builder.setNegativeButton("No",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,int which){
                dialog.cancel();
                Toast.makeText(getApplicationContext(),
                        "You clicked No",Toast.LENGTH_LONG).show();
            }
        });
        //Show pop-up dialog box
        AlertDialog alert = builder.create();
        alert.setTitle("Warning: about to close app");
        alert.show();
    }
}

