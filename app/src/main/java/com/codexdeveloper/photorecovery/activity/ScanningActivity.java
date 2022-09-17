package com.codexdeveloper.photorecovery.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.EnvironmentCompat;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.codexdeveloper.photorecovery.ads.AdmobAdsModel;
import com.codexdeveloper.photorecovery.model.AlbumAudio;
import com.codexdeveloper.photorecovery.model.AlbumOthers;
import com.codexdeveloper.photorecovery.model.AlbumPhoto;
import com.codexdeveloper.photorecovery.model.AlbumVideo;
import com.codexdeveloper.photorecovery.model.AudioModel;
import com.codexdeveloper.photorecovery.model.OtherModel;
import com.codexdeveloper.photorecovery.model.PhotoModel;
import com.codexdeveloper.photorecovery.model.VideoModel;
import com.codexdeveloper.photorecovery.R;
import com.codexdeveloper.photorecovery.utills.Config;
import com.codexdeveloper.photorecovery.utills.Utils;

import static com.codexdeveloper.photorecovery.utills.Utils.mAlbumAudios;
import static com.codexdeveloper.photorecovery.utills.Utils.mAlbumOthers;
import static com.codexdeveloper.photorecovery.utills.Utils.mAlbumPhotos;
import static com.codexdeveloper.photorecovery.utills.Utils.mAlbumVideos;

public class ScanningActivity extends AppCompatActivity {

    ScanImagesAsyncTask mScanImagesAsyncTask;
    ArrayList<PhotoModel> listPhoto = new ArrayList<>();
    ArrayList<VideoModel> listVideo = new ArrayList<>();
    ArrayList<AudioModel> listAudio = new ArrayList<>();
    ArrayList<OtherModel> listOther = new ArrayList<>();

