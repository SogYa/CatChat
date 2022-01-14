package com.example.realtimechat.baseRX;

import android.widget.ImageView;

import com.example.realtimechat.instruments.PhotoInstruments;

import java.util.concurrent.Callable;

public class CallableDAS implements Callable<Void> {
    private final PhotoInstruments photoInstruments = new PhotoInstruments();
    private final String uid;
    private final ImageView imageView;

    public CallableDAS(String uid, ImageView imageView) {
        this.uid = uid;
        this.imageView = imageView;
    }

    @Override
    public Void call() {
        return photoInstruments.downloadAndSetImage(uid, imageView);
    }
}
