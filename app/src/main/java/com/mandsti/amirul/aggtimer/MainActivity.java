package com.mandsti.amirul.aggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timeShow;
    SeekBar seekBar;
    Button startButton;
    CountDownTimer countDownTimer;
    MediaPlayer mediaPlayer;

    boolean playingOrNo = false;

    boolean countdownActive = false;
    private Button btnStop;

    public void pressToStart(View view){

        if (countdownActive){

           checkActive();


        }else {
            countdownActive = true;
            seekBar.setEnabled(false);
            startButton.setText("Stop!");

              countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 200, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    playingOrNo = false;

                    btnStop.setVisibility(View.INVISIBLE);
                    countDownSetter((int) millisUntilFinished / 1000);

                }

                @Override
                public void onFinish() {

                    if (seekBar.getProgress()== 0){
                        btnStop.setVisibility(View.INVISIBLE);
                    }else {


                        playingOrNo = true;
                        btnStop.setVisibility(View.VISIBLE);
                        btnStop.setText("Sound Stop");
                        try {
                            mediaPlayer.start();

                        }catch (Exception e){

                        }

                        checkActive();
                    }


                }
            }.start();

        }
    }

    private void checkActive() {

        countdownActive = false;
        seekBar.setEnabled(true);
        startButton.setText("Start");
        countDownTimer.cancel();
        timeShow.setText("0:30");
        seekBar.setProgress(30);
        seekBar.setMax(600);

    }

    public void countDownSetter(int progress){

        int minute = progress/60;
        int second  =  progress - (minute * 60);

        String secoundString = "";
        if (second < 9){
            secoundString = "0"+second;
        }else {
            secoundString = ""+second;
        }

        timeShow.setText(Integer.toString(minute)+":"+secoundString);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        timeShow = (TextView)findViewById(R.id.tv_Countdown);


        btnStop = (Button)findViewById(R.id.btnStop);
        btnStop.setVisibility(View.INVISIBLE);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.loud_sound);

        startButton = (Button)findViewById(R.id.btnStartStop);

        seekBar.setProgress(30);
        seekBar.setMax(600);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                /*progress bar*/

                countDownSetter(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (mediaPlayer != null && mediaPlayer.isPlaying()){

                   try {
                       mediaPlayer.stop();
                       mediaPlayer.release();
                       mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.loud_sound);
                   }catch (Exception e){

                   }

                   btnStop.setVisibility(View.INVISIBLE);
               }
            }
        });
    }
}
