package com.example.jw.orangeplayer;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by jw on 17-4-3.
 */

public class MediaQuery {

    public static ArrayList<MusicInfo> musicQuery(Context context){
        Cursor cursor= context.getContentResolver()
                .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        MusicInfo info;
        ArrayList musicList=new ArrayList<MusicInfo>();
        if(cursor.moveToFirst()) {
            do {
                info = new MusicInfo(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)), cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)), cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)), cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)), cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)), cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)), cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));
                musicList.add(info);
            } while (cursor.moveToNext());
        }
        return musicList;
    }

    public static ArrayList<VideoInfo>videoQuery(Context context){
        String []column=new String[]{MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.MIME_TYPE};
        Cursor cursor=context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,column ,null,null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        VideoInfo info;
        ArrayList videoList=new ArrayList<VideoInfo>();
        while(cursor.moveToNext()){
            info=new VideoInfo(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)),cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)),cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)),cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)));
            videoList.add(info);
        }
        return videoList;
    }

}
