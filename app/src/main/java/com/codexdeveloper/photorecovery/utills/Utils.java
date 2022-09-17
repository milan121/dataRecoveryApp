package com.codexdeveloper.photorecovery.utills;

import android.app.Activity;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.codexdeveloper.photorecovery.model.AlbumAudio;
import com.codexdeveloper.photorecovery.model.AlbumOthers;
import com.codexdeveloper.photorecovery.model.AlbumPhoto;
import com.codexdeveloper.photorecovery.model.AlbumVideo;

public class Utils {

    public static ArrayList<AlbumPhoto> mAlbumPhotos = new ArrayList<>();
    public static ArrayList<AlbumPhoto> mHiddenFiles = new ArrayList<>();
    public static ArrayList<AlbumVideo> mAlbumVideos = new ArrayList<>();
    public static ArrayList<AlbumAudio> mAlbumAudios = new ArrayList<>();
    public static ArrayList<AlbumOthers> mAlbumOthers = new ArrayList<>();

    public static String noOfImage = "0";
    public static String noOfVideo = "0";
    public static String noOfAudio = "0";
    public static String noOfOther = "0";

    private Utils() {
    }

    public static String formatSize(long size) {
        if (size <= 0)
            return "";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static String getFileName(String path) {
        String filename = path.substring(path.lastIndexOf("/") + 1);
        return filename;
    }

    public static File[] getFileList(String str) {
        File file = new File(str);
        if (!file.isDirectory()) {
            return new File[0];
        }

        if (file.listFiles() != null) {

            return file.listFiles();
        } else return new File[0];

    }

    public static boolean checkSelfPermission(Activity activity, String s) {
        if (isAndroid23()) {
            return ContextCompat.checkSelfPermission(activity, s) == 0;
        } else {
            return true;
        }
    }

    public static boolean isAndroid23() {
        return android.os.Build.VERSION.SDK_INT >= 23;
    }

}
