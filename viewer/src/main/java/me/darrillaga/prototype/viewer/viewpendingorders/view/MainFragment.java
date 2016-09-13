package me.darrillaga.prototype.viewer.viewpendingorders.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import me.darrillaga.prototype.commons.BaseFragment;
import me.darrillaga.prototype.commons.ui.ErrorsHandler;
import me.darrillaga.prototype.viewer.databinding.FragmentMainBinding;
import rx.exceptions.OnErrorThrowable;
import rx.subscriptions.CompositeSubscription;

@FragmentWithArgs
public class MainFragment extends BaseFragment {

    private FragmentMainBinding mFragmentMainBinding;
    private ErrorsHandler mErrorsHandler;

    public MainFragment() {
        addPlugin(new IOIOFragmentPlugin((connectionType, extra) -> new BaseIOIOLooper() {

            private Components mComponents;

            @Override
            protected void setup() throws ConnectionLostException, InterruptedException {
                super.setup();
                mComponents = Components.create(ioio_);
            }

            @Override
            public void loop() throws ConnectionLostException, InterruptedException {
                super.loop();
                MainFragment.this.loop(mComponents);
            }

            @Override
            public void disconnected() {
                super.disconnected();
                mComponents.close();
            }
        }));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mErrorsHandler = new ErrorsHandler(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentMainBinding = FragmentMainBinding
                .inflate(inflater, container, false);

        return mFragmentMainBinding.getRoot();
    }

    private void loop(Components components) {
        try {
            float progress = mFragmentMainBinding.seekbar.getProgress() / 1000f;
            Log.d("AAA", "loop progress: " + progress);
            components.pwmOutput.setDutyCycle(progress);
        } catch (ConnectionLostException e) {
            throw OnErrorThrowable.from(e);
        }
    }

    private static class Components {
        public static Components create(IOIO ioio) {
            try {
                Components components = new Components();

                components.ioio = ioio;
                components.pwmOutput = ioio.openPwmOutput(13, 25000);

                return components;

            } catch (ConnectionLostException e) {
                throw OnErrorThrowable.from(e);
            }
        }

        public IOIO ioio;
        public PwmOutput pwmOutput;

        public void close() {
            pwmOutput.close();
        }
    }
}
