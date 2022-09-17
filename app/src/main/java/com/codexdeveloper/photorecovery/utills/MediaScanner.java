package com.codexdeveloper.photorecovery.utills;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

import java.io.File;

public class MediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {
    private MediaScannerConnection mScannerConnection;
    private File destFile;

    public MediaScanner(Context context, File file) {
        this.destFile = file;
        this.mScannerConnection = new MediaScannerConnection(context, this);
        this.mScannerConnection.connect();
    }

    @Override
    public void onMediaScannerConnected() {
        this.mScannerConnection.scanFile(this.destFile.getAbsolutePath(), null);
    }
    @Override
    public void onScanCompleted(String path, Uri uri) {
        this.mScannerConnection.disconnect();
    }
}
