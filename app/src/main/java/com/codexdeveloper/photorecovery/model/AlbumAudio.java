package com.codexdeveloper.photorecovery.model;

import java.util.ArrayList;

public class AlbumAudio {

    String strAudioFolder;
    ArrayList<AudioModel> listAudio;
    long lastAudioModified;

    public String getStrAudioFolder() {
        return strAudioFolder;
    }

    public void setStrAudioFolder(String strAudioFolder) {
        this.strAudioFolder = strAudioFolder;
    }

    public ArrayList<AudioModel> getListAudio() {
        return listAudio;
    }

    public void setListAudio(ArrayList<AudioModel> listAudio) {
        this.listAudio = listAudio;
    }

    public long getLastAudioModified() {
        return lastAudioModified;
    }

    public void setLastAudioModified(long lastAudioModified) {
        this.lastAudioModified = lastAudioModified;
    }



}
