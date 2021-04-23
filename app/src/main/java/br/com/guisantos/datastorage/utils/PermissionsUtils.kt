package br.com.guisantos.datastorage.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

interface PermissionsUtils {
    abstract class CameraPermissions {
        companion object {
            val RECORD_AUDIO_PERMISSION_CODE = 1000
            fun requestCameraPermission(activity: Activity) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.CAMERA),
                    RECORD_AUDIO_PERMISSION_CODE
                )
            }
            fun isCameraPermissionGranted(context: Context): Boolean {
                return ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            }
        }
    }
}