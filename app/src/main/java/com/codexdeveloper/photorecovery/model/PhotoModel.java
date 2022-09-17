package com.codexdeveloper.photorecovery.model;

public class PhotoModel {
    String pathPhoto;
    long lastModifiedPhoto;
    long sizePhoto;
    boolean isCheck= false;

    public PhotoModel(String path,long date,long size){
        this.pathPhoto = path;
        this.lastModifiedPhoto = date;
        this.sizePhoto = size;
    }
    public long getLastModified(){
        return  lastModifiedPhoto;
    }
    public void setLastModified(long mLastModified){
        this.lastModifiedPhoto = mLastModified;
    }
    public void setSizePhoto(long msizePhoto){
        sizePhoto = msizePhoto;

    }
    public boolean getIsCheck(){
        return isCheck;
    }
    public void setIsCheck(boolean checked){
        isCheck = checked;
    }

    public long getSizePhoto() {
        return sizePhoto;
    }

    public String getPathPhoto() {
        return pathPhoto;
    }

    public void setPathPhoto(String path) {
        this.pathPhoto = path;
    }
}
