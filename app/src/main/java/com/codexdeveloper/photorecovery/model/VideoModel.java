package com.codexdeveloper.photorecovery.model;

public class VideoModel {
    String pathVideo;
    long lastModifiedVideo;
    long sizeVideo;
    boolean isCheck = false;


    public VideoModel(String path,long date,long size){
        this.pathVideo = path;
        this.lastModifiedVideo = date;
        this.sizeVideo = size;
    }

    public String getPathVideo() {
        return pathVideo;
    }

    public void setPathVideo(String pathVideo) {
        this.pathVideo = pathVideo;
    }

    public long getLastModifiedVideo() {
        return lastModifiedVideo;
    }

    public void setLastModifiedVideo(long lastModifiedVideo) {
        this.lastModifiedVideo = lastModifiedVideo;
    }

    public long getSizeVideo() {
        return sizeVideo;
    }

    public void setSizeVideo(long sizeVideo) {
        this.sizeVideo = sizeVideo;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
