package me.darrillaga.prototype.viewer.viewpendingorders.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;

import com.pascalwelsch.compositeandroid.fragment.FragmentPlugin;

import ioio.lib.util.IOIOLooperProvider;
import ioio.lib.util.android.IOIOAndroidApplicationHelper;

public class IOIOFragmentPlugin extends FragmentPlugin {

    private IOIOAndroidApplicationHelper mHelper;
    private IOIOLooperProvider mIOIOLooperProvider;

    public IOIOFragmentPlugin(IOIOLooperProvider looperProvider) {
        mIOIOLooperProvider = looperProvider;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mHelper = new IOIOAndroidApplicationHelper(new ContextWrapper(context), mIOIOLooperProvider);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper.create();
    }

    public void onDestroy() {
        mHelper.destroy();
        super.onDestroy();
    }

    public void onStart() {
        super.onStart();
        mHelper.start();
    }

    public void onStop() {
        mHelper.stop();
        super.onStop();
    }
}
