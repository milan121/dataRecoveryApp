package com.codexdeveloper.photorecovery.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.codexdeveloper.photorecovery.R;
import com.codexdeveloper.photorecovery.utills.Config;

public class RestoreResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_result);

        intData();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onBackPressed();
                finish();
            }
        }, 4000);


    }

    public void intData() {
        int int_position = getIntent().getIntExtra("value", 0);
        TextView tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvStatus.setText(String.valueOf(int_position));
        TextView tvPath = (TextView) findViewById(R.id.tvPath);
        tvPath.setText("File Restored to" + "\n" + "/" + Config.RECOVER_DIRECTORY);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RestoreResultActivity.this, MainActivity.class));
        finish();
    }
}