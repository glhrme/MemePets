package br.com.guisantos.datastorage.utils

import android.app.Activity
import android.net.Uri
import android.provider.MediaStore

abstract class UtilsUri {
    companion object {
        fun getRealPathFromURI(contentUri: Uri, activity: Activity): String? {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = activity.managedQuery(contentUri, proj, null, null, null) ?: return contentUri.path
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        }
    }
}