package com.codexdeveloper.photorecovery.model;

public class OtherModel {

    String pathOther;
    long lastModifiedOther;
    long sizeOther;
    boolean isCheck = false;

    public OtherModel(String pathOther, long lastModifiedOther, long sizeOther) {
        this.pathOther = pathOther;
        this.lastModifiedOther = lastModifiedOther;
        this.sizeOther = sizeOther;
    }

    public String getPathOther() {
        return pathOther;
    }

    public void setPathOther(String pathOther) {
        this.pathOther = pathOther;
    }

    public long getLastModifiedOther() {
        return lastModifiedOther;
    }

    public void setLastModifiedOther(long lastModifiedOther) {
        this.lastModifiedOther = lastModifiedOther;
    }

    public long getSizeOther() {
        return sizeOther;
    }

    public void setSizeOther(long sizeOther) {
        this.sizeOther = sizeOther;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
