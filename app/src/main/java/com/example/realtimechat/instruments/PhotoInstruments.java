package com.example.realtimechat.instruments;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.realtimechat.R;
import com.example.realtimechat.app.App;
import com.example.realtimechat.datalayer.AuthRepo;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class PhotoInstruments {
    private final AuthRepo authRepo = new AuthRepo();
    public void downloadAndSetImage(String uid, ImageView imageView) {
        authRepo.readUserFromDataBase(uid, value -> Glide.with(App.getAppContext())
                .load(Uri.parse(value.getPhotoUrl()))
                .placeholder(R.mipmap.ic_deafault_avatar)
                .into(imageView));
    }
    public void setImage(Activity activity) {
        CropImage.activity()
                .setAspectRatio(1, 1)
                .setRequestedSize(150, 150)
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(activity);
    }
}