package com.example.realtimechat.instruments;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.realtimechat.R;
import com.example.realtimechat.app.App;
import com.example.realtimechat.domain.FirebaseRepository;

public class PhotoInstruments {

    private final FirebaseRepository firebaseRepository = new FirebaseRepository();

    public Void downloadAndSetImage(String uid, ImageView imageView) {
        firebaseRepository.readUserFromDataBase(uid, value -> Glide.with(App.getAppContext())
                .load(Uri.parse(value.getPhotoUrl()))
                .placeholder(R.mipmap.ic_deafault_avatar)
                .into(imageView));
        return null;
    }
}