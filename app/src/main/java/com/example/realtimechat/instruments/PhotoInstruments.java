package com.example.realtimechat.instruments;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.example.realtimechat.R;
import com.example.realtimechat.app.App;
import com.example.realtimechat.datalayer.AuthRepo;
import com.google.firebase.database.core.Context;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class PhotoInstruments {

    private final AuthRepo authRepo = new AuthRepo();

    public Void downloadAndSetImage(String uid, ImageView imageView) {
        authRepo.readUserFromDataBase(uid, value -> Glide.with(App.getAppContext())
                .load(Uri.parse(value.getPhotoUrl()))
                .placeholder(R.mipmap.ic_deafault_avatar)
                .into(imageView));
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setImage(Activity activity) {
        CropImage.activity()
                .setAspectRatio(1, 1)
                .setRequestedSize(600, 600)
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(activity);

    }
}