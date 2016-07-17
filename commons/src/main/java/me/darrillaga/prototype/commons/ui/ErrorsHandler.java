package me.darrillaga.prototype.commons.ui;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;

public class ErrorsHandler {

    private Context mContext;

    public ErrorsHandler(Context context) {
        mContext = context;
    }

    public void handle(Throwable throwable) {
        showToast(throwable.getMessage());
        FirebaseCrash.report(throwable);
    }

    private void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }
}
