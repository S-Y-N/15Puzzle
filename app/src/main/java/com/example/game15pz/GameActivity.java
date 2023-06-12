package com.example.game15pz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private int emptyX=3;
    private int emptyY=3;
    private RelativeLayout group;
    private Button[][] buttons;
    private int[] tiles;
    private TextView textViewSteps;
    private int stepCount = 0;
    private TextView textViewTimer;
    private Timer timer;
    private int timeCouunt =0;
    private Button buttonShuffle;
    private Button buttonStop;
    private boolean isTimerRun;
    private Steps steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        loadViews();
        loadNumbers();
        generateNumbers();
        loadData();

    }
    private void loadData(){
        emptyX=3;
        emptyY=3;
        for (int i = 0; i < group.getChildCount() - 1; i++) {
            buttons[i/4][i%4].setText(String.valueOf(tiles[i]));
            buttons[i/4][i%4].setBackgroundResource(android.R.drawable.btn_default);
        }
        buttons[emptyY][emptyY].setText("");
        buttons[emptyX][emptyX].setBackgroundColor(ContextCompat.getColor(this,R.color.colorEmptyBtn));
    }
    private void generateNumbers(){
        int n=15;
        Random random = new Random();
        while (n>1){
            int rand = random.nextInt(n--);
            int temp = tiles[rand];
            tiles[rand]=tiles[n];
            tiles[n]=temp;
        }
        if(!isSolvable())
            generateNumbers();

    }
    private boolean isSolvable(){
        int countInversions = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < i; j++) {
                if(tiles[j]>tiles[i])
                    countInversions++;
            }
        }
        return countInversions % 2 ==0;
    }
    private void loadNumbers(){
        tiles = new int[16];
        for (int i = 0; i < group.getChildCount() - 1; i++) {
            tiles[i] = i + 1;
        }
    }
    private void loadTimer(){
        isTimerRun = true;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeCouunt++;
                setTime(timeCouunt);
            }
        }, 1000,1000);
    }
    private void setTime(int timeCouunt){
        int second  = timeCouunt %60;
        int hour = timeCouunt / 3600;
        int minute = (timeCouunt - hour * 3600)/60;

        textViewTimer.setText(String.format("Time: %02d:%02d:%02d",hour,minute,second));

    }
    private void loadViews(){
        group = findViewById(R.id.group);

        textViewSteps  = findViewById(R.id.text_view_steps);
        textViewTimer = findViewById(R.id.tv_timer);
        buttonShuffle = findViewById(R.id.btn_shuffle);
        buttonStop = findViewById(R.id.btn_stop);

        loadTimer();

        buttons = new Button[4][4];
        for (int i = 0; i < group.getChildCount(); i++) {
            buttons[i/4][i%4] = (Button) group.getChildAt(i);
        }

        buttonShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateNumbers();
                loadData();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTimerRun){
                    timer.cancel();
                    buttonStop.setText("Continue");
                    isTimerRun = false;
                    for (int i = 0; i < group.getChildCount(); i++) {
                        buttons[i/4][i%4].setClickable(false);
                    }
                }else{
                    loadTimer();
                    buttonStop.setText("STOP");
                    for (int i = 0; i < group.getChildCount(); i++) {
                        buttons[i/4][i%4].setClickable(true);
                    }
                }
            }
        });
    }
    public void buttonClick(View view){
        Button button = (Button) view;
        int x = button.getTag().toString().charAt(0)-'0';
        int y = button.getTag().toString().charAt(1)-'0';

        if((Math.abs(emptyX - x) == 1 && emptyY ==y)||(Math.abs(emptyY - y) == 1 && emptyX == x)){
            buttons[emptyX][emptyY].setText(button.getText().toString());
            buttons[emptyX][emptyY].setBackgroundResource(android.R.drawable.btn_default);
            button.setText("");
            button.setBackgroundColor(ContextCompat.getColor(this,R.color.colorEmptyBtn));
            emptyX = x;
            emptyY = y;
            stepCount++;
            textViewSteps.setText("Steps: "+stepCount);
            checkWin();
        }
    }
    private void checkWin(){
        boolean isWin = false;
        if(emptyX==3 && emptyY ==3){
            for (int i = 0; i < group.getChildCount() - 1; i++) {
                if(buttons[i/4][i%4].getText().toString().equals(String.valueOf(i+1))){
                    isWin=true;
                }else{
                    isWin =false;
                    break;
                }
            }
        }
        if(isWin){
            Toast.makeText(this, "Win!!\nSteps: "+stepCount, Toast.LENGTH_SHORT).show();
            for (int i = 0; i < group.getChildCount(); i++) {
                buttons[i/4][i%4].setClickable(false);
            }
            timer.cancel();
            buttonShuffle.setClickable(false);
            buttonStop.setClickable(false);
            saveData();
        }
    }
    private void saveData(){
        steps = new Steps(GameActivity.this);
        steps.saveLastStep(stepCount);
        steps.saveLastTime(timeCouunt);

        if(steps.getBestStep()!=0){
            if(steps.getBestStep()>stepCount)
                steps.saveBestStep(stepCount);
        }else{
            steps.saveBestStep(stepCount);
        }
        if(steps.getBestTime()!=0){
            if(steps.getBestTime()>timeCouunt)
                steps.saveBestTime(timeCouunt);
        }else{
            steps.saveBestTime(timeCouunt);
        }
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        setResult(MainActivity.REQUEST_CODE);
    }
}