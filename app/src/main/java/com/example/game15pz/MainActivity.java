package com.example.game15pz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;
    private Button btnStartGame;
    private TextView textLastStep;
    private TextView textLastTime;
    private TextView textBestTime;
    private TextView textBestStep;
    private Steps steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartGame  = findViewById(R.id.btn_start);
        textLastStep = findViewById(R.id.text_lastSteps);
        textLastTime = findViewById(R.id.text_lastTime);
        textBestTime = findViewById(R.id.textBestTime);
        textBestStep = findViewById(R.id.textBestScore);

        steps = new Steps(this);

        loadData();

        btnStartGame.setOnClickListener((view)->{
                startActivityForResult(new Intent(MainActivity.this, GameActivity.class),REQUEST_CODE);
            });
    }
    private void loadData(){
        textLastStep.setText(String.valueOf(steps.getLastStep()));
        textBestStep.setText(String.valueOf(steps.getBestStep()));


        int lastTime = steps.getLastTime();
        int lastSecond = lastTime %60;
        int lastHour = lastSecond /3600;
        int lastMinute = (lastTime - lastHour * 3600) / 60;
        textLastTime.setText(String.format("%02d:%02d:%02d",lastHour,lastMinute,lastSecond));

        int bestTime = steps.getBestTime();
        int bestSecond = bestTime %60;
        int bestHour = bestSecond /3600;
        int bestMinute = (bestTime - bestHour * 3600) / 60;
        textBestTime.setText(String.format("%02d:%02d:%02d",bestHour,bestMinute,bestSecond));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){
            textLastStep.setText(String.valueOf(steps.getLastStep()));
            textBestStep.setText(String.valueOf(steps.getBestStep()));

            int lastTime = steps.getLastTime();
            int lastSecond = lastTime %60;
            int lastHour = lastSecond /3600;
            int lastMinute = (lastTime - lastHour * 3600) / 60;
            textLastTime.setText(String.format("%02d:%02d:%02d",lastHour,lastMinute,lastSecond));

            int bestTime = steps.getBestTime();
            int bestSecond = bestTime %60;
            int bestHour = bestSecond /3600;
            int bestMinute = (bestTime - bestHour * 3600) / 60;
            textBestTime.setText(String.format("%02d:%02d:%02d",bestHour,bestMinute,bestSecond));
        }
    }
}