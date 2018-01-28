package com.shock.aasaanjobassignment.page;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shock.aasaanjobassignment.R;

/**
 * Created by shahid shaikh on 26-01-2018.
 */

public class AppBaseActivity extends AppCompatActivity {

    /**
     * Local Variable declaration
     */
    public ProgressDialog progressDialog;
    public Toast toast;

    public void showProgressDailog(boolean isCancelable) {
        progressDialog = new ProgressDialog(AppBaseActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.loading_please_wait_msg));
        progressDialog.setCancelable(isCancelable);
        progressDialog.show();
    }

    public void showToast(String msg, int duration) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(this, msg, duration);
        toast.show();
    }

    public void hideProgressDailog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
