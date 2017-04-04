package com.example.jw.orangeplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v4.content.ContentResolverCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.WindowDecorActionBar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TabHost tabHost;
    private MediaPlayer mediaPlayer;
    private ArrayList<Map<String,String>> musicList;
    private ArrayList<Map<String,String>> videoList;
    private int currentItem;
    private int modeNumber=1;//1 for list cycle and 2 for single cycle
    private Button playpause;
    private Button stop;
    private Button mode;
    private TextView songName;
    private ListView musicListView;
    private ListView videoListView;
    private SeekBar seekBar;
    private float x1=0;
    private float x2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentItem=0;
        musicList=new ArrayList<Map<String,String>>();
        videoList=new ArrayList<Map<String,String>>();
        tabHost=(TabHost)findViewById(android.R.id.tabhost);
        tabHost.setup();

        mediaPlayer=new MediaPlayer();
        LayoutInflater inflater= LayoutInflater.from(this);
        inflater.inflate(R.layout.music,tabHost.getTabContentView());
        inflater.inflate(R.layout.video,tabHost.getTabContentView());
        tabHost.addTab(tabHost.newTabSpec("musicPage").setIndicator("Music").setContent(R.id.musicPage));
        tabHost.addTab(tabHost.newTabSpec("videoPage").setIndicator("Video").setContent(R.id.videoPage));

        displayMusicList();
        displayVideoList();

        playpause=(Button)findViewById(R.id.play);
        stop=(Button)findViewById(R.id.stop);
        mode=(Button)findViewById(R.id.mode);
        songName=(TextView)findViewById(R.id.songName);
        //seekBar=(SeekBar)findViewById(R.id.seekBar);

        setGestureListener();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(modeNumber==1){
                    nextMusic();
                }if(modeNumber==2){
                    playMusic(musicList.get(currentItem).get("path"));
                }if(modeNumber==3){
                    currentItem=(int)(Math.random()*(musicList.size()-1));
                    playMusic(musicList.get(currentItem).get("path"));
                }
            }
        });

        playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    playpause.setBackgroundResource(R.drawable.playicon);
                }else{
                    mediaPlayer.start();
                    playpause.setBackgroundResource(R.drawable.pauseicon);
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                    playpause.setBackgroundResource(R.drawable.playicon);
                }
            }
        });
        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(modeNumber==1){
                    modeNumber=2;
                    mode.setText("单");
                }else if(modeNumber==2){
                    modeNumber=3;
                    mode.setText("随");
                }else {
                    modeNumber=1;
                    mode.setText("表");
                }
            }
        });

    }

    private void displayMusicList(){
        ArrayList<MusicInfo> infoList=MediaQuery.musicQuery(this);
        for(MusicInfo info:infoList){
            Map<String,String>item=new HashMap<String,String>();
            item.put("title",info.getTitle());
            item.put("artist",info.getArtist());
            item.put("duration",info.getFormatDuration());
            item.put("path",info.getData());
            musicList.add(item);
        }
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,musicList,R.layout.music_item,new String[]{"title","artist","duration"},new int[]{R.id.title,R.id.artist,R.id.duration});
        musicListView=(ListView)findViewById(R.id.musicList);
        musicListView.setAdapter(simpleAdapter);
        musicListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentItem=position;
                playMusic(musicList.get(currentItem).get("path"));
            }
        });
    }
    private void displayVideoList(){
        ArrayList<VideoInfo>videoInfoList=MediaQuery.videoQuery(this);
        for(VideoInfo info:videoInfoList){
            Map<String,String>item=new HashMap<String,String>();
            item.put("title",info.getTitle());
            item.put("path",info.getData());
            videoList.add(item);
        }
        SimpleAdapter videoSimpleAdapter=new SimpleAdapter(this,videoList,R.layout.video_item,new String[]{"title"},new int[]{R.id.videoTitle});
        videoListView=(ListView)findViewById(R.id.videoList);
        videoListView.setAdapter(videoSimpleAdapter);
        videoListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                playVideo(videoList.get(position).get("path"));
            }
        });
    }

    private void setGestureListener(){
        songName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x1=event.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x2=event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        if(x1>x2){
                            nextMusic();
                        }else if(x1<x2){
                            preMusic();
                        }
                        break;
                }
                return true;
            }
        });
    }

    void playMusic(String path){
        try{
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
            playpause.setBackgroundResource(R.drawable.pauseicon);
            songName.setText(musicList.get(currentItem).get("title"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    void nextMusic(){
        if(++currentItem>=musicList.size()){
            currentItem=0;
        }
        playMusic(musicList.get(currentItem).get("path"));
    }

    void preMusic(){
        if(currentItem-1<0){
            currentItem=musicList.size()-1;
            playMusic(musicList.get(currentItem).get("path"));
        }else{
            currentItem=currentItem-1;
            playMusic(musicList.get(currentItem).get("path"));
        }
    }

    void playVideo(String path){
        //// TODO: 17-4-3
        Intent intent=new Intent(MainActivity.this,VideoPlayer.class);
        intent.putExtra("path",path);
        startActivity(intent);
    }

   @Override
    protected void onDestroy() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        super.onDestroy();
    }
}
