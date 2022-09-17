package com.codexdeveloper.photorecovery.dialog;

import android.app.Dialog;
import android.content.Context;

import com.codexdeveloper.photorecovery.R;


public class LoadingDialog extends Dialog {

    private Context mContext;

    public LoadingDialog(Context activity) {
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        this.mContext = activity;
        requestWindowFeature(1);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.layout_loading_dialog);

    }
}