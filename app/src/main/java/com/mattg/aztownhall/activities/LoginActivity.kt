package com.mattg.aztownhall.activities


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth.getInstance
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.mattg.aztownhall.R
import com.mattg.aztownhall.utils.User
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private var isFirebaseLaunched = false
    private var mFirebaseDatabaseInstance: FirebaseFirestore? = null
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()

        val extras = intent.extras

        if (extras != null) {
            //the intent carried a url to open
            url = extras.get("url").toString()
        }
        setClickListeners()
    }

    private fun setClickListeners() {
        btn_login.setOnClickListener {
            CoroutineScope(Dispatchers.Unconfined).launch { onSignInClickedButton() }
        }
        btn_logout.setOnClickListener {
            onSignOutClickedButton()
        }
    }

    override fun onStart() {
        super.onStart()

        val wasSignIn = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
            .getBoolean("wasSignInCalled", false)
        if (!wasSignIn) {


            //check if users signed in already in onStart
            val auth = getInstance()
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            if (url != null || url != "" || url != "null") {
                intent.putExtra("url", url)
            }

            if (auth.currentUser != null) {
                Toast.makeText(
                    applicationContext,
                    "Welcome Back! Signing you back in...",
                    Toast.LENGTH_SHORT
                ).show()

                val userToSave = getInstance().currentUser
                val userName = userToSave?.displayName
                val email = userToSave?.email
                val userId = userToSave?.uid
                val userToAdd = User(userName!!, email!!, userId!!)

                //checking to see if user document in database exists, if it doesn't, then create it
                mFirebaseDatabaseInstance!!.collection("users").document(userId).get()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val document = it.result
                            if (document != null) {
                                if (document.exists()) {

                                    finish()
                                    startActivity(intent)
                                } else {
                                    mFirebaseDatabaseInstance!!.collection("users").document(userId)
                                        .set(userToAdd)
                                }
                            }
                        }

                    }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1234) {
            //get data from intent
            val response = IdpResponse.fromResultIntent(data)
            Log.i("onActivityResult", "called")
            doOnResult(resultCode)

        } else {
            Toast.makeText(this, "Need to login", Toast.LENGTH_SHORT).show()
        }
    }

    private fun doOnResult(resultCode: Int) {
        try {
            //Successfully signed in
            if (resultCode == RESULT_OK) {
                Log.i("onActivityResult", "called")
                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("wasSignInCalled", false).apply()
                getInstance().currentUser
                val intent2 = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("url", url)
                //    finish()
                Log.i("******", "activity result calling intent for mainactivity")
                startActivity(intent2)
                finish()
                Toast.makeText(this, "Successfully signed in", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Sign in failed...please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Error) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun onSignInClickedButton() {

        isFirebaseLaunched = true

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
            //providers tell firebase what we need to include could be phone, google, etc
        )
        //Create and launch sign in intent
        val instance = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
         //   .setAlwaysShowSignInMethodScreen(true)
            .build()

        try {
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                    startActivityForResult(
                        instance,
                        1234,
                        null
                    )
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                        .putBoolean("wasSignInCalled", true).apply()
            }

        } catch (ex: FirebaseAuthException) {
            Toast.makeText(this, "Error signing in", Toast.LENGTH_LONG).show()
        }


    }

    private fun onSignOutClickedButton() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                //user is now signed out
                Toast.makeText(applicationContext, "User has signed out", Toast.LENGTH_SHORT).show()
            }
    }


}