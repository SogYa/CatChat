package com.example.realtimechat.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.realtimechat.datalayer.AuthRepo;
import com.example.realtimechat.datalayer.SPControl;
import com.example.realtimechat.datalayer.datamanager.RxData;
import com.example.realtimechat.instruments.AppStatements;
import com.example.realtimechat.instruments.Constants;
import com.example.realtimechat.instruments.MyLifecycle;
import com.example.realtimechat.instruments.myCallBack;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class App extends Application implements Application.ActivityLifecycleCallbacks {

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

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        // AppStatements.sendOnline();

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        // AppStatements.sendOffline();
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
    }
}


