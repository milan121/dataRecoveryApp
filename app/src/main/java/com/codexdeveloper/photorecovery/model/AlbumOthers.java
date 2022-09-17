package com.codexdeveloper.photorecovery.model;

import java.util.ArrayList;

public class AlbumOthers {

    String strOtherFolder;
    ArrayList<OtherModel> listOther;
    long lastAudioModified;

    public String getStrOtherFolder() {
        return strOtherFolder;
    }

    public void setStrOtherFolder(String strOtherFolder) {
        this.strOtherFolder = strOtherFolder;
    }

    public ArrayList<OtherModel> getListOther() {
        return listOther;
    }

    public void setListOther(ArrayList<OtherModel> listOther) {
        this.listOther = listOther;
    }

    public long getLastAudioModified() {
        return lastAudioModified;
    }

    public void setLastAudioModified(long lastAudioModified) {
        this.lastAudioModified = lastAudioModified;
    }


}
