package com.example.realtimechat.instruments

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
//Class for ActivityOnResultApi
class ImagePicker(private val activityResultRegistry: ActivityResultRegistry) {

    private val getContent: ActivityResultLauncher<String> =
        activityResultRegistry.register(REGISTRY_KEY, ActivityResultContracts.GetContent()) {

        }

    private companion object {
        private const val REGISTRY_KEY = "ImagePicker"
    }
}