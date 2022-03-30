package com.example.realtimechat.instruments

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class MyActivityResultContract : ActivityResultContract<Any?, Uri>() {
    override fun createIntent(context: Context, input: Any?): Intent {
        return CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(600, 600)
            .setCropShape(CropImageView.CropShape.OVAL)
            .getIntent(context)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return CropImage.getActivityResult(intent)?.uri
    }

}