package com.codexdeveloper.photorecovery.utills;

import android.os.Environment;

import java.io.File;

public class Config {


    public static final int DATA = 1000;
    public static final int REPAIR = 2000;
    public static final int UPDATE = 3000;
    public static final String RECOVER_DIRECTORY;
    public static final String IMAGE_RECOVER_DIRECTORY;
    public static final String VIDEO_RECOVER_DIRECTORY;
    public static final String AUDIO_RECOVER_DIRECTORY;
    public static final String OTHER_RECOVER_DIRECTORY;

    public static final String APP_NAME = "DataRecovery";


    private Config() {
    }

    static {

        StringBuilder sbDirectory = new StringBuilder();
        sbDirectory.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        sbDirectory.append(File.separator);
        sbDirectory.append(APP_NAME);
        RECOVER_DIRECTORY = sbDirectory.toString();
    }

    static {
        StringBuilder sbDirectory = new StringBuilder();
        sbDirectory.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        sbDirectory.append(File.separator);
        sbDirectory.append(APP_NAME);
        sbDirectory.append(File.separator);
        sbDirectory.append("Audios");
        AUDIO_RECOVER_DIRECTORY = sbDirectory.toString();
    }

    static {
        StringBuilder sbDirectory = new StringBuilder();
        sbDirectory.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        sbDirectory.append(File.separator);
        sbDirectory.append(APP_NAME);
        sbDirectory.append(File.separator);
        sbDirectory.append("Images");
        IMAGE_RECOVER_DIRECTORY = sbDirectory.toString();
    }

    static {
        StringBuilder sbDirectory = new StringBuilder();
        sbDirectory.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        sbDirectory.append(File.separator);
        sbDirectory.append(APP_NAME);
        sbDirectory.append(File.separator);
        sbDirectory.append("Videos");
        VIDEO_RECOVER_DIRECTORY = sbDirectory.toString();
    }

    static {
        StringBuilder sbDirectory = new StringBuilder();
        sbDirectory.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        sbDirectory.append(File.separator);
        sbDirectory.append(APP_NAME);
        sbDirectory.append(File.separator);
        sbDirectory.append("Others");
        OTHER_RECOVER_DIRECTORY = sbDirectory.toString();
    }


}
