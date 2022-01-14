package com.example.realtimechat.baseRX;

import android.util.Log;

import io.reactivex.observers.DisposableSingleObserver;

public abstract class MyDisposableObserver<T> extends DisposableSingleObserver<T> {
    private final String TAG = "MyDisposableObserver";

    public void onError(Throwable e) {
        Log.d(TAG, "onError = " + e);
    }
    public void onSuccess(T t) {}

    public static <T> MyDisposableObserver<T> create() {
        return new MyDisposableObserver<T>() {};
    }
}
