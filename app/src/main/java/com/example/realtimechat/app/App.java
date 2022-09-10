package com.example.realtimechat.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.realtimechat.data.SPControl;
import com.example.realtimechat.data.datamanager.RxData;
import com.example.realtimechat.instruments.AppStatements;
import com.example.realtimechat.instruments.Constants;
import com.example.realtimechat.instruments.MyLifecycle;

public class App extends Application{

    //Класс App где проводим все важные иниты

    private static App app;


    //pickImageFrom

    public static MyLifecycle.AppState getAppState() {
        return RxData.appStateBehaviorRelay.getValue();
    }

    public static Context getAppContext() {
        return app.getApplicationContext();
    }

    public static Activity getActivity() {
        return RxData.activityBehaviorRelay.getValue();
    }


    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
        ProcessLifecycleOwner.get().getLifecycle().addObserver(
                new MyLifecycle(ProcessLifecycleOwner.get().getLifecycle(), appState -> {
                    RxData.appStateBehaviorRelay.accept(appState);
                    if (SPControl.getInstance().getBoolPrefs(Constants.APP_PREFS_IS_AUTH)) {
                        if (appState.event.getTargetState().isAtLeast(Lifecycle.State.RESUMED)) {
                            AppStatements.sendOnline();
                        } else if (appState.event.getTargetState().isAtLeast(Lifecycle.State.DESTROYED))
                            AppStatements.sendOffline();
                    }
                }));
    }
}


