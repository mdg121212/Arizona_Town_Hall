package com.mattg.arizonatownhall.activities


import android.Manifest
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.facebook.CallbackManager
import com.google.android.gms.common.GoogleApiAvailabilityLight
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.mattg.arizonatownhall.R
import com.mattg.arizonatownhall.utils.Constants
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.login_web_portal_dialog.*


const val TOPIC_NEWS = "News"
const val TOPIC_EVENTS = "Events"


const val PERMISSION_ID = 1


class MainActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var callbackManager: CallbackManager
    private var mFirebaseDatabaseInstance: FirebaseFirestore? = null
    private var navControllerBottom: NavController? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (Constants.isNetworkAvailable(this)) {

            mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()

            GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(this)


            navControllerBottom = findNavController(R.id.nav_host_fragment)

            setupNavigation()

            requestPermissions()

            subscribeToTopic(TOPIC_NEWS)

            callbackManager = CallbackManager.Factory.create()



            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_LOW
                )
                notificationChannel.lightColor = Color.BLUE
                notificationChannel.enableVibration(true)
                notificationChannel.enableLights(true)

                val notificationManager = this.getSystemService(
                    NotificationManager::class.java
                )
                notificationManager?.createNotificationChannel(notificationChannel)


            }

            val extras = intent?.extras

            Log.i(
                "**********",
                " extras is ${extras.toString()} or $extras and getKeyUrl is ${extras?.get("url")}"
            )

            //handle url link data if sent in fcm message, opens link in custom chrome tab
            if (extras != null) {
                Log.i("*******", "extras was not null it was $extras")
                if (extras.get("url") != null || !extras.get("url").toString()
                        .isNullOrEmpty() || extras.get("url") != ""
                ) {

                    val url = extras["url"].toString()
                    Log.i(
                        "********",
                        "the url part of extras is $url  it should not be blank or null"
                    )
                    if (url != "null" || url != "" || url.toString().isNotEmpty() || url.toString()
                            .isNotBlank()
                    ) {
                        if (url.startsWith("https://") || url.startsWith("http://")) {
                            startCustomTab(url, this)
                        }

                        intent.extras?.clear()

                    }

                }

            }
            //to target single device need registration token
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("OnCreate", "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }
                    //get new instance id token
                    //  val token = task.result?.token
                })
        } else {

            Toast.makeText(
                this,
                "Application functionality depends on an internet connection.",
                Toast.LENGTH_LONG
            ).show()
            onDestroy()
        }
    }


    private fun setupNavigation() {
        bottomNavigationView
        navControllerBottom?.let {
            NavigationUI.setupWithNavController(
                bottomNavigationView,
                it
            )
        }
    }


    //function to request necessary permissions
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
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
                val isFirst =
                    this.getSharedPreferences("PREFERENCE", AppCompatActivity.MODE_PRIVATE)
                        .getBoolean("isFirst", true)

                Log.i("*******", "is first  = $isFirst")

                if (isFirst) {
                    showLoginDialog(this)
                }

                this.getSharedPreferences("PREFERENCE", AppCompatActivity.MODE_PRIVATE).edit()
                    .putBoolean("isFirst", false).apply()

                return
            } else {
                checkPermissions()
            }
        }
    }


    private fun showLoginDialog(context: Context) {

        Dialog(this).apply {
            setContentView(R.layout.login_web_portal_dialog)
            btn_dialog_yes.setOnClickListener {
                startCustomTab("https://aztownhall.org/Sys/Login", context)
                dismiss()
            }
            btn_dialog_no.setOnClickListener {
                dismiss()
            }
            btn_dialog_member.setOnClickListener {
                startCustomTab("https://aztownhall.org/Support", context)
                dismiss()
            }

        }.show()

    }


    private fun startCustomTab(url: String, context: Context) {
        val builder = CustomTabsIntent.Builder()
        val colorInt = Color.parseColor("#7f0000")
        builder.setToolbarColor(colorInt)
        val customTabIntent = builder.build()
        customTabIntent.launchUrl(context, Uri.parse(url))
    }

    //function to check if needed permissions have been granted
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        showRationalDialogForPermissions()
        return false
    }

    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this)
            .setMessage("It Looks like you have turned off permissions required for the app to function. They can be enabled under Application Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", this.packageName, null)
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

    private fun subscribeToTopic(topicName: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(topicName)
            .addOnCompleteListener { task ->
                var msg = "Subscribed to $topicName"
                if (topicName != "News") {
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                }
                if (!task.isSuccessful) {
                    msg = getString(R.string.message_subscribe_failed)
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                }

            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        when (item.itemId) {
            R.id.action_settings -> {
                //  userOptionsDialog()
                navController.navigate(R.id.userOptionsFragment)
            }
            R.id.action_links -> {
                navController.navigate(R.id.linksFragment)
            }
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        Log.d("data", data.toString())
    }


}