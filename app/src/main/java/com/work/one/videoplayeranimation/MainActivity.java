package com.work.one.videoplayeranimation;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.work.one.videoplayeranimation.vlcsimpleplayer.Logger;

import org.videolan.libvlc.media.VideoView;

public class MainActivity extends AppCompatActivity /*implements View.OnClickListener */ {

    //    private VideoView vvVideoPlayer;
//    private MyVideoView vvVideoPlayer;
    private VideoView vvVideoPlayer;
    private SurfaceView svVideoPlayer;
    private Button btnOpenVP, btnSmall, btnLarge;
    private int height;
    private int width;
    private DisplayMetrics dm;
    private boolean isSmall = false;
    private SimpleExoPlayerView exoPlayerView, exoPlayerView2;
    private SimpleExoPlayer exoPlayer;
    private MediaSource mediaSource;
    private RelativeLayout rlWork;
    private boolean isCircleView = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*initViews();
        playVideo();*/
        playVideoFromExoPlayer();
        chnageVideoSize();

    }

    private void playVideoFromExoPlayer() {
        exoPlayerView = findViewById(R.id.exo_player_view);
        exoPlayerView2 = findViewById(R.id.exo_player_view2);
        rlWork = findViewById(R.id.rlDialog);
        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            Uri videoURI = Uri.parse("http://blueappsoftware.in/layout_design_android_blog.mp4");

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

            fullScreenPlayer(0);
//            circularScreenPlayer(4000);
        } catch (Exception e) {
            Logger.logException("MainAcvtivity exoplayer error ", e);
        }
    }

    private void fullScreenPlayer(long seek) {
        exoPlayerView.setVisibility(View.VISIBLE);
        rlWork.setVisibility(View.GONE);

        exoPlayerView.setPlayer(exoPlayer);
        exoPlayer.prepare(mediaSource);
        exoPlayer.seekTo(seek);
        exoPlayer.setPlayWhenReady(true);

    }

    private void chnageVideoSize() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    long current = exoPlayer.getCurrentPosition();
                    Logger.logInfo("modulo", String.valueOf((int) current % 5));

                    if ((int) current % 5 == 0) {
                        if (isCircleView) {
                            isCircleView = false;
                            fullScreenPlayer(current);
                        } else {
                            isCircleView = true;
                            circularScreenPlayer(current);
                        }
                    }
                    Logger.logInfo("crrent pos", String.valueOf(current));
                    if (current == exoPlayer.getDuration()) {
                        break;
                    }

                }
            }
        }, 2000);
    }

    private void circularScreenPlayer(long seek) {
        exoPlayerView.setVisibility(View.GONE);
        rlWork.setVisibility(View.VISIBLE);
        exoPlayerView2.setPlayer(exoPlayer);
        exoPlayer.prepare(mediaSource);
        exoPlayer.seekTo(seek);
        exoPlayer.setPlayWhenReady(true);
    }
}
