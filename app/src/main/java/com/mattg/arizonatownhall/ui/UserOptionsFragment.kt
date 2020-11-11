package com.mattg.arizonatownhall.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.mattg.arizonatownhall.activities.LoginActivity
import com.mattg.arizonatownhall.R
import com.mattg.arizonatownhall.activities.TOPIC_EVENTS
import com.mattg.arizonatownhall.utils.BaseFragment
import kotlinx.android.synthetic.main.fragment_user_options.*
import kotlinx.android.synthetic.main.notifications_dialog.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class UserOptionsFragment : BaseFragment() {

    private var mFirebaseDatabaseInstance: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_useroptions_delete_account.setOnClickListener {
            deleteAccount()
        }
        btn_useroptions_sign_out.setOnClickListener {
            signOut()
        }
        btn_useroptions_mng_notifications.setOnClickListener {
            showNotificationsDialog()
        }

    }

    private fun showNotificationsDialog() {

        val notificationDialog = Dialog(requireContext())
        notificationDialog.apply {
            setContentView(R.layout.notifications_dialog)
            setTitle("Notification channels")
            val doneText = notificationDialog.tv_done

            btn_events_subscribe.setOnClickListener {
                subscribeToTopic(TOPIC_EVENTS)
            }
            btn_events_unsubscribe.setOnClickListener {
                unsubscribeFromTopic(TOPIC_EVENTS)
            }

            doneText.setOnClickListener {
                dismiss()
            }

        }

        notificationDialog.show()
    }

    private fun subscribeToTopic(topicName: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(topicName)
            .addOnCompleteListener { task ->
                var msg = "Subscribed to $topicName"
                if (topicName != "News") {
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                }
                if (!task.isSuccessful) {
                    msg = getString(R.string.message_subscribe_failed)
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                }

            }
    }

    private fun unsubscribeFromTopic(topicTitle: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topicTitle)
            .addOnCompleteListener { task ->
                val msg = "Unsubscribed from $topicTitle"
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                if (!task.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Failed to unsubscribe from $topicTitle. Try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(requireContext())
            .addOnCompleteListener {
                //user is now signed out
                Toast.makeText(
                    requireContext(),
                    "User has signed out",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
    }

    private fun deleteAccount() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Are you sure you want to delete your account?")
            setPositiveButton("Yes") { _, _ ->
                val auth = FirebaseAuth.getInstance()
                val userToRemove = auth.currentUser
                val userId = userToRemove?.uid
                mFirebaseDatabaseInstance!!.collection("users").document(userId!!).delete()
                userToRemove.delete()
                AuthUI.getInstance().signOut(context)
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
        }
    }

}