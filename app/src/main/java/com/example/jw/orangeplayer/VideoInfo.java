package com.example.jw.orangeplayer;

/**
 * Created by jw on 17-4-3.
 */

public class VideoInfo {

    String data;
    String title;
    int duration;
    String mime;

    public VideoInfo(){

    }
    public VideoInfo(String data, String title, int duration, String mime) {
        this.data = data;
        this.title = title;
        this.duration = duration;
        this.mime=mime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }
}
