package dreamcatcher.zerowasteideas.general

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide

class PictureUploadModule {

    private val GALLERY_PICTURE = 1
    private val CAMERA_PICTURE = 2

    private var currentPhotoUri: Uri? = null
    private var currentPhotoPath: String? = null
    private var pictureSource: Int? = null

    private var photoPreviewLoaded = false

    fun setPicturePreview(imageView: ImageView, context: Context, requestCode: Int, photoUri: Uri?) {

        var pictureUri = ""

        when (requestCode) {
            GALLERY_PICTURE -> {
                //pictureUri = photoUri!!
            }
            CAMERA_PICTURE -> {
                pictureUri = currentPhotoPath!!
            }
        }

        try {
            Glide.with(context).load(pictureUri).into(imageView)
            //photoPreviewLoaded()
        } catch (e: Exception) {
            Log.e("Exception Message: ", e.message)
        }
    }
}