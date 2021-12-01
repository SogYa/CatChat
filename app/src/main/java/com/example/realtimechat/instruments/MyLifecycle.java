package com.example.realtimechat.instruments;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyLifecycle implements LifecycleObserver {

    public interface MyLifecycleListinner {
        void newState(AppState appState);
    }

    String TAG = "MyLifecycle";
    Lifecycle lifecycle;
    MyLifecycleListinner myLifecycleListinner;

    public MyLifecycle(Lifecycle lifecycle,MyLifecycleListinner myLifecycleListinner){
        this.lifecycle = lifecycle;
        this.myLifecycleListinner = myLifecycleListinner;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void stateCreate(){
        Log.d(TAG,"ON_CREATE");
        myLifecycleListinner.newState(new AppState(
                lifecycle.getCurrentState(),
                Lifecycle.Event.ON_CREATE
        ));
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void stateStart(){
        Log.d(TAG,"ON_START");
        myLifecycleListinner.newState(new AppState(
                lifecycle.getCurrentState(),
                Lifecycle.Event.ON_START
        ));
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void stateResume(){
        Log.d(TAG,"ON_RESUME");
        myLifecycleListinner.newState(new AppState(
                lifecycle.getCurrentState(),
                Lifecycle.Event.ON_RESUME
        ));
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void statePause(){
        Log.d(TAG,"ON_PAUSE");
        myLifecycleListinner.newState(new AppState(
                lifecycle.getCurrentState(),
                Lifecycle.Event.ON_PAUSE
        ));
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stateStop(){
        Log.d(TAG,"ON_STOP");
        myLifecycleListinner.newState(new AppState(
                lifecycle.getCurrentState(),
                Lifecycle.Event.ON_STOP
        ));
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void stateDestroy(){
        Log.d(TAG,"ON_DESTROY");
        myLifecycleListinner.newState(new AppState(
                lifecycle.getCurrentState(),
                Lifecycle.Event.ON_DESTROY
        ));
    }

    public static class AppState {
        public Lifecycle.State state;
        public Lifecycle.Event event;

        public AppState(Lifecycle.State state, Lifecycle.Event event) {
            this.state = state;
            this.event = event;
        }

        @NonNull
        @Override
        public String toString() {
            return "Состояние = " + state + "\n" +
                    "Событие = " + event;
        }
    }
}
