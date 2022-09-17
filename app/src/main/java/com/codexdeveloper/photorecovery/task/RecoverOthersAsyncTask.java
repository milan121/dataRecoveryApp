package com.codexdeveloper.photorecovery.task;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import com.codexdeveloper.photorecovery.model.OtherModel;
import com.codexdeveloper.photorecovery.R;
import com.codexdeveloper.photorecovery.dialog.LoadingDialog;
import com.codexdeveloper.photorecovery.utills.Config;
import com.codexdeveloper.photorecovery.utills.MediaScanner;

public class RecoverOthersAsyncTask extends AsyncTask<String, Integer, String> {

    private ArrayList<OtherModel> listPhoto;
    private Context mContext;
    private LoadingDialog progressDialog;
    private OnRestoreListener onRestoreListener;
    TextView tvNumber;
    int count = 0;


    public RecoverOthersAsyncTask(Context context, ArrayList<OtherModel> mList, OnRestoreListener mOnRestoreListener) {
        this.mContext = context;
        this.listPhoto = mList;
        this.onRestoreListener = mOnRestoreListener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.progressDialog = new LoadingDialog(this.mContext);
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {

        for (int strArr = 0; strArr < this.listPhoto.size(); strArr++) {
            File sourceFile = new File(this.listPhoto.get(strArr).getPathOther());
            File fileDirectory = new File(Config.OTHER_RECOVER_DIRECTORY);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Config.OTHER_RECOVER_DIRECTORY);
            stringBuilder.append(File.separator);

            stringBuilder.append(getFileName(listPhoto.get(strArr).getPathOther()));
            File destinationFile = new File(stringBuilder.toString());
            try {
                if (!destinationFile.exists()) {
                    fileDirectory.mkdirs();
                }
                copy(sourceFile, destinationFile);
                if (Build.VERSION.SDK_INT >= 19) {
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.fromFile(destinationFile));
                    this.mContext.sendBroadcast(intent);
                }
                new MediaScanner(this.mContext, destinationFile);
                count = strArr + 1;
                publishProgress(count);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    public void copy(File file, File file2) throws IOException {
        FileChannel source = null;
        FileChannel destination = null;

        source = new FileInputStream(file).getChannel();
        destination = new FileOutputStream(file2).getChannel();

        source.transferTo(0, source.size(), destination);
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }

    public String getFileName(String path) {

        String filename = path.substring(path.lastIndexOf("/") + 1);
        return filename;
    }

    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);
        try {
            if (this.progressDialog != null && progressDialog.isShowing()) {
                this.progressDialog.dismiss();
                this.progressDialog = null;
            }
        } catch (Exception e) {
            //DO Something
        }

        if (null != onRestoreListener) {
            onRestoreListener.onComplete();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        tvNumber = (TextView) progressDialog.findViewById(R.id.tvNumber);
        tvNumber.setText(String.format(mContext.getString(R.string.restoring_number_format_Audio), values[0]));

    }

    public interface OnRestoreListener {
        void onComplete();
    }

}
