package com.mattg.aztownhall.ui.registrationDonation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mattg.aztownhall.R
import com.mattg.aztownhall.utils.BaseFragment
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationDonationFragment : BaseFragment() {



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()

        val root = inflater.inflate(R.layout.fragment_registration, container, false)

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonCardDonate = cardView
        buttonCardDonate.setOnClickListener {
            val url = "https://aztownhall.org/Donate"
           startCustomTab(url, requireContext())
        }

        val buttonCardSignUp = cardView2
        buttonCardSignUp.setOnClickListener {
            val url ="https://aztownhall.org/Support"
           startCustomTab(url, requireContext())
        }
    }
}