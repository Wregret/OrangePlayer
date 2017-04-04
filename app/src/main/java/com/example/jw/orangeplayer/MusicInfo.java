package com.example.jw.orangeplayer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jw on 17-4-3.
 */

public class MusicInfo {

    private int id;
    private String title;
    private String album;
    private String artist;
    private String data;//path
    private int duration;
    private long size;

    public MusicInfo() {
    }

    public MusicInfo(int id, String title, String album, String artist, String data, int duration, long size) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.data = data;
        this.duration = duration;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getDuration() {
        return duration;
    }

    public String getFormatDuration(){
        String min = duration / (1000 * 60) + "";
        String sec = duration % (1000 * 60) + "";
        if (min.length() < 2) {
            min = "0" + duration / (1000 * 60) + "";
        } else {
            min = duration / (1000 * 60) + "";
        }
        if (sec.length() == 4) {
            sec = "0" + (duration % (1000 * 60)) + "";
        } else if (sec.length() == 3) {
            sec = "00" + (duration % (1000 * 60)) + "";
        } else if (sec.length() == 2) {
            sec = "000" + (duration % (1000 * 60)) + "";
        } else if (sec.length() == 1) {
            sec = "0000" + (duration % (1000 * 60)) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
