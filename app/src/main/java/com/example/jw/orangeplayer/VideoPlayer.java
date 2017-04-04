package com.example.jw.orangeplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoPlayer extends AppCompatActivity {

    private VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        Intent intent=getIntent();
        String path=intent.getStringExtra("path");

        video=(VideoView)findViewById(R.id.videoPlayer);
        video.setMediaController(new android.widget.MediaController(this));
        video.setVideoPath(path);
        video.requestFocus();
        try{
            video.start();
        }catch(Exception e){
            e.printStackTrace();
        }
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });
    }
}