    TextView noOfImage, noOfVideo, noOfAudio, noOfOthers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);

        new AdmobAdsModel(this).bannerAds(this, findViewById(R.id.adsView));
        noOfImage = findViewById(R.id.noOfImage);
        noOfVideo = findViewById(R.id.noOfVideo);
        noOfAudio = findViewById(R.id.noOfAudio);
        noOfOthers = findViewById(R.id.noOfOthers);

        mScanImagesAsyncTask = new ScanImagesAsyncTask();
        mScanImagesAsyncTask.execute();

      /*  android.provider.Settings.System.putInt(getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT, -1);*/

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Toast.makeText(this, "Please Wait Till All The Files Are Scanning", Toast.LENGTH_SHORT).show();
    }

    public class ScanImagesAsyncTask extends AsyncTask<String, String, ArrayList<AlbumPhoto>> {

        int numPhoto = 0;
        int numVideo = 0;
        int numAudio = 0;
        int numOther = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<AlbumPhoto> doInBackground(String... strings) {

            String strArr = Environment.getExternalStorageDirectory().getAbsolutePath();

            //Day la tat ca thu muc trong may
            try {
                getSdCard();
                if (Utils.getFileList(strArr) != null)
                    checkFileOfDirectory(strArr, Utils.getFileList(strArr));
            } catch (Exception e) {
                Log.e("Exception", "doInBackground: " + e.getMessage());
            }

            Collections.sort(mAlbumPhotos, new Comparator<AlbumPhoto>() {
                @Override
                public int compare(AlbumPhoto lhs, AlbumPhoto rhs) {

                    return Long.valueOf(rhs.getLastModified()).compareTo(lhs.getLastModified());
                }
            });

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if (values[0].endsWith(".img")) {
                String newImgValue = values[0].replace(".img", " ");
                noOfImage.setText("" + newImgValue);
                Utils.noOfImage = newImgValue;

            } else if (values[0].endsWith(".video")) {
                String newVideoValue = values[0].replace(".video", " ");
                noOfVideo.setText("" + newVideoValue);
                Utils.noOfVideo = newVideoValue;

            } else if (values[0].endsWith(".aud")) {
                String newAudioValue = values[0].replace(".aud", " ");
                noOfAudio.setText("" + newAudioValue);
                Utils.noOfAudio = newAudioValue;
            } else if (values[0].endsWith(".other")) {
                String newAudioValue = values[0].replace(".other", " ");
                noOfOthers.setText("" + newAudioValue);
                Utils.noOfOther = newAudioValue;
            }

        }

        @Override
        protected void onPostExecute(ArrayList<AlbumPhoto> albumPhotos) {
            super.onPostExecute(albumPhotos);

           /* if (mAlbumPhotos.size() == 0) {
                Intent intent = new Intent(getApplicationContext(), NoFileActiviy.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), AlbumActivity.class);
                startActivity(intent);
            }*/

            Toast.makeText(ScanningActivity.this, "All files have been scanned", Toast.LENGTH_SHORT).show();
            //  if (mAlbumPhotos.size() != 0) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            //}
        }

        public void checkFileOfDirectory(String temp, File[] fileArr) {
            if (fileArr != null)
                for (int i = 0; i < fileArr.length; i++) {
                    if (fileArr[i].isDirectory()) {
                        String temp_sub = fileArr[i].getPath();
                        checkFileOfDirectory(temp_sub, Utils.getFileList(fileArr[i].getPath()));

                    } else {

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(fileArr[i].getPath(), options);

                        if (!(options.outWidth == -1 || options.outHeight == -1)) {

                            File file = new File(fileArr[i].getPath());
                            int file_size = Integer.parseInt(String.valueOf(file.length()));

                            if (file_size > 40000) {
                                listPhoto.add(new PhotoModel(file.getPath(), file.lastModified(), file_size));
                                numPhoto = numPhoto + 1;
                                publishProgress(numPhoto + ".img");
                            }

                        } else {

                            if (fileArr[i].getPath().endsWith(".mkv") ||
                                    fileArr[i].getPath().endsWith(".mp4")) {

                                File file = new File(fileArr[i].getPath());
                                long file_size = Long.parseLong(String.valueOf(file.length()));

                                listVideo.add(new VideoModel(file.getPath(), file.lastModified(), file_size));
                                publishProgress(numVideo++ + ".video");

                            } else if (fileArr[i].getPath().endsWith(".opus") ||
                                    fileArr[i].getPath().endsWith(".mp3") ||
                                    fileArr[i].getPath().endsWith(".aac") ||
                                    fileArr[i].getPath().endsWith(".m4a")) {

                                File file = new File(fileArr[i].getPath());
                                long file_size = Long.parseLong(String.valueOf(file.length()));

                                listAudio.add(new AudioModel(file.getPath(), file.lastModified(), file_size));
                                publishProgress(numAudio++ + ".aud");

                            } else {

                                File file = new File(fileArr[i].getPath());
                                long file_size = Long.parseLong(String.valueOf(file.length()));

                                listOther.add(new OtherModel(file.getPath(), file.lastModified(), file_size));
                                publishProgress(numAudio++ + ".other");
                            }
                        }
                    }
                }

            if (listPhoto.size() != 0 && !temp.contains(Config.RECOVER_DIRECTORY)) {
                AlbumPhoto obj_model = new AlbumPhoto();
                obj_model.setStrFolder(temp);
                obj_model.setLastModified(new File(temp).lastModified());
                Collections.sort(listPhoto, new Comparator<PhotoModel>() {
                    @Override
                    public int compare(PhotoModel lhs, PhotoModel rhs) {

                        return Long.valueOf(rhs.getLastModified()).compareTo(lhs.getLastModified());
                    }
                });
                obj_model.setListPhoto((ArrayList<PhotoModel>) listPhoto.clone());

                mAlbumPhotos.add(obj_model);
            }
            listPhoto.clear();

            if (listVideo.size() != 0 && !temp.contains(Config.RECOVER_DIRECTORY)) {

                AlbumVideo obj_model = new AlbumVideo();

                obj_model.setStrVideoFolder(temp);
                obj_model.setLastVideoModified(new File(temp).lastModified());
                Collections.sort(listVideo, new Comparator<VideoModel>() {
                    @Override
                    public int compare(VideoModel lhs, VideoModel rhs) {

                        return Long.valueOf(rhs.getLastModifiedVideo()).compareTo(lhs.getLastModifiedVideo());
                    }
                });
                obj_model.setListVideo((ArrayList<VideoModel>) listVideo.clone());

                mAlbumVideos.add(obj_model);
            }
            listVideo.clear();

            if (listAudio.size() != 0 && !temp.contains(Config.RECOVER_DIRECTORY)) {

                AlbumAudio albumAudio = new AlbumAudio();

                albumAudio.setStrAudioFolder(temp);
                albumAudio.setLastAudioModified(new File(temp).lastModified());

                Collections.sort(listAudio, new Comparator<AudioModel>() {
                    @Override
                    public int compare(AudioModel lhs, AudioModel rhs) {
                        return Long.valueOf(rhs.getLastModifiedAudio()).compareTo(lhs.getLastModifiedAudio());
                    }
                });

                albumAudio.setListAudio((ArrayList<AudioModel>) listAudio.clone());

                mAlbumAudios.add(albumAudio);
            }
            listAudio.clear();

            if (listOther.size() != 0 && !temp.contains(Config.RECOVER_DIRECTORY)) {

                AlbumOthers albumOthers = new AlbumOthers();

                albumOthers.setStrOtherFolder(temp);
                albumOthers.setLastAudioModified(new File(temp).lastModified());

                Collections.sort(listOther, new Comparator<OtherModel>() {
                    @Override
                    public int compare(OtherModel lhs, OtherModel rhs) {
                        return Long.valueOf(rhs.getLastModifiedOther()).compareTo(lhs.getLastModifiedOther());
                    }
                });

                albumOthers.setListOther((ArrayList<OtherModel>) listOther.clone());

                mAlbumOthers.add(albumOthers);
            }
            listOther.clear();
        }

        public void getSdCard() {

            String[] externalStoragePaths = getExternalStorageDirectories();

            if (externalStoragePaths != null && externalStoragePaths.length > 0) {

                for (String path : externalStoragePaths) {
                    File file = new File(path);
                    if (file.exists()) {
                        File[] subFiles = file.listFiles();
                        checkFileOfDirectory(path, subFiles);
                    }
                }
            }
        }

        public String[] getExternalStorageDirectories() {
            List<String> results = new ArrayList();
            if (Build.VERSION.SDK_INT >= 19) {
                File[] externalDirs = getExternalFilesDirs(null);
                if (externalDirs != null && externalDirs.length > 0) {
                    for (File file : externalDirs) {
                        if (file != null) {
                            String[] paths = file.getPath().split("/Android");
                            if (paths != null && paths.length > 0) {
                                boolean addPath;
                                String path = paths[0];
                                if (Build.VERSION.SDK_INT >= 21) {
                                    addPath = Environment.isExternalStorageRemovable(file);
                                } else {
                                    addPath = "mounted".equals(EnvironmentCompat.getStorageState(file));
                                }
                                if (addPath) {
                                    results.add(path);
                                }
                            }
                        }
                    }
                }
            }

            if (results.isEmpty()) {
                String output = "";
                InputStream is = null;
                try {
                    Process process = new ProcessBuilder(new String[0]).command(new String[]{"mount | grep /dev/block/vold"}).redirectErrorStream(true).start();
                    process.waitFor();
                    is = process.getInputStream();
                    byte[] buffer = new byte[1024];
                    while (is.read(buffer) != -1) {
                        output = output + new String(buffer);
                    }
                    is.close();
                } catch (Exception e) {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e2) {

                        }
                    }

                }
                if (!output.trim().isEmpty()) {
                    String[] devicePoints = output.split(IOUtils.LINE_SEPARATOR_UNIX);
                    if (devicePoints.length > 0) {
                        for (String voldPoint : devicePoints) {
                            results.add(voldPoint.split(" ")[2]);
                        }
                    }
                }
            }

            String[] storageDirectories = new String[results.size()];
            for (int i = 0; i < results.size(); i++) {
                storageDirectories[i] = (String) results.get(i);
            }
            return storageDirectories;
        }

    }
}