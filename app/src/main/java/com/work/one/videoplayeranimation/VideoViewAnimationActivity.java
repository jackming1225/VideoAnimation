package com.work.one.videoplayeranimation;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class VideoViewAnimationActivity extends AppCompatActivity {

    private MediaMetadataRetriever mediaMetadataRetriever;
    private MediaController myMediaController;
    private boolean isCompleted = false;
    private MediaPlayer.OnCompletionListener myVideoViewCompletionListener =
            new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer arg0) {
                    isCompleted = true;
                    Toast.makeText(VideoViewAnimationActivity.this, "End of Video",
                            Toast.LENGTH_LONG).show();
                }
            };
    private VideoView vvRandomView;
    private TextView tvStatus;
    private boolean isSmall = false;
    private int height;
    private int width;
    private TimerTask timerTask;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view_animation);
        initView();
        play();
    }

    private void startTimer() {
        Timer timer;
        handler = new Handler();

        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        timer.schedule(timerTask, 0, 5000);
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        //code to run after every 5 seconds
                        if (isSmall) {
                            changeVideoSize(1000, 1000);
                            isSmall = false;
                        } else {
                            changeVideoSize(500, 500);
                            isSmall = true;
                        }
                    }
                });
            }
        };
    }

    private void changeVideoSize(int newWidth, int newHeight) {

        int left = vvRandomView.getLeft();
        int top = vvRandomView.getTop();
        int right = left + (newWidth);
        int bottom = top + (newHeight);
        vvRandomView.layout(left, top, right, bottom);
//        if(!isSmall){
//            vvRandomView.setClipToOutline();
//        }

    }

    private void play() {

        String viewSource = "android.resource://" + getPackageName() + "/" + R.raw.videoplayback;
        mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(this, Uri.parse(viewSource));


        vvRandomView.setVideoURI(Uri.parse(viewSource));
        myMediaController = new MediaController(this);
        vvRandomView.setMediaController(myMediaController);

        /*vvRandomView.setOnCompletionListener(myVideoViewCompletionListener);
        vvRandomView.setOnPreparedListener(MyVideoViewPreparedListener);
        vvRandomView.setOnErrorListener(myVideoViewErrorListener);*/

        vvRandomView.requestFocus();
        vvRandomView.start();
        startTimer();

    }

    private void initView() {
        vvRandomView = findViewById(R.id.vvRandomView);

        tvStatus = findViewById(R.id.tvStatus);

        DisplayMetrics dm;
        dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        height = dm.heightPixels;
        width = dm.widthPixels;

        vvRandomView.setMinimumHeight(height);
        vvRandomView.setMinimumWidth(width);
    }
}
