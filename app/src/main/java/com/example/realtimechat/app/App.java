package com.example.realtimechat.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.realtimechat.datalayer.AuthRepo;
import com.example.realtimechat.datalayer.SPControl;
import com.example.realtimechat.instruments.AppStatements;
import com.example.realtimechat.instruments.Constants;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class App extends Application implements Application.ActivityLifecycleCallbacks {

    //Класс App где проводим все важные иниты

    private static App app;


    public void setImage(ImageView imageView, Activity o) {
        CropImage.activity()
                .setAspectRatio(1, 1)
                .setRequestedSize(150, 150)
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(o);
    }

    //pickImageFrom

//    public static MyLifecycle.AppState getAppState() {
//        return RxData.appStateBehaviorRelay.getValue();
//    }

    public static Context getAppContext() {
        return app.getApplicationContext();
    }

//    public static Activity getActivity() {
//        return RxData.activityBehaviorRelay.getValue();
//    }


    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
//        ProcessLifecycleOwner.get().getLifecycle().addObserver(
//            new MyLifecycle(ProcessLifecycleOwner.get().getLifecycle(), new MyLifecycle.MyLifecycleListener() {
//                @Override
//                public void newState(MyLifecycle.AppState appState) {
//                    RxData.appStateBehaviorRelay.accept(appState);
//                }
//            }));

//        SoLoader.init(this, false);
//        Realm.init(this);
//        Fresco.initialize(this);
//
//        RxPaparazzo.register(this);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
       AppStatements.sendOnline();

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        AppStatements.sendOffline();
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


