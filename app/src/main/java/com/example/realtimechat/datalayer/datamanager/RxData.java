package com.example.realtimechat.datalayer.datamanager;

import android.app.Activity;

import androidx.lifecycle.Lifecycle;

import com.example.realtimechat.instruments.MyLifecycle;
import com.jakewharton.rxrelay2.BehaviorRelay;

public class RxData {

    public static BehaviorRelay<Activity> activityBehaviorRelay = BehaviorRelay.create();

    public static BehaviorRelay<MyLifecycle.AppState> appStateBehaviorRelay = BehaviorRelay.createDefault(
            new MyLifecycle.AppState(Lifecycle.State.DESTROYED, Lifecycle.Event.ON_DESTROY));
}
