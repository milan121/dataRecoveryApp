package com.codexdeveloper.photorecovery.model;

import java.util.ArrayList;

public class AlbumVideo {
    String strVideoFolder;
    ArrayList<VideoModel> listVideo;
    long lastVideoModified;

    public String getStrVideoFolder() {
        return strVideoFolder;
    }

    public void setStrVideoFolder(String strVideoFolder) {
        this.strVideoFolder = strVideoFolder;
    }

    public ArrayList<VideoModel> getListVideo() {
        return listVideo;
    }

    public void setListVideo(ArrayList<VideoModel> listVideo) {
        this.listVideo = listVideo;
    }

    public long getLastVideoModified() {
        return lastVideoModified;
    }

    public void setLastVideoModified(long lastVideoModified) {
        this.lastVideoModified = lastVideoModified;
    }
}
