package com.codexdeveloper.photorecovery.model;

import java.util.ArrayList;

public class AlbumPhoto {
    String strFolder;
    ArrayList<PhotoModel> listPhoto;
    long lastModified;

    public long getLastModified(){
        return  lastModified;
    }
    public void setLastModified(long mLastModified){
        this.lastModified = mLastModified;
    }
    public String getStrFolder() {
        return strFolder;
    }

    public void setStrFolder(String strFolder) {
        this.strFolder = strFolder;
    }

    public ArrayList<PhotoModel> getListPhoto() {
        return listPhoto;
    }

    public void setListPhoto(ArrayList<PhotoModel> mList) {
        this.listPhoto = mList;
    }
}
