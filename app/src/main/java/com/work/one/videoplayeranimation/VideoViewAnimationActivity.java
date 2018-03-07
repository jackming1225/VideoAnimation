package com.work.one.videoplayeranimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private VideoView vvRandomView;
    private Button btnSmall, btnLarge;
    private boolean isSmall = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view_animation);
        initView();
        play();
    }

    private void play() {
        MediaController mediaController = new MediaController(this);

        mediaController.setAnchorView(vvRandomView);
        mediaController.setAnchorView(vvRandomView);

    }

    private void initView() {
        vvRandomView = findViewById(R.id.vvRandomView);
        btnLarge = findViewById(R.id.btnLarge);
        btnSmall = findViewById(R.id.btnSmall);

        btnSmall.setOnClickListener(this);
        btnLarge.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSmall:

                break;

            case R.id.btnLarge:

                break;
        }
    }
}
