package com.vipcodeerror.brandup.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.view.View
import androidx.core.content.FileProvider
import com.vipcodeerror.brandup.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class AppUtils {

    companion object{
        fun screenShot(context: Context?, view: View): Uri? {
            val bitmap = Bitmap.createBitmap(
                view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            return getLocalBitmapUri(context!!, bitmap)
        }

        fun getLocalBitmapUri(context: Context, bmp: Bitmap): Uri? {
            var bmpUri: Uri? = null
            try {
                val file = File(
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "share_image_" + System.currentTimeMillis() + ".png"
                )
                val out = FileOutputStream(file)
                bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
                out.close()
                bmpUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    FileProvider.getUriForFile(
                        context,
                        BuildConfig.APPLICATION_ID + ".provider",
                        file
                    )
                } else {
                    Uri.fromFile(file)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return bmpUri
        }

        fun launchShareIntent(context: Context, view: View?) {
            val uri: Uri? = screenShot(context, view!!)
            val sendIntent = Intent(Intent.ACTION_SEND)

            // (Optional) Here we're passing a content URI to an image to be displayed
            sendIntent.type = "image/*"
            sendIntent.putExtra(Intent.EXTRA_STREAM, uri)
            sendIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

            // Show the Sharesheet
            context.startActivity(Intent.createChooser(sendIntent, "Share Brand Frame"))
        }

    }

}