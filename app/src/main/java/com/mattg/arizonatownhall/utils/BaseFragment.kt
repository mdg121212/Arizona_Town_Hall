@file:Suppress("SameParameterValue")

package com.mattg.arizonatownhall.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.share.Sharer
import com.facebook.share.model.ShareHashtag
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.model.SharePhoto
import com.facebook.share.model.SharePhotoContent
import com.facebook.share.widget.ShareDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mattg.arizonatownhall.activities.PERMISSION_ID
import com.mattg.arizonatownhall.R
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

open class BaseFragment : Fragment(){

    private lateinit var callbackManager: CallbackManager
    private lateinit var shareDialog: ShareDialog
    private lateinit var storageRef: StorageReference
    private var mFirebaseDatabaseInstance : FirebaseFirestore? = null
    private var userId : String? = null
    private var userName : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Depracated method, removed but left in case a problem arises -> FacebookSdk.sdkInitialize(requireActivity().applicationContext)
        callbackManager = CallbackManager.Factory.create()
        shareDialog = ShareDialog(this)
        shareDialog.registerCallback(callbackManager, callBack)

        storageRef = FirebaseStorage.getInstance().reference

        mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()

        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        userId = user?.uid
        userName = user?.displayName
    }
    private fun urlEncode(s:String):String {
        return try {
            URLEncoder.encode(s, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            Log.wtf("WTF", "UTF-8 should always be supported", e)
            ""
        }
    }
    private val callBack = object: FacebookCallback<Sharer.Result> {
        override fun onSuccess(result: Sharer.Result?) {
            Log.d("base", "Share success: ${result?.postId}")
        }

        override fun onCancel() {
            Log.d("base", "Share cancelled")
        }

        override fun onError(error: FacebookException?) {
            Log.d("base", "Error: ${error?.message}")
        }
    }
     @SuppressLint("QueryPermissionsNeeded")
     private fun shareTwitter(message:String, uri: Uri) {
        val tweetIntent = Intent(Intent.ACTION_SEND)
        tweetIntent.putExtra(Intent.EXTRA_TEXT, message)
         tweetIntent.putExtra(Intent.EXTRA_STREAM, uri)
         tweetIntent.type = "image/*"
         val packManager = this.context?.packageManager
        val resolvedInfoList = packManager?.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY)
        var resolved = false
        if (resolvedInfoList != null) {
            for (resolveInfo in resolvedInfoList)
            {
                if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android"))
                {
                    tweetIntent.setClassName(
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name)
                    resolved = true
                    break
                }
            }
        }
        if (resolved)
        {
            startActivity(tweetIntent)
        }
        else
        {
            val i = Intent()
            i.putExtra(Intent.EXTRA_TEXT, message)
            i.action = Intent.ACTION_VIEW
            i.data = Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode(message))
            startActivity(i)
            Toast.makeText(requireContext(), "Twitter app isn't found", Toast.LENGTH_LONG).show()
        }
    }

    fun postToFaceBook(input: String, inputBmp: Bitmap?, inputUri: String?){
        AlertDialog.Builder(requireContext())
            .setIcon(R.drawable.com_facebook_button_icon)
            .setTitle("Share to Social Media")
            .setPositiveButton("Share to Facebook"){ dialog, _ ->
                if(inputBmp == null){
                    val shareHashtag = ShareHashtag.Builder()
                        .setHashtag("#AzTownHall")
                        .build()
                    val content = ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse(input))
                        .setShareHashtag(shareHashtag)
                        .build()
                    if(shareDialog.canShow(content)){
                        shareDialog.show(content)
                    }
                    addPointsToDatabase(3)
                    dialog.dismiss()
                }
                val image = SharePhoto.Builder()
                    .setBitmap(inputBmp)
                    .build()

                val shareHashtag = ShareHashtag.Builder()
                    .setHashtag("#AzTownHall #IWill\n$input").build()
                val photoContent = SharePhotoContent.Builder()
                    .setShareHashtag(shareHashtag)
                    .addPhoto(image)
                    .build()

                if(shareDialog.canShow(photoContent)) {
                    shareDialog.show(photoContent)
                }
                addPointsToDatabase(3)
                dialog.dismiss()
            }
            .setNegativeButton("Share To Twitter"){ dialog, _ ->
                if(inputUri == null){
                    val text = "#AzTownHall $input"
                    shareTwitter(text, Uri.parse(input))
                } else {
                    val text = "#AzTownHall #IWill $input"
                    shareTwitter(text, Uri.parse(inputUri))
                }
                addPointsToDatabase(3)
                dialog.dismiss()
            }
            .setNeutralButton("Cancel"){ dialog, _ ->
                dialog.dismiss()
            }.show()

    }
    fun startCustomTab(url: String, context: Context) {
        val builder = CustomTabsIntent.Builder()
        val colorInt = Color.parseColor("#7f0000")
        builder.setToolbarColor(colorInt)
        val customTabIntent = builder.build()
        customTabIntent.launchUrl(context, Uri.parse(url))
    }

    private fun addPointsToDatabase(points: Int){
        val docReference = mFirebaseDatabaseInstance?.collection("users")?.document(userId!!)
        docReference?.get()?.addOnSuccessListener { documentSnapshot ->
            val user = documentSnapshot.toObject(User::class.java)
            val oldPoints = user?.points!!
            //have to make this adjustment inside of the successlistener block
            docReference.update("points", oldPoints + points)
        }
    }
    //function to request necessary permissions
     fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            PERMISSION_ID
        )
    }

    //when the result of permission checks comes back
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                return
            } else {
                checkPermissions()
            }
        }
    }
    //function to check if needed permissions have been granted
     fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        showRationalDialogForPermissions()
        return false
    }
     private fun showRationalDialogForPermissions() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setMessage("It Looks like you have turned off permissions required for the app to function. They can be enabled under Application Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", requireActivity().packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()

    }
}