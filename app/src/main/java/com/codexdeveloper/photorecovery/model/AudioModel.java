package com.codexdeveloper.photorecovery.model;

public class AudioModel {

    String pathAudio;
    long lastModifiedAudio;
    long sizeAudio;
    boolean isCheck = false;

    public AudioModel(String pathAudio, long lastModifiedAudio, long sizeAudio) {
        this.pathAudio = pathAudio;
        this.lastModifiedAudio = lastModifiedAudio;
        this.sizeAudio = sizeAudio;
    }

    public String getPathAudio() {
        return pathAudio;
    }

    public void setPathAudio(String pathAudio) {
        this.pathAudio = pathAudio;
    }

    public long getLastModifiedAudio() {
        return lastModifiedAudio;
    }

    public void setLastModifiedAudio(long lastModifiedAudio) {
        this.lastModifiedAudio = lastModifiedAudio;
    }

    public long getSizeAudio() {
        return sizeAudio;
    }

    public void setSizeAudio(long sizeAudio) {
        this.sizeAudio = sizeAudio;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
